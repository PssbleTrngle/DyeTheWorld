package com.possible_triangle.dye_the_world.index

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.kekecreations.arts_and_crafts.common.item.palette.PaintbrushPalette
import com.kekecreations.arts_and_crafts.core.registry.ACRegistries
import com.mojang.serialization.JsonOps
import com.possible_triangle.dye_the_world.Constants
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.RegistryOps
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import java.util.concurrent.CompletableFuture

object PaintPalettes {

    class Family(
        val name: String,
        val own: Map<DyeColor, ItemLike>,
        val suffix: String,
        val namespaces: List<String>,
        val base: String?,
        val requires: List<String>,
    )

    private val FAMILIES = mutableListOf<Family>()

    fun add(
        name: String,
        blocks: Map<DyeColor, ItemLike>,
        suffix: String,
        namespace: String,
        base: String? = null,
    ) {
        FAMILIES += Family(name, blocks, suffix, listOf(namespace), base, listOf(namespace))
    }

    fun addVanilla(name: String, suffix: String, base: String? = null) {
        FAMILIES += Family(name, emptyMap(), suffix, listOf("minecraft", Constants.Mods.DYE_DEPOT), base, emptyList())
    }

    private val INDEX = HashMap<String, Map<List<String>, Block>>()

    private fun index(namespace: String): Map<List<String>, Block> = INDEX.getOrPut(namespace) {
        val result = HashMap<List<String>, Block>()
        BuiltInRegistries.BLOCK.entrySet().forEach { entry ->
            val id = entry.key.location()
            if (id.namespace != namespace || entry.value == Blocks.AIR) return@forEach
            result.putIfAbsent(id.path.split("_").sorted(), entry.value)
        }
        result
    }

    private fun findOriginal(family: Family, colour: String): Block? {
        val key = (colour.split("_") + family.suffix.split("_")).sorted()
        return family.namespaces.firstNotNullOfOrNull { index(it)[key] }
    }

    private fun findBase(family: Family): Block? {
        family.base
            ?.let { BuiltInRegistries.BLOCK.getOptional(net.minecraft.resources.ResourceLocation.parse(it)).orElse(null) }
            ?.takeIf { it != Blocks.AIR }
            ?.let { return it }
        val key = family.suffix.split("_").sorted()
        return family.namespaces.firstNotNullOfOrNull { index(it)[key] }
    }

    private fun resolve(item: ItemLike): Block? {
        val direct = (item as? NonNullSupplier<*>)?.get() as? Block
        return (direct ?: Block.byItem(item.asItem())).takeIf { it != Blocks.AIR }
    }

    private fun build(provider: HolderLookup.Provider, family: Family): PaintbrushPalette? {
        val blockLookup = provider.lookupOrThrow(Registries.BLOCK)
        val itemLookup = provider.lookupOrThrow(Registries.ITEM)

        fun blockHolder(block: Block): Holder<Block> =
            blockLookup.getOrThrow(ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(block)))

        fun itemHolder(item: Item): Holder<Item> =
            itemLookup.getOrThrow(ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(item)))

        val own: Map<String, Block> = family.own.entries
            .mapNotNull { (dye, item) -> resolve(item)?.let { dye.toString() to it } }
            .toMap()

        val members = LinkedHashSet<Block>(own.values)
        findBase(family)?.let { members += it }

        val mappings = LinkedHashMap<Holder<Item>, Holder<Block>>()
        val missing = mutableListOf<String>()

        DyedArtsAndCrafts.allPaintbrushes().forEach { (colour, brush) ->
            val original = findOriginal(family, colour)
            if (original != null) members += original

            val target = own[colour] ?: original
            if (target == null) {
                missing += colour
                return@forEach
            }
            mappings[itemHolder(brush)] = blockHolder(target)
        }

        if (missing.isNotEmpty()) {
            Constants.LOGGER.warn("Palette ${family.name}: no block for brushes $missing")
        }
        if (members.size < 2 || mappings.isEmpty()) {
            Constants.LOGGER.warn("Palette ${family.name}: skipped (members=${members.size}, mappings=${mappings.size})")
            return null
        }

        return PaintbrushPalette(
            HolderSet.direct(members.map { blockHolder(it) }),
            mappings,
        )
    }

    private fun conditions(family: Family): JsonArray {
        val mods = (listOf(Constants.Mods.DYE_DEPOT) + family.requires).distinct()
        return JsonArray().also { array ->
            mods.forEach { modId ->
                array.add(JsonObject().apply {
                    addProperty("type", "neoforge:mod_loaded")
                    addProperty("modid", modId)
                })
            }
        }
    }

    fun provider(output: PackOutput, lookup: CompletableFuture<HolderLookup.Provider>): DataProvider =
        object : DataProvider {
            override fun run(cache: CachedOutput): CompletableFuture<*> =
                lookup.thenCompose { provider -> write(cache, output, provider) }

            override fun getName(): String = "Paintbrush palettes"
        }

    private fun write(
        cache: CachedOutput,
        output: PackOutput,
        provider: HolderLookup.Provider,
    ): CompletableFuture<*> {
        val ops = RegistryOps.create(JsonOps.INSTANCE, provider)
        val registryId = ACRegistries.PAINTBRUSH_PALETTE.location()
        val dir = output.getOutputFolder(PackOutput.Target.DATA_PACK)
            .resolve(Constants.MOD_ID)
            .resolve(registryId.namespace)
            .resolve(registryId.path)

        val futures = FAMILIES.mapNotNull { family ->
            val palette = build(provider, family) ?: return@mapNotNull null
            val json = PaintbrushPalette.CODEC.encodeStart(ops, palette)
                .resultOrPartial { Constants.LOGGER.error("Palette ${family.name}: $it") }
                .orElse(null) ?: return@mapNotNull null

            val obj = json.asJsonObject
            obj.add("neoforge:conditions", conditions(family))
            DataProvider.saveStable(cache, obj, dir.resolve("${family.name}.json"))
        }
        return CompletableFuture.allOf(*futures.toTypedArray())
    }
}

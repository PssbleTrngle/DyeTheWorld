package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.ARTS_AND_CRAFTS
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.util.nullness.NonNullConsumer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab.TabVisibility
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.fml.ModLoadingContext
import net.neoforged.neoforge.client.model.generators.BlockModelProvider
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent

object DyedArtsAndCrafts {

    private val DYES = dyesFor(ARTS_AND_CRAFTS)

    private val ARTS_AND_CRAFTS_NAMESPACE = ARTS_AND_CRAFTS.createId("placeholder").namespace

    private val ARTS_AND_CRAFTS_TAB = ResourceKey.create(
        Registries.CREATIVE_MODE_TAB,
        ARTS_AND_CRAFTS.createId("configurable_tab")
    )

    private class PaintbrushGroup(
        val reference: String,
        val before: Boolean,
        val dyes: List<String>,
    )

    private class StoneFamily(
        val cube: String,
        val slab: String,
        val stairs: String,
        val wall: String,
    )

    private class Potted(
        val model: String,
        val parent: ResourceLocation,
        val plant: ResourceLocation?,
    )

    private val PAINTBRUSH_GROUPS = listOf(
        PaintbrushGroup("red", true, listOf("maroon", "rose")),
        PaintbrushGroup("red", false, listOf("coral")),
        PaintbrushGroup("orange", true, listOf("ginger")),
        PaintbrushGroup("orange", false, listOf("tan")),
        PaintbrushGroup("yellow", true, listOf("beige")),
        PaintbrushGroup("yellow", false, listOf("amber", "olive")),
        PaintbrushGroup("green", true, listOf("forest")),
        PaintbrushGroup("green", false, listOf("verdant")),
        PaintbrushGroup("cyan", true, listOf("teal")),
        PaintbrushGroup("cyan", false, listOf("mint", "aqua")),
        PaintbrushGroup("blue", false, listOf("slate", "navy")),
        PaintbrushGroup("purple", true, listOf("indigo")),
    )

    private val STONE_FAMILIES = listOf(
        StoneFamily("terracotta_shingles", "terracotta_shingle_slab", "terracotta_shingle_stairs", "terracotta_shingle_wall"),
        StoneFamily("soapstone", "soapstone_slab", "soapstone_stairs", "soapstone_wall"),
        StoneFamily("polished_soapstone", "polished_soapstone_slab", "polished_soapstone_stairs", "polished_soapstone_wall"),
        StoneFamily("soapstone_bricks", "soapstone_brick_slab", "soapstone_brick_stairs", "soapstone_brick_wall"),
        StoneFamily("mud_bricks", "mud_brick_slab", "mud_brick_stairs", "mud_brick_wall"),
    )

    private val CHALK_PATTERNS = listOf(
        "dot",
        "vertical_line_middle",
        "rotated_line_middle",
        "corner_0", "corner_1", "corner_2", "corner_3",
        "t_cross_0", "t_cross_1", "t_cross_2", "t_cross_3",
        "plus",
        "diagonal_line_right",
        "diagonal_line_left",
        "x",
        "line_edge_0", "line_edge_1", "line_edge_2", "line_edge_3",
        "triangle_0", "triangle_1", "triangle_2", "triangle_3",
        "circle",
        "square",
        "block_corner_0", "block_corner_1", "block_corner_2", "block_corner_3",
        "arch_0", "arch_1", "arch_2", "arch_3",
    )

    private fun aac(path: String): ResourceLocation =
        ResourceLocation.fromNamespaceAndPath(ARTS_AND_CRAFTS_NAMESPACE, path)

    private fun mc(path: String): ResourceLocation =
        ResourceLocation.withDefaultNamespace(path)

    private fun cross(plant: String, model: String = "potted_$plant", parent: String = "flower_pot_cross") =
        Potted(model, mc("block/$parent"), mc("block/$plant"))

    private fun bare(model: String, parent: String, plant: ResourceLocation? = null) =
        Potted(model, mc("block/$parent"), plant)

    private val POTTED = listOf(
        cross("acacia_sapling"),
        cross("allium"),
        bare("potted_potted_azalea_bush", "potted_azalea_bush"),
        cross("azure_bluet"),
        bare("potted_bamboo", "potted_bamboo"),
        cross("birch_sapling"),
        cross("blue_orchid"),
        cross("brown_mushroom"),
        bare("potted_cactus", "potted_cactus"),
        cross("cherry_sapling"),
        Potted("potted_cork_sapling", mc("block/flower_pot_cross"), aac("block/cork_sapling")),
        cross("cornflower"),
        cross("crimson_fungus"),
        cross("crimson_roots_pot", model = "potted_crimson_roots_pot"),
        cross("dandelion"),
        cross("dark_oak_sapling"),
        cross("dead_bush"),
        cross("fern", parent = "tinted_flower_pot_cross"),
        bare("potted_potted_flowering_azalea_bush", "potted_flowering_azalea_bush"),
        cross("jungle_sapling"),
        cross("lily_of_the_valley"),
        bare("potted_mangrove_propagule", "potted_mangrove_propagule", mc("block/mangrove_propagule")),
        cross("oak_sapling"),
        cross("orange_tulip"),
        cross("oxeye_daisy"),
        cross("pink_tulip"),
        cross("poppy"),
        cross("red_mushroom"),
        cross("red_tulip"),
        cross("spruce_sapling"),
        cross("torchflower"),
        cross("warped_fungus"),
        cross("warped_roots_pot", model = "potted_warped_roots_pot"),
        cross("white_tulip"),
        cross("wither_rose"),
    )

    private val PAINTBRUSHES: Map<String, Pair<ResourceLocation, Item>> by lazy {
        val result = LinkedHashMap<String, Pair<ResourceLocation, Item>>()
        BuiltInRegistries.ITEM.entrySet().forEach { entry ->
            val id = entry.key.location()
            if (id.namespace != ARTS_AND_CRAFTS_NAMESPACE) return@forEach

            val tokens = id.path.split("_")
            if (!tokens.contains("paintbrush")) return@forEach

            val colour = tokens.filter { it != "paintbrush" }.joinToString("_")
            if (colour.isNotEmpty()) result[colour] = id to entry.value
        }
        result
    }

    private fun paintbrush(dye: String): Pair<ResourceLocation, Item>? = PAINTBRUSHES[dye]

    fun allPaintbrushes(): Map<String, Item> =
        PAINTBRUSHES.mapValues { it.value.second }

    private fun paintbrushStack(dye: String): ItemStack? =
        paintbrush(dye)?.let { ItemStack(it.second) }

    private fun paintbrushModels() {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_MODEL, NonNullConsumer { provider ->
            DYES.forEach { dye ->
                val (itemId, _) = paintbrush(dye.toString()) ?: return@forEach

                provider
                    .getBuilder("${itemId.namespace}:item/${itemId.path}")
                    .parent(provider.getExistingFile(provider.mcLoc("item/handheld")))
                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(itemId.namespace, "item/${itemId.path}"))
            }
        })
    }

    private fun blockModel(
        models: BlockModelProvider,
        name: String,
        parent: ResourceLocation,
        textures: Map<String, ResourceLocation>,
    ) {
        val builder = models
            .getBuilder("$ARTS_AND_CRAFTS_NAMESPACE:block/$name")
            .parent(ModelFile.UncheckedModelFile(parent))
        textures.forEach { (key, texture) -> builder.texture(key, texture) }
    }

    private fun stoneModels(models: BlockModelProvider, dye: String, family: StoneFamily) {
        val texture = aac("block/${dye}_${family.cube}")
        val faces = mapOf("bottom" to texture, "side" to texture, "top" to texture)
        val wall = mapOf("wall" to texture)

        val cube = "${dye}_${family.cube}"
        val slab = "${dye}_${family.slab}"
        val stairs = "${dye}_${family.stairs}"
        val wallName = "${dye}_${family.wall}"

        blockModel(models, cube, mc("block/cube_all"), mapOf("all" to texture))

        blockModel(models, slab, mc("block/slab"), faces)
        blockModel(models, "${slab}_top", mc("block/slab_top"), faces)

        blockModel(models, stairs, mc("block/stairs"), faces)
        blockModel(models, "${stairs}_inner", mc("block/inner_stairs"), faces)
        blockModel(models, "${stairs}_outer", mc("block/outer_stairs"), faces)

        blockModel(models, wallName, mc("block/wall_inventory"), wall)
        blockModel(models, "${wallName}_post", mc("block/template_wall_post"), wall)
        blockModel(models, "${wallName}_side", mc("block/template_wall_side"), wall)
        blockModel(models, "${wallName}_side_tall", mc("block/template_wall_side_tall"), wall)
    }

    private fun decorationModels(models: BlockModelProvider, dye: String) {
        val plaster = aac("block/${dye}_plaster")
        blockModel(
            models,
            "${dye}_plaster",
            aac("block/plaster_model"),
            mapOf("particle" to plaster, "plaster" to plaster)
        )

        blockModel(models, "${dye}_chalk", mc("block/cube_all"), mapOf("all" to aac("block/${dye}_chalk")))

        CHALK_PATTERNS.forEach { pattern ->
            val texture = aac("block/${dye}_$pattern")
            blockModel(
                models,
                "${dye}_$pattern",
                aac("block/chalk_dust"),
                mapOf("dust" to texture, "particle" to texture)
            )
        }

        val pot = aac("block/${dye}_flower_pot")
        val potTextures = mapOf("flowerpot" to pot, "particle" to pot)

        blockModel(models, "${dye}_flower_pot", mc("block/flower_pot"), potTextures)

        POTTED.forEach { potted ->
            val textures = if (potted.plant == null) potTextures else potTextures + ("plant" to potted.plant)
            blockModel(models, "${dye}_${potted.model}", potted.parent, textures)
        }
    }

    private fun blockModels() {
        REGISTRATE.addDataGenerator(ProviderType.BLOCKSTATE, NonNullConsumer { provider ->
            val models = provider.models()
            DYES.forEach { dye ->
                val name = dye.toString()
                STONE_FAMILIES.forEach { family -> stoneModels(models, name, family) }
                decorationModels(models, name)
            }
        })
    }

    private fun blockItem(provider: ItemModelProvider, name: String) {
        provider
            .getBuilder("$ARTS_AND_CRAFTS_NAMESPACE:item/$name")
            .parent(ModelFile.UncheckedModelFile(aac("block/$name")))
    }

    private fun flatItem(provider: ItemModelProvider, name: String, parent: String) {
        provider
            .getBuilder("$ARTS_AND_CRAFTS_NAMESPACE:item/$name")
            .parent(ModelFile.UncheckedModelFile(mc(parent)))
            .texture("layer0", aac("item/$name"))
    }

    private fun blockItemModels() {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_MODEL, NonNullConsumer { provider ->
            DYES.forEach { dye ->
                val name = dye.toString()

                STONE_FAMILIES.forEach { family ->
                    blockItem(provider, "${name}_${family.cube}")
                    blockItem(provider, "${name}_${family.slab}")
                    blockItem(provider, "${name}_${family.stairs}")
                    blockItem(provider, "${name}_${family.wall}")
                }

                blockItem(provider, "${name}_plaster")
                blockItem(provider, "${name}_chalk")

                flatItem(provider, "${name}_chalk_stick", "item/handheld")
                flatItem(provider, "${name}_flower_pot", "item/generated")
            }
        })
    }

    private fun paintbrushTranslations() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, NonNullConsumer { provider ->
            DYES.forEach { dye ->
                val (itemId, _) = paintbrush(dye.toString()) ?: return@forEach

                provider.add("item.${itemId.namespace}.${itemId.path}", "${dye.translation} Paintbrush")
            }
        })
    }

    private fun palette(name: String, suffix: String, base: String? = null) {
        PaintPalettes.add(name, emptyMap(), suffix, ARTS_AND_CRAFTS, base)
    }

    init {
        palette("terracotta_shingles", "terracotta_shingles")
        palette("terracotta_shingles_slabs", "terracotta_shingle_slab")
        palette("terracotta_shingles_stairs", "terracotta_shingle_stairs")
        palette("terracotta_shingles_walls", "terracotta_shingle_wall")

        palette("soapstone", "soapstone")
        palette("soapstone_slabs", "soapstone_slab")
        palette("soapstone_stairs", "soapstone_stairs")
        palette("soapstone_walls", "soapstone_wall")

        palette("polished_soapstone", "polished_soapstone")
        palette("polished_soapstone_slabs", "polished_soapstone_slab")
        palette("polished_soapstone_stairs", "polished_soapstone_stairs")
        palette("polished_soapstone_walls", "polished_soapstone_wall")

        palette("soapstone_bricks", "soapstone_bricks")
        palette("soapstone_bricks_slabs", "soapstone_brick_slab")
        palette("soapstone_bricks_stairs", "soapstone_brick_stairs")
        palette("soapstone_bricks_walls", "soapstone_brick_wall")

        palette("mud_bricks", "mud_bricks")
        palette("mud_bricks_slabs", "mud_brick_slab")
        palette("mud_bricks_stairs", "mud_brick_stairs")
        palette("mud_bricks_walls", "mud_brick_wall")

        palette("chalk", "chalk", base = "white_chalk")
    }

    private fun fillPaintbrushes(event: BuildCreativeModeTabContentsEvent) {
        val visibility = TabVisibility.PARENT_AND_SEARCH_TABS

        PAINTBRUSH_GROUPS.forEach { group ->
            val items = group.dyes.mapNotNull { paintbrushStack(it) }
            if (items.isEmpty()) return@forEach

            val reference = paintbrushStack(group.reference)

            if (reference == null || !event.parentEntries.contains(reference)) {
                items.forEach { event.accept(it, visibility) }
                return@forEach
            }

            if (group.before) {
                items.forEach { event.insertBefore(reference, it, visibility) }
            } else {
                var anchor: ItemStack = reference
                items.forEach {
                    event.insertAfter(anchor, it, visibility)
                    anchor = it
                }
            }
        }
    }

    private fun fillCreativeTab(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey != ARTS_AND_CRAFTS_TAB) return
        fillPaintbrushes(event)
    }

    private fun gatherData(event: GatherDataEvent) {
        event.generator.addProvider(
            event.includeServer(),
            PaintPalettes.provider(event.generator.packOutput, event.lookupProvider),
        )
    }

    fun register() {
        paintbrushModels()
        blockModels()
        blockItemModels()
        paintbrushTranslations()

        val modBus = requireNotNull(ModLoadingContext.get().activeContainer.eventBus) {
            "Mod event bus is not available"
        }
        modBus.addListener { event: BuildCreativeModeTabContentsEvent -> fillCreativeTab(event) }
        modBus.addListener { event: GatherDataEvent -> gatherData(event) }
    }
}

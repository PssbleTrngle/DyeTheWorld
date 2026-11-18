package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.migrate
import com.possible_triangle.multikulti.registrate.defaultCreativeModeTab
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateTagsProvider
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus

class DyedRegistrate private constructor(
    namespace: String,
    val mod: String,
    private val autoMigrate: Boolean,
) : AbstractRegistrate<DyedRegistrate>(namespace) {
    companion object {
        private val REGISTRATES = hashMapOf<String, DyedRegistrate>()

        fun create(
            namespace: String,
            mod: String,
            migrate: Boolean,
        ) = REGISTRATES.getOrPut(namespace) { DyedRegistrate(namespace, mod, migrate) }
    }

    init {
        @Suppress("TYPE_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        defaultCreativeTab(null as ResourceKey<CreativeModeTab>?)
    }

    val dyes = dyesFor(mod)
    private var isRegistered = false

    fun register(modBus: IEventBus) {
        if (isRegistered) return
        registerEventListeners(modBus)
        isRegistered = true
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> TagKey<T>.provider(): ProviderType<out RegistrateTagsProvider<T>> =
        when (registry) {
            Registries.BLOCK -> ProviderType.BLOCK_TAGS
            Registries.ITEM -> ProviderType.ITEM_TAGS
            else -> throw IllegalArgumentException("no tag provider known for registry ${registry.location()}")
        } as ProviderType<out RegistrateTagsProvider<T>>

    fun dyedBlock(color: DyeColor) = dyedBlock(color, ::Block)

    fun <T : Block> dyedBlock(
        color: DyeColor,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, DyedRegistrate> {
        val name = currentName()
        return entry(name) { callback ->
            DyedBlockBuilder(
                this,
                this,
                color,
                name,
                callback,
                factory,
                BlockBehaviour.Properties::of,
            ).apply {
                defaultBlockstate()
                defaultLoot()
                defaultLang()
                tagDyed()
            }
        }.also {
            if (autoMigrate) it.migrate()
        }
    }

    fun <T : Item> dyedItem(
        color: DyeColor,
        factory: NonNullFunction<Item.Properties, T>,
    ): ItemBuilder<T, DyedRegistrate> = dyedItem(color, this, factory)

    fun <T : Item, P : Any> dyedItem(
        color: DyeColor,
        parent: P,
        factory: NonNullFunction<Item.Properties, T>,
        name: String = currentName(),
    ): ItemBuilder<T, P> =
        entry(name) { callback ->
            DyedItemBuilder(
                this,
                parent,
                color,
                name,
                callback,
                factory,
            ).apply {
                defaultModel()
                defaultLang()
                tagDyed()
                defaultCreativeModeTab?.let(::tab)
            }
        }.also {
            if (autoMigrate) it.migrate()
        }

    fun <T : Any> dyed(factory: DyedRegistrate.(dye: DyeColor) -> T): Map<DyeColor, T> =
        dyes.associateWith {
            factory(it)
        }

    fun <T : Block> dyedBlocks(factory: BlockFactory<T>): Map<DyeColor, BlockEntry<T>> =
        dyes.associateWith {
            factory(it)
                .register()
        }

    fun <T : Item> dyedItems(factory: ItemFactory<T>): Map<DyeColor, ItemEntry<T>> =
        dyes.associateWith {
            factory(it)
                .register()
        }
}

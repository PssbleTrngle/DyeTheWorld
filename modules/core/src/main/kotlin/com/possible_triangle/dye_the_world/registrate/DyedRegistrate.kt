package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.migrate
import com.possible_triangle.multikulti.registrate.MultikultiRegistrate
import com.possible_triangle.multikulti.registrate.defaultCreativeModeTab
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateTagsProvider
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

private typealias DyedFactory<P, T> = (properties: P, dye: DyeColor) -> T

class DyedRegistrate private constructor(
    namespace: String,
    val mod: String,
    private val autoMigrate: Boolean,
) : MultikultiRegistrate<DyedRegistrate>(namespace) {
    private var currentDye: DyeColor? = null

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

    override fun <T : Block, P : Any> block(
        parent: P,
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, P> {
        val dye = currentDye ?: return super.block(parent, name, factory)

        return entry(name) { callback ->
            DyedBlockBuilder(
                this,
                parent,
                dye,
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

    // TODO move to MultikultiRegistrate
    fun item(): ItemBuilder<Item, DyedRegistrate> = item(::Item)

    // TODO move to MultikultiRegistrate
    fun block(): BlockBuilder<Block, DyedRegistrate> = block(::Block)

    override fun <T : Item, P : Any> item(
        parent: P,
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
    ): ItemBuilder<T, P> {
        val dye = currentDye ?: return super.item(parent, name, factory)

        return entry(name) { callback ->
            DyedItemBuilder(
                this,
                parent,
                dye,
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
    }

    fun <T : Any> dyed(factory: DyedRegistrate.(dye: DyeColor) -> T): Map<DyeColor, T> {
        val map =
            dyes.associateWith {
                currentDye = it
                factory(it)
            }
        currentDye = null
        return map
    }
}

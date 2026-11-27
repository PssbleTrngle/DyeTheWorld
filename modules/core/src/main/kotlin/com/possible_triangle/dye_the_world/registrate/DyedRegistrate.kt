package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.migrate
import com.possible_triangle.multikulti.registrate.MultikultiRegistrate
import com.possible_triangle.multikulti.registrate.defaultCreativeModeTab
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullFunction
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

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

    private fun dyed(dye: DyeColor?) =
        apply {
            currentDye = dye
        }

    fun <T : Any> dyed(factory: DyedRegistrate.(dye: DyeColor) -> T): Map<DyeColor, T> {
        val map =
            dyes.associateWith {
                dyed(it)
                factory(it)
            }
        dyed(null)
        return map
    }

    fun <I, O> Map<DyeColor, I>.mapDyed(mapper: DyedRegistrate.(DyeColor, I) -> O): Map<DyeColor, O> =
        mapValues {
            dyed(it.key)
            mapper(it.key, it.value)
        }
}

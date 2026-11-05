package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.DyedTags
import com.possible_triangle.dye_the_world.extensions.doMigration
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import com.tterrag.registrate.util.nullness.NonNullBiFunction
import com.tterrag.registrate.util.nullness.NonNullFunction
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

class DyedBlockBuilder<T : Block, P : Any> internal constructor(
    private val owner: DyedRegistrate,
    parent: P,
    override val dye: DyeColor,
    name: String,
    callback: BuilderCallback,
    factory: NonNullFunction<Properties, T>,
    initialProperties: NonNullSupplier<Properties>,
) : BlockBuilder<T, P>(owner, parent, name, callback, factory, initialProperties),
    DyedBuilder {
    override var migrateFrom: ResourceLocation? = null

    init {
        doMigration()
    }

    override fun <I : Item> item(factory: NonNullBiFunction<in T, Item.Properties, out I>): ItemBuilder<I, BlockBuilder<T, P>> =
        owner.entry(name) { callback ->
            DyedItemBuilder(owner, this as BlockBuilder<T, P>, dye, name, callback) { properties ->
                factory.apply(entry, properties)
            }.apply {
                setData(ProviderType.LANG, NonNullBiConsumer.noop())
                model { _, provider ->
                    provider.blockItem(asSupplier())
                }
                tagDyed()
                this.migrateFrom = this@DyedBlockBuilder.migrateFrom
            }
        }

    fun tagDyed() {
        tag(DyedTags.BLOCKS.loader("dyed/$dye"))
    }
}

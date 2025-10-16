package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.index.DyedTags
import com.possible_triangle.dye_the_world.withNamespace
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import com.tterrag.registrate.util.nullness.NonNullBiFunction
import com.tterrag.registrate.util.nullness.NonNullFunction
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.tags.TagEntry
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

class DyedBlockBuilder<T : Block, P : Any> internal constructor(
    private val owner: DyedRegistrate,
    parent: P,
    override val dye: DyeColor,
    private val mod: String,
    name: String,
    callback: BuilderCallback,
    factory: NonNullFunction<Properties, T>,
    initialProperties: NonNullSupplier<Properties>,
) : BlockBuilder<T, P>(owner, parent, name, callback, factory, initialProperties), DyedBuilder {

    init {
        mod.validateMod()
    }

    override fun recipe(cons: NonNullBiConsumer<DataGenContext<Block, T>, RegistrateRecipeProvider>) = apply {
        super.recipe { context, provider ->
            provider.withNamespace(mod) {
                cons.accept(context, provider)
            }
        }
    }

    override fun loot(cons: NonNullBiConsumer<RegistrateBlockLootTables, T>) = apply {
        super.loot { provider, value ->
            provider.withNamespace(mod) {
                cons.accept(provider, value)
            }
        }
    }

    override fun <I : Item> item(factory: NonNullBiFunction<in T, Item.Properties, out I>): ItemBuilder<I, BlockBuilder<T, P>> {
        return owner.entry(name) { callback ->
            DyedItemBuilder(owner, this as BlockBuilder<T, P>, dye, mod, name, callback) { properties ->
                factory.apply(entry, properties)
            }.apply {
                setData(ProviderType.LANG, NonNullBiConsumer.noop())
                model { _, provider ->
                    provider.blockItem(asSupplier())
                }
                tagDyed()
            }
        }
    }

    fun tagDyed() {
        optionalTag(DyedTags.Blocks.DYED[dye]!!)
    }

}
package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.index.DyedTags
import com.possible_triangle.dye_the_world.withNamespace
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import com.tterrag.registrate.util.nullness.NonNullFunction
import net.minecraft.tags.TagEntry
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

class DyedItemBuilder<T : Item, P : Any> internal constructor(
    owner: DyedRegistrate,
    parent: P,
    override val dye: DyeColor,
    private val mod: String,
    name: String,
    callback: BuilderCallback,
    factory: NonNullFunction<Properties, T>,
) : ItemBuilder<T, P>(owner, parent, name, callback, factory), DyedBuilder {

    init {
        mod.validateMod()
    }

    override fun recipe(cons: NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider>) = apply {
        super.recipe { context, provider ->
            provider.withNamespace(mod) {
                cons.accept(context, provider)
            }
        }
    }

    fun tagDyed() {
        optionalTag(DyedTags.Items.DYED[dye]!!)
    }

}
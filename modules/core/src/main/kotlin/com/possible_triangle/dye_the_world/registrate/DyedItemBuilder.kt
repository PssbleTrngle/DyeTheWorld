package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.DyedTags
import com.possible_triangle.dye_the_world.extensions.doMigration
import com.tterrag.registrate.builders.BuilderCallback
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullFunction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

class DyedItemBuilder<T : Item, P : Any> internal constructor(
    owner: DyedRegistrate,
    parent: P,
    override val dye: DyeColor,
    name: String,
    callback: BuilderCallback,
    factory: NonNullFunction<Properties, T>,
) : ItemBuilder<T, P>(owner, parent, name, callback, factory),
    DyedBuilder {
    override var migrateFrom: ResourceLocation? = null

    init {
        doMigration()
    }

    fun tagDyed() {
        tag(DyedTags.ITEMS.loader("dyed/$dye"))
    }
}

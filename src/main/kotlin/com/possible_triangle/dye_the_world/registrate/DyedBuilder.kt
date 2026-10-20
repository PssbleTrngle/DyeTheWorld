package com.possible_triangle.dye_the_world.registrate

import com.tterrag.registrate.builders.Builder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor

interface DyedBuilder {
    val dye: DyeColor
    var migrateFrom: ResourceLocation?
}

val Builder<*, *, *, *>.dye: DyeColor
    get() {
        return if (this is DyedBuilder) {
            this.dye
        } else {
            throw ClassCastException("not a DyedBlockBuilder")
        }
    }

val Builder<*, *, *, *>.mod: String
    get() {
        val owner = this.owner
        return if (owner is DyedRegistrate) {
            owner.mod
        } else {
            throw ClassCastException("not a DyedBlockBuilder")
        }
    }

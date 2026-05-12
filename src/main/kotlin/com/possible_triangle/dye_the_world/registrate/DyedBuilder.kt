package com.possible_triangle.dye_the_world.registrate

import com.possible_triangle.dye_the_world.Constants
import com.tterrag.registrate.builders.Builder
import net.minecraft.world.item.DyeColor

interface DyedBuilder {
    val dye: DyeColor
}

val Builder<*, *, *, *>.dye: DyeColor
    get() {
        return if (this is DyedBuilder) {
            this.dye
        } else {
            throw ClassCastException("not a DyedBlockBuilder")
        }
    }

internal fun String.validateMod() {
    if (this == Constants.MOD_ID) error("dyed entry 'mod' should not be ${Constants.MOD_ID}")
}

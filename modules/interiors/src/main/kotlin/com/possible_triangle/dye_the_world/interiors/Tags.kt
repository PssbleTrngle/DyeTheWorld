package com.possible_triangle.dye_the_world.interiors

import com.possible_triangle.dye_the_world.DyedTags

// TODO rename
object Tags {
    object Blocks {
        val CHAIRS = DyedTags.BLOCKS.create(CREATE_INTERIORS, "chairs")
        val FLOOR_CHAIRS = DyedTags.BLOCKS.create(CREATE_INTERIORS, "floor_chairs")
    }

    object Items {
        val CHAIRS = DyedTags.ITEMS.copy(Blocks.CHAIRS)
        val FLOOR_CHAIRS = DyedTags.ITEMS.copy(Blocks.FLOOR_CHAIRS)
    }
}

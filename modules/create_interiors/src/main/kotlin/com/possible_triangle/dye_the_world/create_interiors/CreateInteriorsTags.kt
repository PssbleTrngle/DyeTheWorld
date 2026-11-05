package com.possible_triangle.dye_the_world.create_interiors

import com.possible_triangle.dye_the_world.DyedTags

object CreateInteriorsTags {
    object Blocks {
        val CHAIRS = DyedTags.BLOCKS.create(CREATE_INTERIORS, "chairs")
        val FLOOR_CHAIRS = DyedTags.BLOCKS.create(CREATE_INTERIORS, "floor_chairs")
    }

    object Items {
        val CHAIRS = DyedTags.ITEMS.copy(Blocks.CHAIRS)
        val FLOOR_CHAIRS = DyedTags.ITEMS.copy(Blocks.FLOOR_CHAIRS)
    }
}

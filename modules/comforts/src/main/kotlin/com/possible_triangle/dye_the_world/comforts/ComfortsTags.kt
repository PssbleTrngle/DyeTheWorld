package com.possible_triangle.dye_the_world.comforts

import com.possible_triangle.dye_the_world.DyedTags

object ComfortsTags {
    object Blocks {
        val SLEEPING_BAGS = DyedTags.BLOCKS.create(COMFORTS, "sleeping_bags")
        val HAMMOCKS = DyedTags.BLOCKS.create(COMFORTS, "hammocks")
    }

    object Items {
        val SLEEPING_BAGS = DyedTags.ITEMS.copy(Blocks.SLEEPING_BAGS)
        val HAMMOCKS = DyedTags.ITEMS.copy(Blocks.HAMMOCKS)
    }
}

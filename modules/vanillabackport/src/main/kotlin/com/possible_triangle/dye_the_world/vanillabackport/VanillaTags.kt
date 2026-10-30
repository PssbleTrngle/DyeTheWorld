package com.possible_triangle.dye_the_world.vanillabackport

import com.possible_triangle.dye_the_world.DyedTags

object VanillaTags {
    object Blocks {
        val CONCRETE_SLABS = DyedTags.BLOCKS.vanilla("concrete_slabs")
        val CONCRETE_STAIRS = DyedTags.BLOCKS.vanilla("concrete_stairs")
    }

    object Items {
        val CONCRETE_SLABS = DyedTags.ITEMS.copy(Blocks.CONCRETE_SLABS)
        val CONCRETE_STAIRS = DyedTags.ITEMS.copy(Blocks.CONCRETE_STAIRS)
    }
}

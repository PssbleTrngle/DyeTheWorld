package com.possible_triangle.dye_the_world.sleep_tight

import com.possible_triangle.dye_the_world.DyedTags

object SleepTightTags {
    object Blocks {
        val HAMMOCKS = DyedTags.BLOCKS.create(SLEEP_TIGHT, "hammocks")
    }

    object Items {
        val HAMMOCKS = DyedTags.ITEMS.copy(Blocks.HAMMOCKS)
    }
}

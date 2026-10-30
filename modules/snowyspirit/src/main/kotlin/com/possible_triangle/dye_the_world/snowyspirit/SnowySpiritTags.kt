package com.possible_triangle.dye_the_world.snowyspirit

import com.possible_triangle.dye_the_world.DyedTags

object SnowySpiritTags {
    object Blocks {
        val GLOW_LIGHTS = DyedTags.BLOCKS.create(SNOWYSPIRIT, "glow_lights")
        val GUMDROPS = DyedTags.BLOCKS.create(SNOWYSPIRIT, "gumdrops")
    }

    object Items {
        val GLOW_LIGHTS = DyedTags.ITEMS.copy(Blocks.GLOW_LIGHTS)
        val GUMDROPS = DyedTags.ITEMS.copy(Blocks.GUMDROPS)
    }
}

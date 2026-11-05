package com.possible_triangle.dye_the_world.snowy_spirit

import com.possible_triangle.dye_the_world.DyedTags

object SnowySpiritTags {
    object Blocks {
        val GLOW_LIGHTS = DyedTags.BLOCKS.create(SNOWY_SPIRIT, "glow_lights")
        val GUMDROPS = DyedTags.BLOCKS.create(SNOWY_SPIRIT, "gumdrops")
    }

    object Items {
        val GLOW_LIGHTS = DyedTags.ITEMS.copy(Blocks.GLOW_LIGHTS)
        val GUMDROPS = DyedTags.ITEMS.copy(Blocks.GUMDROPS)
    }
}

package com.possible_triangle.dye_the_world.supplementaries

import com.possible_triangle.dye_the_world.DyedTags

object SupplementariesTags {
    object Blocks {
        val AWNINGS = DyedTags.BLOCKS.create(SUPPLEMENTARIES, "awnings")
        val BUNTINGS = DyedTags.BLOCKS.create(SUPPLEMENTARIES, "buntings")
        val BOUNCY_BLOCKS = DyedTags.BLOCKS.create(SUPPLEMENTARIES, "bouncy_blocks")
        val CEILING_BANNERS = DyedTags.BLOCKS.create(AMENDMENTS, "ceiling_banners")
    }

    object Items {
        val AWNINGS = DyedTags.ITEMS.copy(Blocks.AWNINGS)
        val BUNTINGS = DyedTags.ITEMS.copy(Blocks.BUNTINGS)
    }
}

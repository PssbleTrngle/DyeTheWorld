package com.possible_triangle.dye_the_world.aeronautics

import com.possible_triangle.dye_the_world.DyedTags

object Tags {
    object Blocks {
        val SYMMETRIC_SAILS = DyedTags.BLOCKS.create(SIMULATED, "symmetric_sails")
        val HANDLES = DyedTags.BLOCKS.create(SIMULATED, "handles")
        val NAMEPLATES = DyedTags.BLOCKS.create(SIMULATED, "nameplate_blocks")
        val ENVELOPES = DyedTags.BLOCKS.create(AERONAUTICS, "envelope")
        val AIRTIGHT = DyedTags.BLOCKS.create(AERONAUTICS, "airtight")
    }

    object Items {
        val SYMMETRIC_SAILS = DyedTags.ITEMS.copy(Blocks.SYMMETRIC_SAILS)
        val HANDLES = DyedTags.ITEMS.copy(Blocks.HANDLES)
        val NAMEPLATES = DyedTags.ITEMS.copy(Blocks.NAMEPLATES)
        val ENVELOPES = DyedTags.ITEMS.copy(Blocks.ENVELOPES)
        val AIRTIGHT = DyedTags.ITEMS.copy(Blocks.AIRTIGHT)
        val SHAFTLESS_ENVELOPES = DyedTags.ITEMS.create(AERONAUTICS, "shaftless_envelope")
    }
}

package com.possible_triangle.dye_the_world.aeronautics

import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.aeronautics.data.envelopeBlockstate
import com.possible_triangle.dye_the_world.aeronautics.data.envelopeRecipe
import com.possible_triangle.dye_the_world.aeronautics.data.envelopeShaftBlockstate
import com.possible_triangle.dye_the_world.aeronautics.data.envelopeShaftItemModel
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.translation
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.RotatedPillarBlock

object DyedAeronautics {
    val ENVELOPES =
        REGISTRATE_AERONAUTICS.dyed { dye ->
            `object`("${dye}_envelope")
                .dyedBlock(dye)
                .lang("${dye.translation} Hot Air Envelope")
                .germanLang("${dye.germanTranslation(Genus.F)} Heißluftballonhülle")
                .optionalTag(Tags.Blocks.ENVELOPES)
                .optionalTag(Tags.Blocks.AIRTIGHT)
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .envelopeBlockstate()
                .withItem {
                    optionalTag(Tags.Items.ENVELOPES)
                    optionalTag(Tags.Items.SHAFTLESS_ENVELOPES)
                    envelopeRecipe()
                }.register()
        }

    val ENVELOPES_SHAFTS =
        REGISTRATE_AERONAUTICS.dyed { dye ->
            `object`("${dye}_envelope_encased_shaft")
                .dyedBlock(dye, ::RotatedPillarBlock)
                .lang("${dye.translation} Envelope Encased Shaft")
                .germanLang("${dye.germanTranslation(Genus.F)} Ballonummantelte Welle")
                .optionalTag(Tags.Blocks.ENVELOPES)
                .optionalTag(Tags.Blocks.AIRTIGHT)
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .envelopeShaftBlockstate()
                .loot { tables, block ->
                    tables.dropOther(block, ENVELOPES[dye]!!.get())
                }.withItem {
                    optionalTag(Tags.Items.ENVELOPES)
                    envelopeShaftItemModel()
                }.register()
        }
}

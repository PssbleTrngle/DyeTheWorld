package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_AERONAUTICS
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.data.envelopeBlockstate
import com.possible_triangle.dye_the_world.data.envelopeRecipe
import com.possible_triangle.dye_the_world.data.envelopeShaftBlockstate
import com.possible_triangle.dye_the_world.data.envelopeShaftItemModel
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock

object DyedAeronautics {

    private val REGISTRATE = DyedRegistrate.create(CREATE_AERONAUTICS)

    private val DYES = dyesFor(CREATE_AERONAUTICS)

    val ENVELOPES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_envelope")
            .dyedBlock(dye, ::Block)
            .lang("${dye.translation} Hot Air Envelope")
            .germanLang("${dye.germanTranslation(Genus.F)} Heißluftballonhülle")
            .optionalTag(DyedTags.Blocks.ENVELOPES)
            .optionalTag(BlockTags.MINEABLE_WITH_AXE)
            .envelopeBlockstate()
            .withItem {
                optionalTag(DyedTags.Items.ENVELOPES)
                optionalTag(DyedTags.Items.SHAFTLESS_ENVELOPES)
                envelopeRecipe()
            }
            .register()
    }


    val ENVELOPES_SHAFTS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_envelope_encased_shaft")
            .dyedBlock(dye, ::RotatedPillarBlock)
            .lang("${dye.translation} Envelope Encased Shaft")
            .germanLang("${dye.germanTranslation(Genus.F)} Ballonummantelte Welle")
            .optionalTag(DyedTags.Blocks.ENVELOPES)
            .optionalTag(BlockTags.MINEABLE_WITH_AXE)
            .envelopeShaftBlockstate()
            .loot { tables, block ->
                tables.dropOther(block, ENVELOPES[dye]!!.get())
            }
            .withItem {
                optionalTag(DyedTags.Items.ENVELOPES)
                envelopeShaftItemModel()
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}
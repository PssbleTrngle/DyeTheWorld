package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_SIMULATED
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.data.portableEngineBlockstate
import com.possible_triangle.dye_the_world.data.portableEngineItemModel
import com.possible_triangle.dye_the_world.data.sailBlockstate
import com.possible_triangle.dye_the_world.data.symmetricSailBlockstate
import com.possible_triangle.dye_the_world.data.valveRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import com.simibubi.create.AllTags
import dev.simulated_team.simulated.content.blocks.portable_engine.PortableEngineBlock
import dev.simulated_team.simulated.content.blocks.symmetric_sail.SymmetricSailBlock
import dev.simulated_team.simulated.index.SimTags
import net.minecraft.tags.BlockTags

object DyedSimulated {

    private val REGISTRATE = DyedRegistrate.create(CREATE_SIMULATED)

    private val DYES = dyesFor(CREATE_SIMULATED)

    val SYMMETRIC_SAILS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_symmetric_sail")
            .dyedBlock(dye) { SymmetricSailBlock.withCanvas(it, dye) }
            .lang("${dye.translation} Symmetric Sail")
            .germanLang("${dye.germanTranslation(Genus.I)} Symmetrisches Segel")
            .optionalTag(SimTags.Blocks.SYMMETRIC_SAILS)
            .symmetricSailBlockstate()
            .register()
    }

    val PORTABLE_ENGINES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_portable_engine")
            .dyedBlock(dye) { PortableEngineBlock(it, dye) }
            .lang("${dye.translation} Portable Engine")
            .germanLang("${dye.germanTranslation(Genus.F)} Tragbarer Antrieb")
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .portableEngineBlockstate()
            .withItem {
                portableEngineItemModel()
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}
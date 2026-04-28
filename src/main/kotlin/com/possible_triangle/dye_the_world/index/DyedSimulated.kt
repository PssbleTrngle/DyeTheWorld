package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_SIMULATED
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.data.AXIS_ALONG_FIRST_COORDINATE
import com.possible_triangle.dye_the_world.data.NAMEPLATE_POSITION
import com.possible_triangle.dye_the_world.data.handleBlockstate
import com.possible_triangle.dye_the_world.data.handleItemModel
import com.possible_triangle.dye_the_world.data.nameplateBlockstate
import com.possible_triangle.dye_the_world.data.nameplateItemModel
import com.possible_triangle.dye_the_world.data.portableEngineBlockstate
import com.possible_triangle.dye_the_world.data.portableEngineItemModel
import com.possible_triangle.dye_the_world.data.symmetricSailBlockstate
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.`object`.block.DummyBlock
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.simibubi.create.AllTags
import dev.simulated_team.simulated.index.SimBlocks
import dev.simulated_team.simulated.index.SimItems
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties

object DyedSimulated {

    private val REGISTRATE = DyedRegistrate.create(CREATE_SIMULATED)

    private val DYES = dyesFor(CREATE_SIMULATED)

    val SYMMETRIC_SAILS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_symmetric_sail")
            .dyedBlock(dye, ::RotatedPillarBlock)
            .lang("${dye.translation} Symmetric Sail")
            .germanLang("${dye.germanTranslation(Genus.I)} Symmetrisches Segel")
            .optionalTag(DyedTags.Blocks.SYMMETRIC_SAILS)
            .optionalTag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .symmetricSailBlockstate()
            .register()
    }

    val PORTABLE_ENGINES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_portable_engine")
            .dyedBlock(dye, DummyBlock.of(BlockStateProperties.HORIZONTAL_FACING))
            .lang("${dye.translation} Portable Engine")
            .germanLang("${dye.germanTranslation(Genus.F)} Tragbarer Antrieb")
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .portableEngineBlockstate()
            .withItem {
                portableEngineItemModel()
            }
            .register()
    }

    val HANDLES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_handle")
            .dyedBlock(dye, DummyBlock.of(AXIS_ALONG_FIRST_COORDINATE, BlockStateProperties.FACING))
            .lang("${dye.translation} Handle")
            .germanLang("${dye.germanTranslation(Genus.M)} Griff")
            .optionalTag(DyedTags.Blocks.HANDLES)
            .handleBlockstate()
            .withItem {
                handleItemModel()
                optionalTag(DyedTags.Items.HANDLES)
                recipe { context, provider ->
                    provider.dyeingRecipe(dye, DyedTags.Items.HANDLES, context)
                }
            }
            .register()
    }

    val NAMEPLATES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_nameplate")
            .dyedBlock(dye, DummyBlock.of(NAMEPLATE_POSITION, BlockStateProperties.HORIZONTAL_FACING))
            .lang("${dye.translation} Nameplate")
            .germanLang("${dye.germanTranslation(Genus.I)} Namensschild")
            .optionalTag(DyedTags.Blocks.NAMEPLATES)
            .optionalTag(BlockTags.MINEABLE_WITH_AXE)
            .nameplateBlockstate()
            .withItem {
                nameplateItemModel()
                optionalTag(DyedTags.Items.NAMEPLATES)
                recipe { context, provider ->
                    provider.dyeingRecipe(dye, DyedTags.Items.NAMEPLATES, context)
                }
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}
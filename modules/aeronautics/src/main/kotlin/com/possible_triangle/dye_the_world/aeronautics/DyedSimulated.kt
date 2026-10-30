package com.possible_triangle.dye_the_world.aeronautics

import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.aeronautics.data.*
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.world.block.DummyBlock
import com.simibubi.create.AllTags
import dev.simulated_team.simulated.index.SimBlocks
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties

object DyedSimulated {
    val SYMMETRIC_SAILS =
        REGISTRATE_SIMULATED.dyed { dye ->
            `object`("${dye}_symmetric_sail")
                .dyedBlock(dye, ::RotatedPillarBlock)
                .lang("${dye.translation} Symmetric Sail")
                .germanLang("${dye.germanTranslation(Genus.I)} Symmetrisches Segel")
                .optionalTag(Tags.Blocks.SYMMETRIC_SAILS)
                .optionalTag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
                .symmetricSailBlockstate()
                .register()
        }

    val PORTABLE_ENGINES =
        REGISTRATE_SIMULATED.dyed { dye ->
            `object`("${dye}_portable_engine")
                .dyedBlock(dye, DummyBlock.of(BlockStateProperties.HORIZONTAL_FACING))
                .lang("${dye.translation} Portable Engine")
                .germanLang("${dye.germanTranslation(Genus.F)} Tragbarer Antrieb")
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .portableEngineBlockstate()
                .withItem {
                    portableEngineItemModel()
                }.register()
        }

    val HANDLES =
        REGISTRATE_SIMULATED.dyed { dye ->
            `object`("${dye}_handle")
                .dyedBlock(dye, DummyBlock.of(AXIS_ALONG_FIRST_COORDINATE, BlockStateProperties.FACING))
                .lang("${dye.translation} Handle")
                .germanLang("${dye.germanTranslation(Genus.M)} Griff")
                .optionalTag(Tags.Blocks.HANDLES)
                .handleBlockstate()
                .withItem {
                    handleItemModel()
                    optionalTag(Tags.Items.HANDLES)
                    recipe { context, provider ->
                        provider.dyeingRecipe(dye, SimBlocks.IRON_HANDLE, context)
                    }
                }.register()
        }

    val NAMEPLATES =
        REGISTRATE_SIMULATED.dyed { dye ->
            `object`("${dye}_nameplate")
                .dyedBlock(dye, DummyBlock.of(NAMEPLATE_POSITION, BlockStateProperties.HORIZONTAL_FACING))
                .lang("${dye.translation} Nameplate")
                .germanLang("${dye.germanTranslation(Genus.I)} Namensschild")
                .optionalTag(Tags.Blocks.NAMEPLATES)
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .nameplateBlockstate()
                .withItem {
                    nameplateItemModel()
                    optionalTag(Tags.Items.NAMEPLATES)
                    recipe { context, provider ->
                        provider.dyeingRecipe(dye, Tags.Items.NAMEPLATES, context)
                    }
                }.register()
        }
}

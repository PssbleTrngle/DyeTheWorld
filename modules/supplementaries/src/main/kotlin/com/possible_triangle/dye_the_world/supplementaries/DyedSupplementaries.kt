package com.possible_triangle.dye_the_world.supplementaries

import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.supplementaries.data.*
import com.possible_triangle.dye_the_world.translation
import net.mehvahdjukaar.supplementaries.common.block.blocks.AwningBlock
import net.mehvahdjukaar.supplementaries.common.block.blocks.BuntingCeilingBlock
import net.mehvahdjukaar.supplementaries.common.block.blocks.BuntingWallBlock

object DyedSupplementaries {
    val BUNTINGS =
        REGISTRATE_SUPPLEMENTARIES.dyed { dye ->
            `object`("bunting_$dye")
                .dyedBlock(dye) { BuntingCeilingBlock(dye, it) }
                .lang("${dye.translation} Bunting")
                .buntingBlockState()
                .tag(SupplementariesTags.Blocks.BUNTINGS)
                .withItem {
                    tag(SupplementariesTags.Items.BUNTINGS)
                    dyedBuntingItemModel()
                    dyedBuntingRecipe()
                }.register()
        }

    val WALL_BUNTINGS =
        REGISTRATE_SUPPLEMENTARIES.dyed { dye ->
            `object`("bunting_wall_$dye")
                .dyedBlock(dye) { BuntingWallBlock(dye, it) }
                .lang("${dye.translation} Bunting")
                .wallBuntingBlockState()
                .tag(SupplementariesTags.Blocks.BUNTINGS)
                .register()
        }

    val AWNINGS =
        REGISTRATE_SUPPLEMENTARIES.dyed { dye ->
            `object`("awning_$dye")
                .dyedBlock(dye) { AwningBlock(dye, it) }
                .lang("${dye.translation} Awning")
                .tag(SupplementariesTags.Blocks.BOUNCY_BLOCKS)
                .tag(SupplementariesTags.Blocks.AWNINGS)
                .awningBlockstate()
                .withItem {
                    tag(SupplementariesTags.Items.AWNINGS)
                    awningItemModel()
                    awningRecipe()
                }.register()
        }

    init {
        REGISTRATE_SUPPLEMENTARIES.translateBannerPattern(SUPPLEMENTARIES.createId("dragon"), "Dragon Charge")
    }
}

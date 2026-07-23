package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.AMENDMENTS
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES_SQUARED
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import net.mehvahdjukaar.supplementaries.common.block.blocks.AwningBlock
import net.mehvahdjukaar.supplementaries.common.block.blocks.BuntingCeilingBlock
import net.mehvahdjukaar.supplementaries.common.block.blocks.BuntingWallBlock
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.ColorRGBA
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block

private val DyeColor.rgba get() = ColorRGBA(textColor)

object DyedSupplementaries {
    private val DYES = dyesFor(SUPPLEMENTARIES)

    private val REGISTRATE = DyedRegistrate.create(SUPPLEMENTARIES)
    private val REGISTRATE_AMENDMENTS = DyedRegistrate.create(AMENDMENTS)
    private val SQUARED_REGISTRATE = DyedRegistrate.create(SUPPLEMENTARIES_SQUARED)

    val SACKS =
        DYES.associateWith { dye ->
            SQUARED_REGISTRATE
                .`object`("sack_$dye")
                .dyedBlock(dye) { SackBlock(dye.rgba, it) }
                .lang("${dye.translation} Sack")
                .sackBlockstate()
                .loot { t, b -> t.add(b, t.createShulkerBoxDrop(b)) }
                .withItem {
                    sackItemModel()
                }.register()
        }

    val CEILING_BANNERS =
        DYES.associateWith { dye ->
            REGISTRATE_AMENDMENTS
                .`object`("ceiling_banner_$dye")
                .dyedBlock(dye, ::Block)
                .lang("${dye.translation} Banner")
                .optionalTag(DyedTags.Blocks.CEILING_BANNERS)
                .blockstate { context, provider ->
                    val model = provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/banner"))
                    provider.simpleBlock(context.get(), model)
                }.register()
        }

    val BUNTINGS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("bunting_$dye")
                .dyedBlock(dye) { BuntingCeilingBlock(dye, it) }
                .lang("${dye.translation} Bunting")
                .buntingBlockState()
                .withItem {
                    dyedBuntingItemModel()
                    dyedBuntingRecipe()
                }.register()
        }

    val WALL_BUNTINGS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("bunting_wall_$dye")
                .dyedBlock(dye) { BuntingWallBlock(dye, it) }
                .lang("${dye.translation} Bunting")
                .wallBuntingBlockState()
                .register()
        }

    val AWNINGS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("awning_$dye")
                .dyedBlock(dye) { AwningBlock(dye, it) }
                .lang("${dye.translation} Awning")
                .optionalTag(DyedTags.Blocks.MINEABLE_SHEAR)
                .optionalTag(DyedTags.Blocks.BOUNCY_BLOCKS)
                .optionalTag(DyedTags.Blocks.AWNINGS)
                .awningBlockstate()
                .withItem {
                    optionalTag(DyedTags.Items.AWNINGS)
                    awningItemModel()
                    awningRecipe()
                }.register()
        }

    fun register() {
        REGISTRATE.register()
        SQUARED_REGISTRATE.register()
        REGISTRATE_AMENDMENTS.register()
    }
}

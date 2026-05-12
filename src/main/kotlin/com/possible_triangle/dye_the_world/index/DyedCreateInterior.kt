package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_INTERIORS
import com.possible_triangle.dye_the_world.data.chairBlockstate
import com.possible_triangle.dye_the_world.data.chairItemModel
import com.possible_triangle.dye_the_world.data.chairRecipe
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.`object`.block.DummyChairBlock
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.block.Block

object DyedCreateInterior {
    private val DYES = dyesFor(CREATE_INTERIORS)

    private val REGISTRATE = DyedRegistrate.create(CREATE_INTERIORS)

    val CHAIRS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_chair")
                .dyedBlock(dye, ::DummyChairBlock)
                .lang("${dye.translation} Chair")
                .germanLang("${dye.germanTranslation(Genus.M)} Stuhl")
                .optionalTag(DyedTags.Blocks.CHAIRS)
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .chairBlockstate()
                .withItem {
                    optionalTag(DyedTags.Items.CHAIRS)
                    chairItemModel()
                    chairRecipe()
                }.register()
        }

    val FLOOR_CHAIRS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_floor_chair")
                .dyedBlock(dye, ::DummyChairBlock)
                .lang("${dye.translation} Floor Chair")
                .optionalTag(DyedTags.Blocks.FLOOR_CHAIRS)
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .chairBlockstate()
                .withItem {
                    optionalTag(DyedTags.Items.FLOOR_CHAIRS)
                    chairItemModel()
                    chairRecipe()
                }.register()
        }

    val CUSHIONS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_cushion")
                .dyedBlock(dye, ::Block)
                .lang("${dye.translation} Cushion")
                .germanLang("${dye.germanTranslation(Genus.I)} Kissen")
                .optionalTag(BlockTags.WOOL)
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .optionalTag(DyedTags.Blocks.MINEABLE_KNIFE)
                .blockstate { c, p ->
                    val texture = Constants.MOD_ID.createId("block/$CREATE/seat/top_$dye")
                    p.simpleBlock(c.get(), p.models().cubeAll(c.name, texture))
                }.withItem {
                    recipe { c, p ->
                        ShapelessRecipeBuilder
                            .shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                            .requiresUnlocking(dye.blockOf("wool"))
                            .requires(ItemTags.PLANKS)
                            .save(p)
                    }
                }.register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

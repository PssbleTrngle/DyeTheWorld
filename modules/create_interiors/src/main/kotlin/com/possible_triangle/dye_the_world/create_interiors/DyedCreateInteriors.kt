package com.possible_triangle.dye_the_world.create_interiors

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.create.CREATE
import com.possible_triangle.dye_the_world.create_interiors.data.chairBlockstate
import com.possible_triangle.dye_the_world.create_interiors.data.chairItemModel
import com.possible_triangle.dye_the_world.create_interiors.data.chairRecipe
import com.possible_triangle.dye_the_world.create_interiors.world.block.DummyChairBlock
import com.possible_triangle.dye_the_world.extensions.*
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags

object DyedCreateInteriors {
    val CHAIRS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_chair")
                .dyedBlock(dye, ::DummyChairBlock)
                .lang("${dye.translation} Chair")
                .germanLang("${dye.germanTranslation(Genus.M)} Stuhl")
                .tag(CreateInteriorsTags.Blocks.CHAIRS)
                .tag(BlockTags.MINEABLE_WITH_AXE)
                .chairBlockstate()
                .withItem {
                    tag(CreateInteriorsTags.Items.CHAIRS)
                    chairItemModel()
                    chairRecipe()
                }.register()
        }

    val FLOOR_CHAIRS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_floor_chair")
                .dyedBlock(dye, ::DummyChairBlock)
                .lang("${dye.translation} Floor Chair")
                .tag(CreateInteriorsTags.Blocks.FLOOR_CHAIRS)
                .tag(BlockTags.MINEABLE_WITH_AXE)
                .chairBlockstate()
                .withItem {
                    tag(CreateInteriorsTags.Items.FLOOR_CHAIRS)
                    chairItemModel()
                    chairRecipe()
                }.register()
        }

    val CUSHIONS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_cushion")
                .dyedBlock(dye)
                .lang("${dye.translation} Cushion")
                .germanLang("${dye.germanTranslation(Genus.I)} Kissen")
                .tag(BlockTags.WOOL)
                .tag(BlockTags.MINEABLE_WITH_AXE)
                .blockstate { c, p ->
                    val texture = CREATE.createId("block/seat/top_$dye")
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
}

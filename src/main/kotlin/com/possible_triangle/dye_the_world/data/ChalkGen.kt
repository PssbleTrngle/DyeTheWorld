package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CHALK
import com.possible_triangle.dye_the_world.DEPOT_DYES
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.client.model.generators.ModelFile

fun <T : Block, P> BlockBuilder<T, P>.chalkBlockstate() =
    blockstate { context, provider ->
        val model = provider.models().getExistingFile(CHALK.createId("block/chalk_mark"))
        provider.simpleBlock(context.get(), model)
    }

fun <T : Item, P> ItemBuilder<T, P>.chalkRecipe() =
    recipe { context, provider ->
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.MISC, context.get())
            .requiresUnlocking(Blocks.CALCITE)
            .requiresUnlocking(dye.tag)
            .group("$CHALK:chalk")
            .save(provider)
    }

fun <T : Item, P> ItemBuilder<T, P>.chalkItemModel() =
    model { context, provider ->
        provider.generated(context, Constants.MOD_ID.createId("item/$CHALK/$dye"))
    }

fun <T : Item, P> ItemBuilder<T, P>.chalkBoxModel() =
    model { context, provider ->
        val model = provider.generated(context, CHALK.createId("item/chalk_box"))

        DEPOT_DYES.forEach { dye ->
            provider
                .getBuilder("item/chalk_box_$dye")
                .parent(ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", CHALK.createId("item/chalk_box"))
                .texture("layer1", Constants.MOD_ID.createId("item/$CHALK/box_$dye"))
        }

        DyeColor.entries.forEach { dye ->
            model
                .override()
                .model(provider.getExistingFile(CHALK.createId("item/chalk_box_$dye")))
                .predicate(CHALK.createId("selected"), 1F + dye.id)
                .end()
        }
    }

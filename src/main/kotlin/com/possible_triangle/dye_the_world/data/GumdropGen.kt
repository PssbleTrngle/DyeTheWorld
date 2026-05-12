package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SNOWY_SPIRIT
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.snowyspirit.common.block.GumdropButton
import net.minecraft.core.Direction
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.gumdropBlockstate() =
    blockstate { context, provider ->
        val models =
            listOf(true, false).associateWith { powered ->
                val suffix = if (powered) "_pressed" else ""
                provider
                    .models()
                    .getBuilder(context.name + suffix)
                    .parent(provider.models().getExistingFile(SNOWY_SPIRIT.createId("block/gumdrop$suffix")))
                    .texture("particle", Constants.MOD_ID.createId("block/$SNOWY_SPIRIT/gumdrop/$dye"))
            }

        provider.createVariant(context) { state ->
            val facing = state.getValue(GumdropButton.FACING)
            val powered = state.getValue(GumdropButton.POWERED)
            val model = models[powered]!!

            val xRot =
                when (facing) {
                    Direction.DOWN -> 180
                    Direction.UP -> 0
                    else -> 90
                }

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationX(xRot)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.gumdropModel() =
    model { context, provider ->
        provider
            .withExistingParent(context.name, SNOWY_SPIRIT.createId("item/gumdrop"))
            .texture("texture", Constants.MOD_ID.createId("block/$SNOWY_SPIRIT/gumdrop/$dye"))
    }

fun <T : Item, P> ItemBuilder<T, P>.gumdropRecipe() =
    recipe { context, provider ->
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.MISC, context.get())
            .group("gumdrop")
            .requiresUnlocking(Items.SUGAR)
            .requires(dye.tag)
            .save(provider)
    }

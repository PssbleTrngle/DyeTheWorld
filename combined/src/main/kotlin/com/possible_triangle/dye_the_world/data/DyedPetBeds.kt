package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.REDOMESTICATE
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.index.DyedTags
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.petBedBlockstate() =
    blockstate { context, provider ->
        val model =
            provider
                .models()
                .withExistingParent(context.name, REDOMESTICATE.createId("block/pet_bed"))
                .texture("bed", REDOMESTICATE.createId("block/${context.name}"))

        provider.createVariant(context) { state ->
            val facing = state.getValue(HorizontalDirectionalBlock.FACING)

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.petBedRecipe() =
    recipe { context, provider ->
        val wool = dye.blockOf("wool")

        shapeless(RecipeCategory.DECORATIONS, context.get())
            .requiresUnlocking(ItemTags.PLANKS)
            .requiresUnlocking(Items.BONE)
            .requires(wool)
            .save(provider)

        provider.dyeingRecipe(dye, DyedTags.Items.PET_BEDS, context)
    }

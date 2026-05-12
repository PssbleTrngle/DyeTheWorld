package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.DOMESTICATION_INNOVATION
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.index.DyedTags
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.petBedBlockstate() =
    blockstate { context, provider ->
        val model =
            provider
                .models()
                .withExistingParent(context.name, DOMESTICATION_INNOVATION.createId("block/pet_bed"))
                .texture("bed", Constants.MOD_ID.createId("block/$DOMESTICATION_INNOVATION/${context.name}"))

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

        ShapedRecipeBuilder
            .shaped(RecipeCategory.DECORATIONS, context.get())
            .pattern("WWW")
            .pattern("PBP")
            .define('W', wool)
            .define('P', ItemTags.PLANKS)
            .define('B', Items.BONE)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .unlockedBy("has_bone", RegistrateRecipeProvider.has(Items.BONE))
            .save(provider)

        provider.dyeingRecipe(dye, DyedTags.Items.PET_BEDS, context)
    }

package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_INTERIORS
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.index.DyedTags
import com.possible_triangle.dye_the_world.`object`.block.DummyChairBlock
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <P> BlockBuilder<DummyChairBlock, P>.chairBlockstate() =
    blockstate { context, provider ->
        provider.createVariant(context) { state ->
            val armrests = state.getValue(DummyChairBlock.ARMRESTS)
            val facing = state.getValue(DummyChairBlock.FACING)
            val croppedBack = state.getValue(DummyChairBlock.CROPPED_BACK)

            val type = armrests.serializedName + if (croppedBack) "_cropped" else ""
            val parent = CREATE_INTERIORS.createId("block/${context.name.substring(dye.serializedName.length + 1)}/$type")

            val model =
                provider
                    .models()
                    .withExistingParent("${context.name}_$type", parent)
                    .texture("side", Constants.MOD_ID.createId("block/$CREATE/seat/side_$dye"))
                    .texture("side_front", Constants.MOD_ID.createId("block/$CREATE/seat/side_$dye"))
                    .texture("side_top", Constants.MOD_ID.createId("block/$CREATE_INTERIORS/chair/side_top_$dye"))
                    .texture("top", Constants.MOD_ID.createId("block/$CREATE/seat/top_$dye"))

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.chairRecipe() =
    recipe { context, provider ->
        val floor = context.name.endsWith("_floor_chair")
        val seat = BuiltInRegistries.BLOCK.getOrThrow(CREATE.createId("${dye}_seat"))
        val secondaryIngredient = if (floor) ItemTags.WOODEN_SLABS else ItemTags.PLANKS

        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
            .requires(secondaryIngredient)
            .requires(seat)
            .unlockedBy(dye.blockOf("wool"))
            .save(provider, provider.safeId(context.get()).withSuffix("_from_seat"))

        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
            .requires(ItemTags.WOODEN_SLABS)
            .requires(secondaryIngredient)
            .requiresUnlocking(dye.blockOf("wool"))
            .save(provider)

        if (floor) {
            provider.dyeingRecipe(dye, DyedTags.Items.FLOOR_CHAIRS, context)
        } else {
            val floor = BuiltInRegistries.BLOCK.getOrThrow(context.id.withPath { it.replace("_chair", "_floor_chair") })

            ShapelessRecipeBuilder
                .shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
                .requires(ItemTags.WOODEN_SLABS)
                .requires(floor)
                .unlockedBy(dye.blockOf("wool"))
                .save(provider, provider.safeId(context.get()).withSuffix("_from_floor_chair"))

            provider.dyeingRecipe(dye, DyedTags.Items.CHAIRS, context)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.chairItemModel() =
    model { context, provider ->
        provider.withExistingParent(context.name, CREATE_INTERIORS.createId("block/${context.name}_both"))
    }

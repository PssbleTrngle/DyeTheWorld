package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.getOrThrow
import com.possible_triangle.dye_the_world.index.DyedQuark
import com.possible_triangle.dye_the_world.index.DyedQuark.flagCondition
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.multikulti.datagen.conditions.withConditions
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Item

fun <I : Item, P> ItemBuilder<I, P>.framedGlassRecipes() =
    recipe { context, provider ->
        provider.withConditions(flagCondition("framed_glass")) {
            val framedGlass = BuiltInRegistries.BLOCK.getOrThrow(QUARK.createId("framed_glass"))

            ShapelessRecipeBuilder
                .shapeless(RecipeCategory.BUILDING_BLOCKS, context.get(), 8)
                .requires(framedGlass)
                .requires(framedGlass)
                .requires(framedGlass)
                .requires(framedGlass)
                .requires(framedGlass)
                .requires(framedGlass)
                .requires(framedGlass)
                .requires(framedGlass)
                .requires(dye.tag)
                .unlockedBy("has_framed_glass", RegistrateRecipeProvider.has(framedGlass))
                .unlockedBy("has_dye", RegistrateRecipeProvider.has(dye.tag))
                .save(provider)
        }
    }

fun <I : Item, P> ItemBuilder<I, P>.framedGlassPaneRecipes() =
    recipe { context, provider ->
        provider.withConditions(flagCondition("framed_glass")) {
            val framedGlass = DyedQuark.FRAMED_GLASS[dye]!!.get()

            ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 16)
                .pattern("###")
                .pattern("###")
                .define('#', framedGlass)
                .unlockedBy("has_framed_glass", RegistrateRecipeProvider.has(framedGlass))
                .save(provider)
        }
    }

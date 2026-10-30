package com.possible_triangle.dye_the_world.quark.data

import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.texture
import com.possible_triangle.dye_the_world.quark.flagCondition
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.multikulti.datagen.conditions.withConditions
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ConfiguredModel
import org.violetmoon.quark.content.building.block.StoolBlock
import org.violetmoon.quark.content.building.module.StoolsModule

fun <T : Item, P> ItemBuilder<T, P>.stoolRecipe() =
    recipe { context, provider ->
        provider.withConditions(flagCondition("stools")) {
            val wool = dye.blockOf("wool")
            ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
                .group("stools")
                .pattern("#W#")
                .pattern("WWW")
                .define('#', ItemTags.WOODEN_SLABS)
                .define('W', wool)
                .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
                .save(provider)

            provider.dyeingRecipe(dye, StoolsModule.stools.first(), context) {
                group("stools")
            }
        }
    }

fun <T : StoolBlock, P> BlockBuilder<T, P>.stoolBlockstate() =
    blockstate { context, provider ->
        provider.createVariant(context) { state ->
            val big = state.getValue(StoolBlock.BIG)

            val prefix = if (big) "big_" else ""
            val parent = QUARK.createId("block/${prefix}stool")
            val model =
                provider
                    .models()
                    .withExistingParent("block/$prefix${context.name}", parent)
                    .texture("main", context.texture)

            ConfiguredModel
                .builder()
                .modelFile(model)
        }
    }

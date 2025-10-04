package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.starfish_studios.another_furniture.block.StoolBlock
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Item, P> ItemBuilder<T, P>.stoolRecipes() = recipe { context, provider ->
    val wool = dye.blockOf("wool")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 3)
        .group("stools")
        .pattern("#W#")
        .pattern("/ /")
        .define('#', ItemTags.PLANKS)
        .define('W', wool)
        .define('/', Items.STICK)
        .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
        .save(provider)

    provider.dyeingRecipe(dye, AFBlocks.WHITE_STOOL.get(), context) {
        group("stools")
    }
}

fun <T : Block, P> BlockBuilder<T, P>.stoolBlockstate() = blockstate { context, provider ->
    fun texture(suffix: String) = Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/stool/${dye}_$suffix")

    provider.createVariant(context) { state ->
        val low = state.getValue(StoolBlock.LOW)

        val suffix = if (low) "_low" else ""
        val parent = ANOTHER_FURNITURE.createId("block/template/stool$suffix")
        val model = provider.models().withExistingParent(context.name + suffix, parent)
            .texture("side", texture("side"))
            .texture("top", texture("top"))

        ConfiguredModel.builder()
            .modelFile(model)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.tallStoolRecipes() = recipe { context, provider ->
    val wool = dye.blockOf("wool")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 3)
        .group("tall_stools")
        .pattern("#W#")
        .pattern("/ /")
        .pattern("/ /")
        .define('#', ItemTags.PLANKS)
        .define('W', wool)
        .define('/', Items.STICK)
        .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
        .save(provider)

    provider.dyeingRecipe(dye, AFBlocks.WHITE_TALL_STOOL.get(), context) {
        group("tall_stools")
    }
}

fun <T : Block, P> BlockBuilder<T, P>.tallStoolBlockstate() = blockstate { context, provider ->
    fun texture(suffix: String) = Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/tall_stool/${dye}_$suffix")

    val parent = ANOTHER_FURNITURE.createId("block/template/tall_stool")
    val model = provider.models().withExistingParent(context.name, parent)
        .texture("top", texture("top"))

    provider.simpleBlock(context.get(), model)
}
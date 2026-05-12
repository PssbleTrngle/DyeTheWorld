package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.starfish_studios.another_furniture.block.LampBlock
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Item, P> ItemBuilder<T, P>.lampRecipes() =
    recipe { context, provider ->
        val wool = dye.blockOf("wool")
        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 3)
            .group("lamps")
            .pattern(" W ")
            .pattern("WTW")
            .pattern(" / ")
            .define('W', wool)
            .define('T', Items.TORCH)
            .define('/', Items.STICK)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .save(provider)

        provider.dyeingRecipe(dye, AFBlocks.WHITE_LAMP.get(), context) {
            group("lamps")
        }
    }

fun <T : Block, P> BlockBuilder<T, P>.lampBlockstate() =
    blockstate { context, provider ->
        fun texture(suffix: String): ResourceLocation = Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/lamp/${dye}_$suffix")

        provider.createVariant(context) { state ->
            val facing = state.getValue(LampBlock.FACING)

            val lit = state.getValue(LampBlock.LIT)
            val base = state.getValue(LampBlock.BASE)

            val wall = facing.axis.isHorizontal
            val litSuffix = if (lit) "" else "_off"
            val baseSuffix = if (base || wall) "" else "_tall_top"
            val typeSuffix = if (wall) "_wall" else ""
            val suffix = typeSuffix + baseSuffix + litSuffix

            val parent = ANOTHER_FURNITURE.createId("block/template/lamp$suffix")

            val model =
                provider
                    .models()
                    .withExistingParent("block/$ANOTHER_FURNITURE/lamp/${dye}$suffix", parent)
                    .texture("top", texture("top"))
                    .texture("side", texture("side"))

            ConfiguredModel
                .builder()
                .rotationY(facing.yRot)
                .modelFile(model)
        }
    }

fun <T : Block, P> BlockBuilder<T, P>.lampConnectorBlockstate() =
    blockstate { context, provider ->
        fun texture(suffix: String): ResourceLocation = Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/lamp/${dye}_$suffix")

        provider.createVariant(context) { state ->
            val base = state.getValue(LampBlock.BASE)

            val suffix = if (base) "base" else "middle"

            val parent = ANOTHER_FURNITURE.createId("block/template/lamp_tall_$suffix")

            val model =
                provider
                    .models()
                    .withExistingParent("block/$ANOTHER_FURNITURE/lamp_tall/${dye}_$suffix", parent)
                    .texture("top", texture("top"))
                    .texture("side", texture("side"))

            ConfiguredModel.builder().modelFile(model)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.lampItemModel() =
    model { context, provider ->
        provider.withExistingParent(context.name, Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/lamp/$dye"))
    }

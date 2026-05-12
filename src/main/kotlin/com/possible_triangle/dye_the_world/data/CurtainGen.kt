package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.matchesState
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.starfish_studios.another_furniture.block.CurtainBlock
import com.starfish_studios.another_furniture.block.properties.HorizontalConnectionType
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.Direction
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Item, P> ItemBuilder<T, P>.curtainRecipes() =
    recipe { context, provider ->
        val wool = dye.blockOf("wool")
        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 3)
            .group("curtains")
            .pattern("//")
            .pattern("##")
            .pattern("##")
            .define('#', wool)
            .define('/', Items.STICK)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .save(provider)

        provider.dyeingRecipe(dye, AFBlocks.WHITE_CURTAIN.get(), context) {
            group("curtains")
        }
    }

fun <T : Block, P> BlockBuilder<T, P>.curtainBlockstate() =
    blockstate { context, provider ->
        fun texture(suffix: String): ResourceLocation = Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/curtain2/${dye}$suffix")

        provider.createVariant(context) model@{ state ->
            val facing = state.getValue(CurtainBlock.FACING)
            val vertical = state.getValue(CurtainBlock.VERTICAL_CONNECTION_TYPE)
            val horizontal = state.getValue(CurtainBlock.HORIZONTAL_CONNECTION_TYPE)

            if (!state.getValue(CurtainBlock.OPEN)) {
                val openState = if (vertical == Direction.UP) "top" else "bottom"
                val suffix = "_closed_$openState"
                val parent = ANOTHER_FURNITURE.createId("block/template/curtain$suffix")
                val model =
                    provider
                        .models()
                        .withExistingParent("$dye$suffix", parent)
                        .texture("curtain", texture(suffix))

                return@model ConfiguredModel
                    .builder()
                    .modelFile(model)
                    .rotationY(facing.yRot)
            }

            val suffixH =
                when (horizontal) {
                    HorizontalConnectionType.LEFT -> "_left"
                    HorizontalConnectionType.RIGHT -> "_right"
                    else -> "_middle"
                }

            val isMiddle = suffixH == "_middle"

            if (isMiddle && vertical == Direction.DOWN) {
                return@model ConfiguredModel
                    .builder()
                    .modelFile(provider.models().getExistingFile(ResourceLocation("block/air")))
            }

            val suffixV =
                if (isMiddle) {
                    ""
                } else {
                    when (vertical) {
                        Direction.UP -> "_top"
                        else -> "_bottom"
                    }
                }

            val suffix = suffixH + suffixV
            val parent = ANOTHER_FURNITURE.createId("block/template/curtain$suffix")

            val texturePrefix =
                if (isMiddle) {
                    "_middle"
                } else {
                    "_open"
                }

            val model =
                provider
                    .models()
                    .withExistingParent("${context.name}$suffix", parent)
                    .texture("curtain", texture(texturePrefix + suffixV))

            ConfiguredModel
                .builder()
                .rotationY(facing.yRot)
                .modelFile(model)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.curtainItemModel() =
    model { context, provider ->
        provider
            .withExistingParent(context.name, ANOTHER_FURNITURE.createId("item/template/curtain"))
            .texture("all", Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/curtain/$dye"))
    }

fun <T : Block, P> BlockBuilder<T, P>.curtainLoot() =
    loot { tables, block ->
        val isTop =
            matchesState(block) {
                hasProperty(CurtainBlock.VERTICAL_CONNECTION_TYPE, Direction.UP)
            }

        val pool =
            tables
                .applyExplosionDecay(block, LootPool.lootPool())
                .add(LootItem.lootTableItem(block))
                .setRolls(ConstantValue.exactly(1.0F))
                .`when`(isTop)

        tables.add(block, LootTable.lootTable().withPool(pool))
    }

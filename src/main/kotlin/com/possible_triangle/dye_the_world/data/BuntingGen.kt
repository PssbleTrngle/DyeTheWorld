package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.textureAndParticle
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.supplementaries.reg.ModRegistry
import net.minecraft.core.Direction
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Item, P> ItemBuilder<T, P>.dyedBuntingItemModel() =
    model { context, provider ->
        provider.generated(context, Constants.MOD_ID.createId("item/$SUPPLEMENTARIES/buntings/$dye"))
    }

fun <T : Block, P> BlockBuilder<T, P>.buntingBlockState() =
    blockstate { context, provider ->
        val parent = SUPPLEMENTARIES.createId("block/buntings/white_ceiling")
        val texture = SUPPLEMENTARIES.createId("block/buntings/wall_bunting_$dye")

        val model =
            provider
                .models()
                .withExistingParent("${context.name}_ceiling", parent)
                .textureAndParticle("0", texture)

        provider.createVariant(context) { state ->
            val axis = state.getValue(BlockStateProperties.HORIZONTAL_AXIS)

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(if (axis == Direction.Axis.X) 90 else 0)
        }
    }

fun <T : Block, P> BlockBuilder<T, P>.wallBuntingBlockState() =
    blockstate { context, provider ->
        val parent = SUPPLEMENTARIES.createId("block/buntings/white_wall")
        val texture = SUPPLEMENTARIES.createId("block/buntings/wall_bunting_$dye")

        val model =
            provider
                .models()
                .withExistingParent("${context.name}_wall", parent)
                .textureAndParticle("0", texture)

        provider.createVariant(context) { state ->
            val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.dyedBuntingRecipe() =
    recipe { context, provider ->
        ShapedRecipeBuilder
            .shaped(RecipeCategory.DECORATIONS, context.get(), 6)
            .pattern("SSS")
            .pattern("WWW")
            .pattern(" W ")
            .defineUnlocking('W', dye.blockOf("wool"))
            .defineUnlocking('S', Items.STRING)
            .save(provider, Constants.MOD_ID.createId("bunting_$dye"))

        provider.dyeingRecipe(
            dye,
            ModRegistry.BUNTING_BLOCKS[DyeColor.WHITE]!!.get(),
            context,
            id = Constants.MOD_ID.createId("bunting_${dye}_dyeing"),
        ) {
            group("bunting")
        }
    }

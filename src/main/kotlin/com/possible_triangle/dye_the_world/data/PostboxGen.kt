package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.simibubi.create.AllItems
import com.simibubi.create.AllTags
import com.simibubi.create.content.logistics.packagePort.postbox.PostboxBlock
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Item, P> ItemBuilder<T, P>.postboxItemModel() =
    model { context, provider ->
        provider
            .withExistingParent(context.name, CREATE.createId("block/package_postbox/item"))
            .texture("0", Constants.MOD_ID.createId("block/$CREATE/postbox/$dye"))
            .texture("1", Constants.MOD_ID.createId("block/$CREATE/postbox/${dye}_closed"))
    }

fun <T : PostboxBlock, P> BlockBuilder<T, P>.postboxBlockstate() =
    blockstate { context, provider ->
        val (open, closed) =
            listOf("open", "closed").map { suffix ->
                provider
                    .models()
                    .withExistingParent("${context.name}_$suffix", CREATE.createId("block/package_postbox/block_$suffix"))
                    .texture("0", Constants.MOD_ID.createId("block/$CREATE/postbox/$dye"))
                    .texture("1", Constants.MOD_ID.createId("block/$CREATE/postbox/${dye}_$suffix"))
            }

        provider.createVariant(context) { state ->
            val isOpen = state.getValue(PostboxBlock.OPEN)
            val facing = state.getValue(PostboxBlock.FACING)

            ConfiguredModel
                .builder()
                .modelFile(if (isOpen) open else closed)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.postboxRecipe() =
    recipe { context, provider ->
        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
            .pattern("D")
            .pattern("B")
            .pattern("A")
            .define('A', AllItems.ANDESITE_ALLOY.get())
            .defineUnlocking('B', Items.BARREL)
            .define('D', dye.tag)
            .save(provider)

        provider.dyeingRecipe(dye, AllTags.AllItemTags.POSTBOXES.tag, context)
    }

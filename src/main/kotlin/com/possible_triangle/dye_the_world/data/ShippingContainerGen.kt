package com.possible_triangle.dye_the_world.data

import com.github.talrey.createdeco.blocks.ShippingContainerBlock
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_DECO
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.index.DyedTags
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.simibubi.create.AllBlocks
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.core.Direction
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : ShippingContainerBlock, P> BlockBuilder<T, P>.shippingContainerBlockstate() =
    blockstate { context, provider ->
        val dye = context.get().COLOR
        fun texture(suffix: String): ResourceLocation =
            CREATE_DECO.createId("block/palettes/shipping_containers/${dye}/vault_${suffix}_small")

        val model = provider.models()
            .withExistingParent(context.name, CREATE_DECO.createId("block/shipping_container"))
            .texture("0", texture("bottom"))
            .texture("1", texture("front"))
            .texture("2", texture("side"))
            .texture("3", texture("top"))
            .texture("particle", texture("top"))

        provider.createVariant(context, ShippingContainerBlock.LARGE) { state ->
            val axis = state.getValue(ShippingContainerBlock.HORIZONTAL_AXIS)

            val yRot = if (axis == Direction.Axis.X) 90 else 0

            ConfiguredModel.builder()
                .modelFile(model)
                .rotationY(yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.shippingContainerRecipe() = recipe { context, provider ->
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, context.get())
        .pattern("CS")
        .pattern("SB")
        .defineUnlocking('B', Blocks.BARREL)
        .defineUnlocking('C', dye.tag)
        .defineUnlocking('S', DyedTags.Items.IRON_PLATES)
        .save(provider)

    provider.dyeingRecipe(dye, AllBlocks.ITEM_VAULT.get(), context)
}
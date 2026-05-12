package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.simibubi.create.AllTags
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

fun <T : Block, P> BlockBuilder<T, P>.seatBlockstate() =
    blockstate { context, provider ->
        val parent = CREATE.createId("block/seat")
        val model =
            provider
                .models()
                .withExistingParent(context.name, parent)
                .texture("1", Constants.MOD_ID.createId("block/$CREATE/seat/top_$dye"))
                .texture("2", Constants.MOD_ID.createId("block/$CREATE/seat/side_$dye"))

        provider.simpleBlock(context.get(), model)
    }

fun <T : Item, P> ItemBuilder<T, P>.seatRecipe() =
    recipe { context, provider ->
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
            .requiresUnlocking(dye.blockOf("wool"))
            .requires(ItemTags.WOODEN_SLABS)
            .save(provider)

        provider.dyeingRecipe(dye, AllTags.AllItemTags.SEATS.tag, context)
    }

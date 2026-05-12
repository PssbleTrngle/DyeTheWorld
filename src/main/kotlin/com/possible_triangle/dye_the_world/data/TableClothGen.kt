package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.extensions.unlockedBy
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.simibubi.create.AllItems
import com.simibubi.create.AllTags
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Item

fun <T : Item, P> ItemBuilder<T, P>.tableClothItemModel() =
    model { context, provider ->
        provider
            .withExistingParent(context.name, CREATE.createId("block/table_cloth/item"))
            .texture("0", Constants.MOD_ID.createId("block/$CREATE/table_cloth/$dye"))
    }

fun <T : TableClothBlock, P> BlockBuilder<T, P>.tableClothBlockstate() =
    blockstate { context, provider ->
        val model =
            provider
                .models()
                .withExistingParent(context.name, CREATE.createId("block/table_cloth/block"))
                .texture("0", Constants.MOD_ID.createId("block/$CREATE/table_cloth/$dye"))

        provider.simpleBlock(context.get(), model)
    }

fun <T : Item, P> ItemBuilder<T, P>.tableClothRecipe() =
    recipe { context, provider ->
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.BUILDING_BLOCKS, context.get(), 2)
            .requiresUnlocking(AllItems.ANDESITE_ALLOY.get())
            .requires(dye.blockOf("wool"))
            .save(provider)

        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
            .requires(context.get())
            .unlockedBy(AllItems.ANDESITE_ALLOY.get())
            .save(provider, provider.safeId(context.get()).withSuffix("_clear"))

        provider.dyeingRecipe(dye, AllTags.AllItemTags.DYED_TABLE_CLOTHS.tag, context)
    }

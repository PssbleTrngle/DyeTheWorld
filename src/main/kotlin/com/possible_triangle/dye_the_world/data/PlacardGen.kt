package com.possible_triangle.dye_the_world.data

import com.github.talrey.createdeco.api.CDTags
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_DECO
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.simibubi.create.AllItems
import com.simibubi.create.content.decoration.placard.PlacardBlock
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items

fun <T : PlacardBlock, P> BlockBuilder<T, P>.placardBlockstate() =
    blockstate { context, provider ->
        val texture = Constants.MOD_ID.createId("block/$CREATE_DECO/placard/$dye")

        val model =
            provider
                .models()
                .withExistingParent(context.name, CREATE_DECO.createId("block/dyed_placard"))
                .texture("0", texture)
                .texture("particle", texture)

        provider.horizontalFaceBlock(context.get(), model)
    }

fun <T : Item, P> ItemBuilder<T, P>.placardRecipe() =
    recipe { context, provider ->
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.DECORATIONS, context.get())
            .requiresUnlocking(Items.ITEM_FRAME)
            .requiresUnlocking(AllItems.BRASS_SHEET)
            .requiresUnlocking(dye.tag)
            .save(provider)

        provider.dyeingRecipe(dye, CDTags.PLACARD, context)
    }

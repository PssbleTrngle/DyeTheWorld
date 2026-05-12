package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.supplementaries.reg.ModRegistry
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items

fun <T : Item, P> ItemBuilder<T, P>.dyedBuntingItemModel() =
    model { context, provider ->
        provider.generated(context, Constants.MOD_ID.createId("item/$SUPPLEMENTARIES/buntings/$dye"))
    }

fun <T : Item, P> ItemBuilder<T, P>.buntingItemModel() =
    model { context, provider ->
        val model = provider.generated(context, SUPPLEMENTARIES.createId("item/buntings/bunting_white"))

        DyeColor.entries.filter { it != DyeColor.WHITE }.forEach { dye ->
            model
                .override()
                .model(provider.getExistingFile(SUPPLEMENTARIES.createId("item/bunting_$dye")))
                .predicate(SUPPLEMENTARIES.createId("dye"), 0.01F * dye.id)
                .end()
        }
    }

// Needs to be modified manually afterward to add nbt to result
fun <T : Item, P> ItemBuilder<T, P>.dyedBuntingRecipe() =
    recipe { _, provider ->
        val bunting = ModRegistry.BUNTING.get()

        ShapedRecipeBuilder
            .shaped(RecipeCategory.DECORATIONS, bunting, 6)
            .pattern("SSS")
            .pattern("WWW")
            .pattern(" W ")
            .defineUnlocking('W', dye.blockOf("wool"))
            .defineUnlocking('S', Items.STRING)
            .save(provider, Constants.MOD_ID.createId("bunting_$dye"))

        provider.dyeingRecipe(dye, bunting, { bunting }, id = Constants.MOD_ID.createId("bunting_${dye}_dyeing")) {
            group("bunting")
        }
    }

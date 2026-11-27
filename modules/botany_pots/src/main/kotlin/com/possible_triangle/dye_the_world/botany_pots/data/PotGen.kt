package com.possible_triangle.dye_the_world.botany_pots.data

import com.possible_triangle.dye_the_world.botany_pots.BOTANY_POTS
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.Holder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike

fun BlockBuilder<*, *>.basicPotRecipe(material: Holder<out ItemLike>) =
    recipe { context, provider ->
        ShapedRecipeBuilder
            .shaped(RecipeCategory.MISC, context.get())
            .pattern("M M")
            .pattern("MPM")
            .pattern(" M ")
            .defineUnlocking('M', material.value())
            .defineUnlocking('P', Items.FLOWER_POT)
            .group("$BOTANY_POTS:basic_pot")
            .save(provider)
    }

fun BlockBuilder<*, *>.hopperPotRecipe(
    basic: Holder<out ItemLike>,
    material: Holder<out ItemLike>,
) = recipe { context, provider ->
    ShapedRecipeBuilder
        .shaped(RecipeCategory.MISC, context.get())
        .pattern("MHM")
        .pattern("MPM")
        .pattern(" M ")
        .defineUnlocking('M', material.value())
        .defineUnlocking('P', Items.FLOWER_POT)
        .defineUnlocking('H', Items.HOPPER)
        .group("$BOTANY_POTS:quick_hopper_pot")
        .save(provider)

    ShapelessRecipeBuilder
        .shapeless(RecipeCategory.MISC, context.get())
        .requiresUnlocking(Items.HOPPER)
        .requiresUnlocking(basic.value())
        .group("$BOTANY_POTS:hopper_pot")
        .save(provider, RegistrateRecipeProvider.getItemName(context.get()) + "_quick")
}

fun BlockBuilder<*, *>.waxedPotRecipe(basic: Holder<out ItemLike>) =
    recipe { context, provider ->
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.MISC, context.get())
            .requiresUnlocking(Items.HONEYCOMB)
            .requiresUnlocking(basic.value())
            .group("$BOTANY_POTS:waxed_pot")
            .save(provider)
    }

fun BlockBuilder<*, *>.potBlockstate(
    type: String,
    template: String = "pot",
) = blockstate { context, provider ->
    val parent = BOTANY_POTS.createId("block/template/$template")
    val model =
        provider
            .models()
            .withExistingParent(context.name, parent)
            .renderType("cutout")
            .texture("material", dye.namespace.createId("block/${dye}_$type"))
            .texture("material_top", BOTANY_POTS.createId("block/${dye}_${type}_pot_top"))
    provider.simpleBlock(context.get(), model)
}

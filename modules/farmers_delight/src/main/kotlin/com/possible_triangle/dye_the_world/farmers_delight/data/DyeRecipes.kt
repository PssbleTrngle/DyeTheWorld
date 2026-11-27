package com.possible_triangle.dye_the_world.farmers_delight.data

import com.ninni.dye_depot.registry.DDDyes
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.farmers_delight.FARMERS_DELIGHT
import com.possible_triangle.dye_the_world.item
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider.getItemName
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import vectorwing.farmersdelight.common.tag.ModTags
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder

fun DyedRegistrate.createDyeRecipes() =
    addDataGenerator(ProviderType.RECIPE) { provider ->
        provider.cutting(DDDyes.MAROON.get(), Items.SPIDER_EYE, 2)

        provider.cutting(DDDyes.AQUA.get(), Items.GLOW_INK_SAC, 2)

        provider.cutting(DDDyes.ROSE.get(), Items.ROSE_BUSH, 3)

        provider.cutting(DDDyes.BEIGE.get(), Items.OXEYE_DAISY, 2)

        provider.cutting(DDDyes.FOREST.get(), Items.BAMBOO, 2)

        provider.cutting(DDDyes.VERDANT.get(), Items.DRIED_KELP, 2)
    }

private fun RegistrateRecipeProvider.cutting(
    dye: DyeColor,
    from: ItemLike,
    amount: Int,
    block: CuttingBoardRecipeBuilder.() -> Unit = {},
) {
    val id = FARMERS_DELIGHT.createId("cutting/${getItemName(from)}")
    CuttingBoardRecipeBuilder
        .cuttingRecipe(Ingredient.of(from), Ingredient.of(ModTags.Items.KNIVES), dye.item, amount)
        .apply(block)
        .build(this, id)
}

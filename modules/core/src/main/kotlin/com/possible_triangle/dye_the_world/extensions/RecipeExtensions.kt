package com.possible_triangle.dye_the_world.extensions

import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.DataIngredient
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.level.ItemLike

fun RegistrateRecipeProvider.quadTransform(
    from: ItemLike,
    to: DataGenContext<*, out ItemLike>,
    stonecutting: Boolean = true,
    build: ShapedRecipeBuilder.() -> Unit = { },
) {
    ShapedRecipeBuilder
        .shaped(BUILDING_BLOCKS, to.get(), 4)
        .pattern("XX")
        .pattern("XX")
        .defineUnlocking('X', from)
        .apply(build)
        .save(this)

    if (stonecutting) {
        stonecutting(DataIngredient.items(from), BUILDING_BLOCKS, to)
    }
}

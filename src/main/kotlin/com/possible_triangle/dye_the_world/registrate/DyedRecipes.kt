package com.possible_triangle.dye_the_world.registrate

import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

fun RegistrateRecipeProvider.dyeingRecipe(
    dye: DyeColor,
    from: Ingredient,
    to: NonNullSupplier<out ItemLike>,
    id: ResourceLocation? = null,
    build: ShapelessRecipeBuilder.() -> Unit = { },
) {
    ShapelessRecipeBuilder
        .shapeless(RecipeCategory.BUILDING_BLOCKS, to.get())
        .apply(build)
        .requires(dye.tag)
        .requires(from)
        .save(this, id ?: safeId(to.get()).withSuffix("_dyeing"))
}

fun RegistrateRecipeProvider.dyeingRecipe(
    dye: DyeColor,
    from: ItemLike,
    to: NonNullSupplier<out ItemLike>,
    id: ResourceLocation? = null,
    build: ShapelessRecipeBuilder.() -> Unit = { },
) {
    val name = safeId(from).path
    dyeingRecipe(dye, Ingredient.of(from), to, id) {
        unlockedBy("has_$name", RegistrateRecipeProvider.has(from))
        build()
    }
}

fun RegistrateRecipeProvider.dyeingRecipe(
    dye: DyeColor,
    from: TagKey<Item>,
    to: NonNullSupplier<out ItemLike>,
    id: ResourceLocation? = null,
    build: ShapelessRecipeBuilder.() -> Unit = { },
) {
    dyeingRecipe(dye, Ingredient.of(from), to, id) {
        unlockedBy("has_${from.location.path}", RegistrateRecipeProvider.has(from))
        build()
    }
}

fun RegistrateRecipeProvider.shapedDyeingRecipe(
    dye: DyeColor,
    from: Ingredient,
    to: DataGenContext<*, out ItemLike>,
    build: ShapedRecipeBuilder.() -> Unit = { },
) {
    ShapedRecipeBuilder
        .shaped(RecipeCategory.BUILDING_BLOCKS, to.get(), 8)
        .pattern("###")
        .pattern("#D#")
        .pattern("###")
        .define('#', from)
        .define('D', dye.tag)
        .unlockedBy("has_dye", RegistrateRecipeProvider.has(dye.tag))
        .apply(build)
        .save(this, safeId(to.get()).withSuffix("_dyeing"))
}

fun RegistrateRecipeProvider.shapedDyeingRecipe(
    dye: DyeColor,
    from: ItemLike,
    to: DataGenContext<*, out ItemLike>,
    build: ShapedRecipeBuilder.() -> Unit = { },
) {
    val name = safeId(from).path
    shapedDyeingRecipe(dye, Ingredient.of(from), to) {
        unlockedBy("has_$name", RegistrateRecipeProvider.has(from))
        build()
    }
}

fun RegistrateRecipeProvider.shapedDyeingRecipe(
    dye: DyeColor,
    from: TagKey<Item>,
    to: DataGenContext<*, out ItemLike>,
    build: ShapedRecipeBuilder.() -> Unit = { },
) {
    shapedDyeingRecipe(dye, Ingredient.of(from), to) {
        unlockedBy("has_${from.location.path}", RegistrateRecipeProvider.has(from))
        build()
    }
}

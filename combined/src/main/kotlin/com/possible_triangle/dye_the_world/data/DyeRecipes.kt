package com.possible_triangle.dye_the_world.data

import com.ninni.dye_depot.registry.DDDyes
import com.possible_triangle.dye_the_world.Constants.Mods
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.itemOf
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.simibubi.create.content.kinetics.millstone.MillingRecipe
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider.getItemName
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import vectorwing.farmersdelight.common.tag.ModTags
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder

private val dyes = DyeColor.entries.associateWith { NonNullSupplier.lazy { it.itemOf("dye") } }

private val DyeColor.item get() = dyes.getValue(this).get()

fun DyedRegistrate.createDyeRecipes() =
    addDataGenerator(ProviderType.RECIPE) { provider ->
        provider.millingAndCutting(DDDyes.MAROON.get(), Items.SPIDER_EYE, 2) {
            output(0.1F, DyeColor.GREEN.item)
        }

        provider.millingAndCutting(DDDyes.AQUA.get(), Items.GLOW_INK_SAC, 2) {
            output(0.1F, DyeColor.WHITE.item)
        }

        provider.millingAndCutting(DDDyes.ROSE.get(), Items.ROSE_BUSH, 3) {
            output(0.25F, DDDyes.ROSE.get().item, 2)
            output(0.05F, DyeColor.GREEN.item, 2)
        }

        provider.millingAndCutting(DDDyes.BEIGE.get(), Items.OXEYE_DAISY, 2) {
            output(0.2F, DyeColor.WHITE.item)
            output(0.05F, DyeColor.YELLOW.item)
        }

        provider.millingAndCutting(DDDyes.FOREST.get(), Items.BAMBOO, 2) {
            output(0.25F, DDDyes.FOREST.get().item)
        }

        provider.millingAndCutting(DDDyes.VERDANT.get(), Items.DRIED_KELP, 2) {
            output(0.05F, DyeColor.GREEN.item)
        }

        provider.milling(DDDyes.TEAL.get(), Items.PRISMARINE_SHARD, 2) {
            output(0.1F, DDDyes.TEAL.get().item)
        }

        provider.milling(DDDyes.CORAL.get(), Items.NAUTILUS_SHELL, 2) {
            output(0.1F, DDDyes.CORAL.get().item)
        }
    }

private fun RegistrateRecipeProvider.cutting(
    dye: DyeColor,
    from: ItemLike,
    amount: Int,
    block: CuttingBoardRecipeBuilder.() -> Unit = {},
) {
    val id = Mods.FARMERS_DELIGHT.createId("cutting/${getItemName(from)}")
    CuttingBoardRecipeBuilder
        .cuttingRecipe(Ingredient.of(from), Ingredient.of(ModTags.Items.KNIVES), dye.item, amount)
        .apply(block)
        .build(this, id)
}

private fun RegistrateRecipeProvider.milling(
    dye: DyeColor,
    from: ItemLike,
    amount: Int,
    block: StandardProcessingRecipe.Builder<MillingRecipe>.() -> Unit = {},
) {
    val id = Mods.CREATE.createId(getItemName(from))
    StandardProcessingRecipe
        .Builder(::MillingRecipe, id)
        .apply {
            require(from)
            output(dye.item, amount)
            block()
        }.build(this)
}

private fun RegistrateRecipeProvider.millingAndCutting(
    dye: DyeColor,
    from: ItemLike,
    amount: Int,
    block: StandardProcessingRecipe.Builder<MillingRecipe>.() -> Unit = {},
) {
    milling(dye, from, amount, block)
    cutting(dye, from, amount)
}

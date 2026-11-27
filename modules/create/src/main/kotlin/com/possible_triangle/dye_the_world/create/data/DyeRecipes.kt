package com.possible_triangle.dye_the_world.create.data

import com.ninni.dye_depot.registry.DDDyes
import com.possible_triangle.dye_the_world.create.CREATE
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.item
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.simibubi.create.content.kinetics.millstone.MillingRecipe
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider.getItemName
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike

fun DyedRegistrate.createDyeRecipes() =
    addDataGenerator(ProviderType.RECIPE) { provider ->
        provider.milling(DDDyes.MAROON.get(), Items.SPIDER_EYE, 2) {
            output(0.1F, DyeColor.GREEN.item)
        }

        provider.milling(DDDyes.AQUA.get(), Items.GLOW_INK_SAC, 2) {
            output(0.1F, DyeColor.WHITE.item)
        }

        provider.milling(DDDyes.ROSE.get(), Items.ROSE_BUSH, 3) {
            output(0.25F, DDDyes.ROSE.get().item, 2)
            output(0.05F, DyeColor.GREEN.item, 2)
        }

        provider.milling(DDDyes.BEIGE.get(), Items.OXEYE_DAISY, 2) {
            output(0.2F, DyeColor.WHITE.item)
            output(0.05F, DyeColor.YELLOW.item)
        }

        provider.milling(DDDyes.FOREST.get(), Items.BAMBOO, 2) {
            output(0.25F, DDDyes.FOREST.get().item)
        }

        provider.milling(DDDyes.VERDANT.get(), Items.DRIED_KELP, 2) {
            output(0.05F, DyeColor.GREEN.item)
        }

        provider.milling(DDDyes.TEAL.get(), Items.PRISMARINE_SHARD, 2) {
            output(0.1F, DDDyes.TEAL.get().item)
        }

        provider.milling(DDDyes.CORAL.get(), Items.NAUTILUS_SHELL, 2) {
            output(0.1F, DDDyes.CORAL.get().item)
        }
    }

private fun RegistrateRecipeProvider.milling(
    dye: DyeColor,
    from: ItemLike,
    amount: Int,
    block: StandardProcessingRecipe.Builder<MillingRecipe>.() -> Unit = {},
) {
    val id = CREATE.createId(getItemName(from))
    StandardProcessingRecipe
        .Builder(::MillingRecipe, id)
        .apply {
            require(from)
            output(dye.item, amount)
            block()
        }.build(this)
}

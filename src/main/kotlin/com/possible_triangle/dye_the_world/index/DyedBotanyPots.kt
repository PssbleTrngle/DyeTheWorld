package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.BOTANY_POTS
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike

private fun BlockBuilder<*, *>.basicPotRecipe(material: NonNullSupplier<out ItemLike>) =
    recipe { context, provider ->
        ShapedRecipeBuilder
            .shaped(RecipeCategory.MISC, context.get())
            .pattern("M M")
            .pattern("MPM")
            .pattern(" M ")
            .defineUnlocking('M', material.get())
            .defineUnlocking('P', Items.FLOWER_POT)
            .group("$BOTANY_POTS:basic_pot")
            .save(provider)
    }

private fun BlockBuilder<*, *>.hopperPotRecipe(
    basic: NonNullSupplier<out ItemLike>,
    material: NonNullSupplier<out ItemLike>,
) = recipe { context, provider ->
    ShapedRecipeBuilder
        .shaped(RecipeCategory.MISC, context.get())
        .pattern("MHM")
        .pattern("MPM")
        .pattern(" M ")
        .defineUnlocking('M', material.get())
        .defineUnlocking('P', Items.FLOWER_POT)
        .defineUnlocking('H', Items.HOPPER)
        .group("$BOTANY_POTS:quick_hopper_pot")
        .save(provider)

    ShapelessRecipeBuilder
        .shapeless(RecipeCategory.MISC, context.get())
        .requiresUnlocking(Items.HOPPER)
        .requiresUnlocking(basic.get())
        .group("$BOTANY_POTS:hopper_pot")
        .save(provider, RegistrateRecipeProvider.getItemName(context.get()) + "_quick")
}

private fun BlockBuilder<*, *>.waxedPotRecipe(basic: NonNullSupplier<out ItemLike>) =
    recipe { context, provider ->
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.MISC, context.get())
            .requiresUnlocking(Items.HONEYCOMB)
            .requiresUnlocking(basic.get())
            .group("$BOTANY_POTS:waxed_pot")
            .save(provider)
    }

private fun BlockBuilder<*, *>.potBlockstate(
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
            .texture("material_top", Constants.MOD_ID.createId("block/$BOTANY_POTS/${dye}_$type"))
    provider.simpleBlock(context.get(), model)
}

object DyedBotanyPots {
    private val REGISTRATE = DyedRegistrate.create(BOTANY_POTS)
    private val DYES = dyesFor(BOTANY_POTS)

    private fun createPots(
        type: String,
        block: BlockBuilder<*, *>.() -> Unit = {},
    ) = DYES.associateWith { dye ->
        REGISTRATE
            .`object`("${dye}_${type}_botany_pot")
            .dyedBlock(dye)
            .optionalTag(DyedTags.Blocks.BOTANY_POTS)
            .withItem {
                optionalTag(DyedTags.Items.BOTANY_POTS)
            }.apply(block)
            .register()
    }

    private fun createAllPots(
        type: String,
        translation: String? = null,
        base: Map<DyeColor, NonNullSupplier<out ItemLike>> = dyedBlockMap(BOTANY_POTS, type),
    ) {
        val translationPrefix = translation?.let { " $it" } ?: ""

        val basic =
            createPots(type) {
                basicPotRecipe(base[dye]!!)
                potBlockstate(type)
                lang("${dye.translation}$translationPrefix Botany Pot")
            }

        createPots("${type}_hopper") {
            hopperPotRecipe(basic[dye]!!, base[dye]!!)
            potBlockstate(type, template = "hopper_pot")
            lang("${dye.translation}$translationPrefix Hopper Botany Pot")
        }

        createPots("${type}_waxed") {
            waxedPotRecipe(basic[dye]!!)
            potBlockstate(type)
            lang("${dye.translation}$translationPrefix Waxed Botany Pot")
        }
    }

    val TERRACOTTA_POTS = createAllPots("terracotta")

    val CONCRETE_POTS = createAllPots("concrete", "Concrete")

    val GLAZED_TERRACOTTA_POTS = createAllPots("glazed_terracotta", "Glazed")

    fun register() {
        REGISTRATE.register()
    }
}

package com.possible_triangle.dye_the_world.botany_pots

import com.possible_triangle.dye_the_world.botany_pots.data.basicPotRecipe
import com.possible_triangle.dye_the_world.botany_pots.data.hopperPotRecipe
import com.possible_triangle.dye_the_world.botany_pots.data.potBlockstate
import com.possible_triangle.dye_the_world.botany_pots.data.waxedPotRecipe
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.ItemLike

object DyedBotanyPots {
    private fun createPots(
        type: String,
        block: BlockBuilder<*, *>.() -> Unit = {},
    ) = REGISTRATE.dyed { dye ->
        `object`("${dye}_${type}_botany_pot")
            .dyedBlock(dye)
            .tag(Tags.BLOCK)
            .withItem {
                tag(Tags.ITEM)
            }.apply(block)
            .register()
    }

    private fun createAllPots(
        type: String,
        translation: String? = null,
        base: Map<DyeColor, NonNullSupplier<out ItemLike>> = dyedBlockMap(BOTANY_POTS, type),
    ): Pots {
        val translationPrefix = translation?.let { " $it" } ?: ""

        val basic =
            createPots(type) {
                basicPotRecipe(base[dye]!!)
                potBlockstate(type)
                lang("${dye.translation}$translationPrefix Botany Pot")
            }

        val hopper =
            createPots("${type}_hopper") {
                hopperPotRecipe(basic[dye]!!, base[dye]!!)
                potBlockstate(type, template = "hopper_pot")
                lang("${dye.translation}$translationPrefix Hopper Botany Pot")
            }

        val waxed =
            createPots("${type}_waxed") {
                waxedPotRecipe(basic[dye]!!)
                potBlockstate(type)
                lang("${dye.translation}$translationPrefix Waxed Botany Pot")
            }

        return Pots(basic, hopper, waxed)
    }

    val TERRACOTTA_POTS = createAllPots("terracotta")

    val CONCRETE_POTS = createAllPots("concrete", "Concrete")

    val GLAZED_TERRACOTTA_POTS = createAllPots("glazed_terracotta", "Glazed")

    data class Pots(
        val basic: Map<DyeColor, BlockEntry<*>>,
        val hopper: Map<DyeColor, BlockEntry<*>>,
        val waxed: Map<DyeColor, BlockEntry<*>>,
    )
}

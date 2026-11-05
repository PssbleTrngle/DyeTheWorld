package com.possible_triangle.dye_the_world.botany_pots

import com.possible_triangle.dye_the_world.botany_pots.data.basicPotRecipe
import com.possible_triangle.dye_the_world.botany_pots.data.hopperPotRecipe
import com.possible_triangle.dye_the_world.botany_pots.data.potBlockstate
import com.possible_triangle.dye_the_world.botany_pots.data.waxedPotRecipe
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.multikulti.platform.LOGGER
import com.tterrag.registrate.builders.BlockBuilder
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
            .optionalTag(Tags.BLOCK)
            .withItem {
                optionalTag(Tags.ITEM)
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
}

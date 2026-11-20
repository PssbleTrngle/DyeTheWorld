package com.possible_triangle.dye_the_world.botany_pots

import com.possible_triangle.dye_the_world.DyedMap
import com.possible_triangle.dye_the_world.botany_pots.data.basicPotRecipe
import com.possible_triangle.dye_the_world.botany_pots.data.hopperPotRecipe
import com.possible_triangle.dye_the_world.botany_pots.data.potBlockstate
import com.possible_triangle.dye_the_world.botany_pots.data.waxedPotRecipe
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block

object DyedBotanyPots {
    private fun createPots(
        type: String,
        block: BlockBuilder<*, *>.() -> Unit = {},
    ) = REGISTRATE.dyed { dye ->
        `object`("${dye}_${type}_botany_pot")
            .dyedBlock(dye)
            .tag(BotanyPotsTags.BLOCK)
            .withItem {
                tag(BotanyPotsTags.ITEM)
            }.apply(block)
            .register()
    }

    private fun createAllPots(
        type: String,
        translation: String? = null,
        base: DyedMap<out ItemLike> = dyedBlockMap(BOTANY_POTS, type),
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
        val basic: DyedMap<Block>,
        val hopper: DyedMap<Block>,
        val waxed: DyedMap<Block>,
    )
}

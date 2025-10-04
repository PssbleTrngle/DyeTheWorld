package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.sackBlockstate() =
    blockstate { context, provider ->
        fun texture(suffix: String) =
            Constants.MOD_ID.createId("block/${Constants.Mods.SUPPLEMENTARIES_SQUARED}/sack_${dye}_$suffix")

        provider.createVariant(context) { state ->
            val open = state.getValue(SackBlock.OPEN)

            val suffix = if (open) "open" else "closed"
            val model = provider.models()
                .withExistingParent(
                    "block/${context.name}_$suffix",
                    Constants.Mods.SUPPLEMENTARIES.createId("block/sack_$suffix")
                )
                .texture("1", texture("front"))
                .texture("2", texture("top"))
                .texture("3", texture("bottom"))
                .texture("4", texture("closed"))
                .texture("particle", texture("front"))

            ConfiguredModel.builder().modelFile(model)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.sackItemModel() = model { context, provider ->
    provider.withExistingParent(
        "item/${context.name}",
        Constants.Mods.SUPPLEMENTARIES_SQUARED.createId("block/${context.name}_closed")
    )
}
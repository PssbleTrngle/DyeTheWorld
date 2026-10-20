package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES_SQUARED
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.texture
import com.possible_triangle.dye_the_world.extensions.textureAndParticle
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.sackBlockstate() =
    blockstate { context, provider ->
        provider.createVariant(context) { state ->
            val open = state.getValue(SackBlock.OPEN)

            val suffix = if (open) "open" else "closed"
            val model =
                provider
                    .models()
                    .withExistingParent(
                        "block/${context.name}_$suffix",
                        SUPPLEMENTARIES.createId("block/sack_$suffix"),
                    ).textureAndParticle("1", context.texture.withSuffix("_front"))
                    .texture("2", context.texture.withSuffix("_top"))
                    .texture("3", context.texture.withSuffix("_bottom"))
                    .texture("4", context.texture.withSuffix("_closed"))

            ConfiguredModel.builder().modelFile(model)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.sackItemModel() =
    model { context, provider ->
        provider.withExistingParent(
            "item/${context.name}",
            SUPPLEMENTARIES_SQUARED.createId("block/${context.name}_closed"),
        )
    }

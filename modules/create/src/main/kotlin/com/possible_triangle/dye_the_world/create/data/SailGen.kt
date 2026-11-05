package com.possible_triangle.dye_the_world.create.data

import com.possible_triangle.dye_the_world.create.CREATE
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.simibubi.create.content.contraptions.bearing.SailBlock
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.core.Direction
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : SailBlock, P> BlockBuilder<T, P>.sailBlockstate() =
    blockstate { context, provider ->
        val parent = CREATE.createId("block/white_sail")
        val model =
            provider
                .models()
                .withExistingParent(context.name, parent)
                .texture("0", CREATE.createId("block/sail/$dye"))

        provider.createVariant(context) { state ->
            val facing = state.getValue(BlockStateProperties.FACING)

            val xRot =
                when (facing) {
                    Direction.DOWN -> 180
                    Direction.UP -> 0
                    else -> 90
                }

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.yRot)
                .rotationX(xRot)
        }
    }

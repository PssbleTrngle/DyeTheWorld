package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_SIMULATED
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.simibubi.create.content.contraptions.bearing.SailBlock
import com.tterrag.registrate.builders.BlockBuilder
import dev.simulated_team.simulated.content.blocks.symmetric_sail.SymmetricSailBlock
import net.minecraft.core.Direction
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : SailBlock, P> BlockBuilder<T, P>.sailBlockstate() = blockstate { context, provider ->
    val parent = CREATE.createId("block/white_sail")
    val model = provider.models().withExistingParent(context.name, parent)
        .texture("0", Constants.MOD_ID.createId("block/$CREATE/sail/$dye"))

    provider.createVariant(context) { state ->
        val facing = state.getValue(BlockStateProperties.FACING)

        val xRot = when (facing) {
            Direction.DOWN -> 180
            Direction.UP -> 0
            else -> 90
        }

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
            .rotationX(xRot)
    }
}

fun <T : SymmetricSailBlock, P> BlockBuilder<T, P>.symmetricSailBlockstate() = blockstate { context, provider ->
    val parent = CREATE_SIMULATED.createId("block/symmetric_sail/block")
    val model = provider.models().withExistingParent(context.name, parent)
        .texture("0", Constants.MOD_ID.createId("block/$CREATE/sail/$dye"))
        .texture("particle", Constants.MOD_ID.createId("block/$CREATE/sail/$dye"))
        .texture("1", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/symmetric_sail/side_$dye"))

    provider.createVariant(context) { state ->
        val axis = state.getValue(BlockStateProperties.AXIS)

        val xRot =
            if (axis.isHorizontal) 90
            else 0

        val yRot = when (axis) {
            Direction.Axis.X -> 90
            Direction.Axis.Z -> 180
            else -> 0
        }

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(yRot)
            .rotationX(xRot)
    }
}
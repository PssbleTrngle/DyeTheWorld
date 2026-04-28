package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_SIMULATED
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.core.Direction
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

internal val AXIS_ALONG_FIRST_COORDINATE: BooleanProperty = BooleanProperty.create("axis_along_first")

fun <T : Block, P> BlockBuilder<T, P>.handleBlockstate() = blockstate { context, provider ->
    fun model(type: String): BlockModelBuilder {
        val parent = CREATE_SIMULATED.createId("block/handle/block_$type")
        return provider.models().withExistingParent("${context.name}_${type}", parent)
            .texture("0", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/handle/$dye"))
    }

    val vertical = model("vertical")
    val horizontal = model("horizontal")

    provider.createVariant(context, BlockStateProperties.LIT) { state ->
        val alongFirst = state.getValue(AXIS_ALONG_FIRST_COORDINATE)
        val facing = state.getValue(BlockStateProperties.FACING)
        val isVertical = facing.axis.isHorizontal && (facing.axis === Direction.Axis.X) == alongFirst
        val xRot = when (facing) {
            Direction.DOWN -> 270
            Direction.UP -> 90
            else -> 0
        }
        val yRot =
            if (facing.axis.isVertical)
                if (alongFirst) 0
                else 90
            else facing.toYRot().toInt()

        ConfiguredModel.builder()
            .modelFile(if (isVertical) vertical else horizontal)
            .rotationY(yRot)
            .rotationX(xRot)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.handleItemModel() = model { context, provider ->
    val parent = CREATE_SIMULATED.createId("block/handle/item")
    provider.withExistingParent(context.name, parent)
        .texture("0", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/handle/$dye"))
}
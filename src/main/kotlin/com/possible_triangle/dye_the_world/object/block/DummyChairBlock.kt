package com.possible_triangle.dye_the_world.`object`.block

import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.EnumProperty

class DummyChairBlock(
    properties: Properties,
) : HorizontalDirectionalBlock(properties) {
    companion object {
        val ARMRESTS = EnumProperty.create("armrests", ArmrestConfiguration::class.java)
        val CROPPED_BACK = BooleanProperty.create("cropped_back")
        val FACING = BlockStateProperties.HORIZONTAL_FACING
    }

    override fun codec() = simpleCodec(::DummyChairBlock)

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(ARMRESTS, FACING, CROPPED_BACK)
    }

    enum class ArmrestConfiguration : StringRepresentable {
        BOTH,
        NONE,
        LEFT,
        RIGHT,
        ;

        override fun getSerializedName() = name.lowercase()
    }
}

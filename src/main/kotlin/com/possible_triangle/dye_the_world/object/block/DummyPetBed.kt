package com.possible_triangle.dye_the_world.`object`.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition

class DummyPetBed(properties: Properties) : HorizontalDirectionalBlock(properties) {

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(FACING)
    }

    override fun codec() = simpleCodec(::DummyChairBlock)

}
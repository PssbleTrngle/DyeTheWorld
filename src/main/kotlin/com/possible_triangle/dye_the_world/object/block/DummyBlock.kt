package com.possible_triangle.dye_the_world.`object`.block

import com.tterrag.registrate.util.nullness.NonNullFunction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.Property

object DummyBlock {

    fun of(vararg properties: Property<*>): NonNullFunction<Properties, Block> {
        return NonNullFunction {
            object : Block(it) {
                override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
                    super.createBlockStateDefinition(builder)
                    builder.add(*properties)
                }
            }
        }
    }

}
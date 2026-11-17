package com.possible_triangle.dye_the_world.collections

import com.tterrag.registrate.util.entry.BlockEntry
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock

data class StoneSet(
    val cube: BlockEntry<out Block>,
    val stairs: BlockEntry<out StairBlock>,
    val slab: BlockEntry<out SlabBlock>,
    val wall: BlockEntry<out WallBlock>,
)

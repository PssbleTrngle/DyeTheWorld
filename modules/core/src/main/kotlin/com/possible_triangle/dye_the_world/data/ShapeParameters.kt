package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.DyedMap
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block

typealias BlockModifier<T> = BlockBuilder<T, DyedRegistrate>.() -> Unit
typealias BlockItemModifier<T> = ItemBuilder<BlockItem, BlockBuilder<T, DyedRegistrate>>.() -> Unit

data class ShapeParameters(
    val name: ResourceLocation,
    val cubes: DyedMap<Block>,
    val stone: Boolean = true,
)

package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block

typealias BlockModifier<T> = BlockBuilder<T, DyedRegistrate>.() -> Unit
typealias BlockItemModifier<T> = ItemBuilder<BlockItem, BlockBuilder<T, DyedRegistrate>>.() -> Unit

data class ShapeParameters(
    val name: ResourceLocation,
    val cubes: Map<DyeColor, NonNullSupplier<out Block>>,
    val stone: Boolean = true,
)

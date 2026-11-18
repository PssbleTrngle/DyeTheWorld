package com.possible_triangle.dye_the_world.registrate

import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.world.item.DyeColor

typealias BlockFactory<T> = DyedRegistrate.(dye: DyeColor) -> BlockBuilder<T, DyedRegistrate>

typealias ItemFactory<T> = DyedRegistrate.(dye: DyeColor) -> ItemBuilder<T, DyedRegistrate>

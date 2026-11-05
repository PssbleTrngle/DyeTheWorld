package com.possible_triangle.dye_the_world.elevators

import com.possible_triangle.dye_the_world.DyedTags

object ElevatorsTags {
    val BLOCK = DyedTags.BLOCKS.create(ELEVATORS, "elevators")
    val ITEM = DyedTags.ITEMS.copy(BLOCK)
}

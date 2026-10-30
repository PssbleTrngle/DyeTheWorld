package com.possible_triangle.dye_the_world.farmersdelight.world.block

import com.possible_triangle.dye_the_world.farmersdelight.isDarkBackground
import net.minecraft.world.item.DyeColor
import vectorwing.farmersdelight.common.block.WallCanvasSignBlock

class DyedWallCanvasSignBlock(
    properties: Properties,
    dye: DyeColor,
) : WallCanvasSignBlock(properties, dye) {
    override fun isDarkBackground() = backgroundColor!!.isDarkBackground
}

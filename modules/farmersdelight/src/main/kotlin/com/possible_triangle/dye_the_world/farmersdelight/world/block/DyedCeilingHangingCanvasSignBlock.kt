package com.possible_triangle.dye_the_world.farmersdelight.world.block

import com.possible_triangle.dye_the_world.farmersdelight.isDarkBackground
import net.minecraft.world.item.DyeColor
import vectorwing.farmersdelight.common.block.CeilingHangingCanvasSignBlock

class DyedCeilingHangingCanvasSignBlock(
    dye: DyeColor,
) : CeilingHangingCanvasSignBlock(dye) {
    override fun isDarkBackground() = backgroundColor!!.isDarkBackground
}

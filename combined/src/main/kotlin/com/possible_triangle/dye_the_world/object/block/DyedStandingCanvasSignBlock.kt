package com.possible_triangle.dye_the_world.`object`.block

import com.possible_triangle.dye_the_world.index.isDarkBackground
import net.minecraft.world.item.DyeColor
import vectorwing.farmersdelight.common.block.StandingCanvasSignBlock

class DyedStandingCanvasSignBlock(
    dye: DyeColor,
) : StandingCanvasSignBlock(dye) {
    override fun isDarkBackground() = backgroundColor!!.isDarkBackground
}

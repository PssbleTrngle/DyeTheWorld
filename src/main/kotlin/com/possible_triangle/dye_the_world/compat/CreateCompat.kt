package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.ColorSpaces
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.dyesFor
import com.simibubi.create.foundation.utility.DyeHelper
import net.minecraft.world.item.DyeColor

object CreateCompat {

    private val WOOL = dyedBlockMap(CREATE, "wool")

    fun registerDyes() {
        val colors = DyeHelper.getDyeColors(DyeColor.ORANGE) //use default color of nixie tubes as reference colors
        val (brightL, brightC, _) = ColorSpaces.OkLCh.fromARGB32(colors.first)
        val (darkL, darkC, _) = ColorSpaces.OkLCh.fromARGB32(colors.second)

        fun createAdjustedColors(color: Int): IntArray {
            val (_, _, hue) = ColorSpaces.OkLCh.fromARGB32(color)
            return intArrayOf(
                ColorSpaces.OkLCh.gamutMapToARGB32(doubleArrayOf(brightL, brightC, hue)),
                ColorSpaces.OkLCh.gamutMapToARGB32(doubleArrayOf(darkL, darkC, hue))
            )
        }

        dyesFor(CREATE).forEach { dye ->
            val (brightColor, darkColor) = createAdjustedColors(dye.textColor)
            DyeHelper.addDye(dye, brightColor, darkColor) {
                WOOL[dye]!!.get()
            }
        }
    }

}
package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.Constants.Mods.CHALK
import com.possible_triangle.dye_the_world.dyesFor
import io.github.mortuusars.chalk.render.ChalkColors

object ChalkCompat {
    private val DYES = dyesFor(CHALK)

    @JvmStatic
    fun registerColors() {
        DYES.forEach {
            ChalkColors.COLORS[it] = it.fireworkColor
        }
    }
}

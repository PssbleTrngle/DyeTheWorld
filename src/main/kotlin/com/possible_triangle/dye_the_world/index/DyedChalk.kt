package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CHALK
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate

object DyedChalk {
    private val REGISTRATE = DyedRegistrate.create(CHALK)

    private val DYES = dyesFor(CHALK)

    fun register() {
        REGISTRATE.register()
    }
}

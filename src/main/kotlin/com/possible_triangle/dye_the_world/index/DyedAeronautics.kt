package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_AERONAUTICS
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate

object DyedAeronautics {

    private val REGISTRATE = DyedRegistrate.create(CREATE_AERONAUTICS)

    private val DYES = dyesFor(CREATE_AERONAUTICS)

    fun register() {
        REGISTRATE.register()
    }

}
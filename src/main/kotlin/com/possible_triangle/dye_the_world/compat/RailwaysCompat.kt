package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_RAILWAYS
import com.possible_triangle.dye_the_world.dyedItemMap
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.translation

object RailwaysCompat {

    private val DYES = dyesFor(CREATE_RAILWAYS)

    @JvmField
    val TRANSLATIONS = DYES
        .associateWith { it.translation }
        .mapKeys { it.key.serializedName }

    @JvmField
    val DYE_ITEMS = dyedItemMap(CREATE_RAILWAYS, "dye")

}
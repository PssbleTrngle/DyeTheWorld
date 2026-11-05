package com.possible_triangle.dye_the_world.create_railways

import com.possible_triangle.dye_the_world.dyedItemMap
import com.possible_triangle.dye_the_world.translation

object RailwaysCompat {
    @JvmField
    val TRANSLATIONS =
        REGISTRATE.dyes
            .associateWith { it.translation }
            .mapKeys { it.key.serializedName }

    @JvmField
    val DYE_ITEMS = dyedItemMap(CREATE_RAILWAYS, "dye")
}

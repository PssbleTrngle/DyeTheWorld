package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.dyesFor
import com.simibubi.create.foundation.utility.DyeHelper

object CreateCompat {

    private val WOOL = dyedBlockMap(CREATE, "wool")

    private val TUBE_COLORS = mapOf(
        "amber" to (-3430621 to -6325248),
        "aqua" to (-16725852 to -16737670),
        "beige" to (-3757531 to -6652416),
        "forest" to (-9125525 to -14050787),
        "forest" to (-816040 to -14050787),
        "ginger" to (-816040 to -2797568),
        "indigo" to (-4876550 to -7446048),
        "maroon" to (-555156 to -2602714),
        "mint" to (-11221630 to -16736948),
        "navy" to (-10440449 to -16743702),
        "olive" to (-5262538 to -8026368),
        "rose" to (-490622 to -2538676),
        "slate" to (-8410881 to -11962386),
        "tan" to (-1077175 to -3449856),
        "teal" to (-16725854 to -16737672),
        "verdant" to (-8732570 to -13068022),
    )

    fun registerDyes() {
        dyesFor(CREATE).forEach { dye ->
            val (brightColor, darkColor) = TUBE_COLORS[dye.serializedName] ?: return@forEach
            DyeHelper.addDye(dye, brightColor, darkColor) {
                WOOL[dye]!!.get()
            }
        }
    }

}
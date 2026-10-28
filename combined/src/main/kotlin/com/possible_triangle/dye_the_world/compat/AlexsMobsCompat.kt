package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.Constants.Mods.ALEXS_MOBS
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import net.minecraft.world.item.DyeColor

object AlexsMobsCompat {
    @JvmStatic
    fun getCarpet(dye: DyeColor) = dye.blockOf("carpet").asItem()

    private val DECORS =
        dyesFor(ALEXS_MOBS).associateWith {
            ALEXS_MOBS.createId("textures/entity/elephant_decor/$it.png")
        }

    @JvmStatic
    fun getDecorTexture(dye: DyeColor) = DECORS[dye]
}

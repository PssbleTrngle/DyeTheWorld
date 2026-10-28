package com.possible_triangle.dye_the_world.compat

import com.mojang.datafixers.util.Pair
import com.possible_triangle.dye_the_world.Constants.Mods.LABELS
import com.possible_triangle.dye_the_world.dyesFor
import net.minecraft.util.FastColor.ABGR32
import net.minecraft.util.FastColor.ARGB32
import net.minecraft.world.item.DyeColor

object LabelsCompat {
    private val DYES = dyesFor(LABELS)

    private const val LIGHT_AMOUNT = 0.4F
    private const val DARK_FACTOR = 0.6F

    // labels expects both colors to be fully opaque other wise the generated recolor palette
    // collapses into a single color and the item stays grey or goes to another weird transparent color
    private fun lightOf(dye: DyeColor) = ARGB32.lerp(LIGHT_AMOUNT, ARGB32.opaque(dye.fireworkColor), ARGB32.opaque(0xFFFFFF))

    private fun darkOf(dye: DyeColor): Int {
        val base = ARGB32.opaque(dye.fireworkColor)
        val scale = ARGB32.color(255, (DARK_FACTOR * 255).toInt(), (DARK_FACTOR * 255).toInt(), (DARK_FACTOR * 255).toInt())
        return ARGB32.multiply(base, scale)
    }

    // labels (via moonlight) reads these as native ABGR instead of ARGB, so red and blue need to be swapped
    private fun toLabelsColor(argb: Int) = ABGR32.fromArgb32(argb)

    @JvmStatic
    fun registerColors(map: MutableMap<DyeColor, Pair<Int, Int>>) {
        DYES.forEach {
            map[it] = Pair.of(toLabelsColor(lightOf(it)), toLabelsColor(darkOf(it)))
        }
    }
}

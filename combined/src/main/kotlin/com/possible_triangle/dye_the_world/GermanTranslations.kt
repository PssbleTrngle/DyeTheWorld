package com.possible_triangle.dye_the_world

import net.minecraft.world.item.DyeColor

object Genus {
    const val M = "er"
    const val F = "e"
    const val I = "es"
}

fun DyeColor.germanTranslation(suffix: String): String =
    when (serializedName) {
        "white" -> "Weiß$suffix"
        "orange" -> "Orang$suffix"
        "magenta" -> "Magenta"
        "light_blue" -> "Hellblau$suffix"
        "yellow" -> "Gelb$suffix"
        "lime" -> "Hellgrün$suffix"
        "pink" -> "Rosa"
        "gray" -> "Grau$suffix"
        "light_gray" -> "Hellgrau$suffix"
        "cyan" -> "Türkis$suffix"
        "purple" -> "Violett$suffix"
        "blue" -> "Blau$suffix"
        "brown" -> "Braun$suffix"
        "green" -> "Grün$suffix"
        "red" -> "Rot$suffix"
        "black" -> "Schwarz$suffix"
        "amber" -> "Bernstein$suffix"
        "aqua" -> "Aquamarin$suffix"
        "beige" -> "Beig$suffix"
        "coral" -> "Korall$suffix"
        "forest" -> "Blattgrün$suffix"
        "ginger" -> "Bronzen$suffix"
        "indigo" -> "Indigo"
        "maroon" -> "Marone$suffix"
        "mint" -> "Minzgrün$suffix"
        "navy" -> "Marineblau$suffix"
        "olive" -> "Olivengrün$suffix"
        "rose" -> "Rosarot$suffix"
        "slate" -> "Schieferblau$suffix"
        "tan" -> "Lohfarben$suffix"
        "teal" -> "Dunkeltürkis$suffix"
        "verdant" -> "Dunkelgrün$suffix"
        else -> throw IllegalArgumentException("No known german translations for '$serializedName'")
    }

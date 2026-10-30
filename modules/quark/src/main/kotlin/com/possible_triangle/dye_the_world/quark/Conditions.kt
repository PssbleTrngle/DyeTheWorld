package com.possible_triangle.dye_the_world.quark

import com.google.gson.JsonObject
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.multikulti.datagen.conditions.Condition
import org.violetmoon.zeta.config.ConfigFlagManager
import org.violetmoon.zeta.util.zetalist.ZetaList

data class QuarkConfigCondition(
    val flag: String,
) : Condition {
    override fun JsonObject.toFabric() {
        error("no fabric support yet")
    }

    override fun JsonObject.toForge() {
        addProperty("type", "zeta:flag")
        addProperty("flag", flag)
    }
}

val FLAG_MANAGER: ConfigFlagManager by lazy {
    val instances = ZetaList.INSTANCE.zetas
    val quark = instances.find { it.modid == QUARK }
    if (quark == null) {
        Constants.LOGGER.error("Could not find quark instance, active zeta instances are (${instances.joinToString { it.modid }})")
        throw NullPointerException("Could not find Quark Instance")
    }
    quark.configManager.configFlagManager
}

fun flagEnabled(flag: String) = FLAG_MANAGER.getFlag(flag)

fun flagCondition(flag: String): QuarkConfigCondition = QuarkConfigCondition(flag)

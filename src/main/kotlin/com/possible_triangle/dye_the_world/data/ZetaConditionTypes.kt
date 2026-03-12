package com.possible_triangle.dye_the_world.data

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.registries.RegisterEvent
import org.violetmoon.zeta.config.ConfigFlagManager
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

fun registerZetaConditionTypes() {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(
            Registries.LOOT_CONDITION_TYPE,
            ResourceLocation.fromNamespaceAndPath("zeta", "flag"),
        ) {
            ConfigFlagManager.FLAG_CONDITION_TYPE
        }
    }
}
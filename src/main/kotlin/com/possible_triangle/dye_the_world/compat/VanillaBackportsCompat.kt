package com.possible_triangle.dye_the_world.compat

import com.blackgear.vanillabackport.client.level.entities.layer.GhastHarnessHandler
import com.blackgear.vanillabackport.common.api.bundle.BundleFeatures
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.VANILLA_BACKPORT
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.index.DyedVanillaBackport.BUNDLES
import com.possible_triangle.dye_the_world.index.DyedVanillaBackport.HARNESSES
import kotlin.collections.component1
import kotlin.collections.component2

object VanillaBackportsCompat {
    fun registerHarnessLayers() {
        HARNESSES.forEach { (color, item) ->
            val stack = item.asStack()
            val texture = Constants.MOD_ID.createId("textures/entity/$VANILLA_BACKPORT/harness/$color.png")
            GhastHarnessHandler.register(stack, texture)
        }

        BUNDLES.forEach { (color, item) ->
            BundleFeatures.register(color, item.get())
        }
    }
}

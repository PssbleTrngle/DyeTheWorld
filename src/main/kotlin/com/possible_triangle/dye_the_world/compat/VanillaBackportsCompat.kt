package com.possible_triangle.dye_the_world.compat

import com.blackgear.vanillabackport.client.level.layer.GhastEquipmentManager
import com.blackgear.vanillabackport.common.api.modules.bundle_ui.BundleFeatures
import com.blackgear.vanillabackport.common.level.entities.decoration.Cushion
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.VANILLA_BACKPORT
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.index.DyedVanillaBackport.BUNDLES
import com.possible_triangle.dye_the_world.index.DyedVanillaBackport.CUSHIONS
import com.possible_triangle.dye_the_world.index.DyedVanillaBackport.HARNESSES

object VanillaBackportsCompat {
    fun registerHarnessLayers() {
        HARNESSES.forEach { (color, item) ->
            val texture = Constants.MOD_ID.createId("textures/entity/$VANILLA_BACKPORT/harness/$color.png")
            GhastEquipmentManager.register(item, texture)
        }

        BUNDLES.forEach { (color, item) ->
            BundleFeatures.register(color, item.get())
        }

        CUSHIONS.forEach { (color, item) ->
            Cushion.register(color, color.blockOf("wool"), item.get())
        }
    }
}

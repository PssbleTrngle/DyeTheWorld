package com.possible_triangle.dye_the_world.compat

import net.mehvahdjukaar.moonlight.api.platform.PlatHelper
import net.mehvahdjukaar.moonlight.api.platform.RegHelper

object SleepThighCompat {
    fun init() {
        if (PlatHelper.getPhysicalSide().isClient) {
            RegHelper.registerDynamicResourceProvider(SleepThighDynamicResources())
        }
    }
}

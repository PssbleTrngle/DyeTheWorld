package com.possible_triangle.dye_the_world.mixins.data;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.railwayteam.railways.forge.RailwaysImpl;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RailwaysImpl.class, remap = false)
public class RailswaysImplMixin {

    @WrapMethod(
        method = "restoreLoggers",
        require = 0
    )
    private static void cancelLogConfiguration(Operation<Void> original) {
        // don't do anything
    }

}

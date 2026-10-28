package com.possible_triangle.dye_the_world.mixins;

import com.google.common.collect.HashBiMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.dye_the_world.compat.ChalkCompat;
import io.github.mortuusars.chalk.Config;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Config.Server.class)
public class ChalkConfigMixin {

    @WrapOperation(
            method = "createColorsMap()V",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/HashBiMap;create(I)Lcom/google/common/collect/HashBiMap;"),
            require = 0
    )
    private static HashBiMap<DyeColor, Integer> getPotFromDyeColor(int expectedSize, Operation<HashBiMap<DyeColor, Integer>> original) {
        var map = original.call(expectedSize);
        ChalkCompat.registerColors(map);
        return map;
    }

}

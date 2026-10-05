package com.possible_triangle.dye_the_world.mixins;

import com.mojang.datafixers.util.Pair;
import com.possible_triangle.dye_the_world.compat.LabelsCompat;
import java.util.List;
import java.util.Map;
import net.mehvahdjukaar.labels.ColorManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ColorManager.class, remap = false)
public class LabelsColorManagerMixin
{

    @Shadow
    @Final
    private static Map<DyeColor, Pair<Integer, Integer>> COLORS;

    @Inject(
            method = "apply(Ljava/util/List;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("TAIL"),
            require = 0
    )
    private void registerDyedColors(List<Integer> colors, ResourceManager manager, ProfilerFiller profiler, CallbackInfo ci) {
        LabelsCompat.registerColors(COLORS);
    }

}

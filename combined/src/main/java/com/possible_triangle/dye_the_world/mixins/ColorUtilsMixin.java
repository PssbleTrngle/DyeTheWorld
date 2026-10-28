package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.Dyes;
import com.possible_triangle.dye_the_world.compat.RailwaysCompat;
import com.railwayteam.railways.util.ColorUtils;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ColorUtils.class, remap = false)
public class ColorUtilsMixin {

    @Inject(
            method = "mapColorFromDye(Lnet/minecraft/world/item/DyeColor;)Lnet/minecraft/world/level/material/MapColor;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void mapColorFromDye(DyeColor dye, CallbackInfoReturnable<MapColor> cir) {
        if (Dyes.isVanilla(dye)) return;
        cir.setReturnValue(dye.getMapColor());
    }

    @Inject(
            method = "getDyeColorDyeItem(Lnet/minecraft/world/item/DyeColor;)Lnet/minecraft/world/item/Item;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void getDyeColorDyeItem(DyeColor dye, CallbackInfoReturnable<Item> cir) {
        if (Dyes.isVanilla(dye)) return;
        cir.setReturnValue(RailwaysCompat.DYE_ITEMS.get(dye).get());
    }

    @Inject(
            method = "coloredName(Ljava/lang/String;)Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coloredName(String string, CallbackInfoReturnable<String> cir) {
        var translation = RailwaysCompat.TRANSLATIONS.get(string);
        if (translation != null) cir.setReturnValue(translation);
    }

}

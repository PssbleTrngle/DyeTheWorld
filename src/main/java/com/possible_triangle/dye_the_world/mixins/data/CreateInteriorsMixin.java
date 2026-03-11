package com.possible_triangle.dye_the_world.mixins.data;

import com.aesefficio.interiors.CreateInteriors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CreateInteriors.class, remap = false)
public class CreateInteriorsMixin {

    @Inject(
            method = "provideDefaultLang",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void cancelProvideDefaultLang(String fileName, CallbackInfo ci) {
        ci.cancel();
    }

}

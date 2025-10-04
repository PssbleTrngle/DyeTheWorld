package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.Dyes;
import com.railwayteam.railways.content.smokestack.particles.chimneypush.ChimneyPushParticleData;
import com.railwayteam.railways.util.ColorUtils;
import net.createmod.catnip.theme.Color;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChimneyPushParticleData.class, remap = false)
public class ChimneyPushParticleDataMixin {

    @Inject(
            method = "create(ZZLnet/minecraft/world/item/DyeColor;)Lcom/railwayteam/railways/content/smokestack/particles/chimneypush/ChimneyPushParticleData;",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 0
    )
    private static void overwritePuffColor(boolean small, boolean leadOnly, DyeColor dye, CallbackInfoReturnable<ChimneyPushParticleData<?>> cir) {
        if (Dyes.isVanilla(dye)) return;
        var color = new Color(dye.getTextureDiffuseColor());
        cir.setReturnValue(ChimneyPushParticleData.create(small, leadOnly, color.getRed(), color.getGreen(), color.getBlue()));
    }

}

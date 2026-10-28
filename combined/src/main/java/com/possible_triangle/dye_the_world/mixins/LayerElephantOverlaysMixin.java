package com.possible_triangle.dye_the_world.mixins;

import com.github.alexthe666.alexsmobs.client.render.layer.LayerElephantOverlays;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.possible_triangle.dye_the_world.Dyes;
import com.possible_triangle.dye_the_world.compat.AlexsMobsCompat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LayerElephantOverlays.class, remap = false)
public class LayerElephantOverlaysMixin {

    @ModifyExpressionValue(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILcom/github/alexthe666/alexsmobs/entity/EntityElephant;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/alexthe666/alexsmobs/entity/EntityElephant;isTrader()Z",
                    ordinal = 1
            ),
            require = 0
    )
    public boolean overwriteCarpetLayer(boolean original, @Local DyeColor dye) {
        return original || dye.getId() > 15;
    }

    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILcom/github/alexthe666/alexsmobs/entity/EntityElephant;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType;entityCutoutNoCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"
            ),
            require = 0
    )
    public RenderType overwriteCarpetLayer(ResourceLocation texture, Operation<RenderType> original, @Local DyeColor dye) {
        if (Dyes.isVanilla(dye)) return original.call(texture);
        return original.call(AlexsMobsCompat.getDecorTexture(dye));
    }

}

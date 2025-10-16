package com.possible_triangle.dye_the_world.mixins;

import com.blackgear.vanillabackport.client.level.entities.layer.GhastHarnessLayer;
import com.blackgear.vanillabackport.common.level.entities.happyghast.HappyGhast;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.possible_triangle.dye_the_world.index.DyedVanillaBackport;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GhastHarnessLayer.class, remap = false)
public class GhastHarnessLayerMixin {

    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILcom/blackgear/vanillabackport/common/level/entities/happyghast/HappyGhast;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;findFirst()Ljava/util/Optional;"),
            require = 0
    )
    private Optional<ResourceLocation> overwriteTexture(Stream<ResourceLocation> instance, Operation<Optional<ResourceLocation>> original, @Local HappyGhast entity) {
        var item = entity.getItemBySlot(EquipmentSlot.CHEST);
        return DyedVanillaBackport.textureOf(item.getItem())
                .or(() -> original.call(instance));
    }

}

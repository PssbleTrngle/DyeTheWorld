package com.possible_triangle.dye_the_world.arts_and_crafts.mixins;

import com.kekecreations.arts_and_crafts.client.renderer.bewlr.ArtsAndCraftsBEWLR;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ArtsAndCraftsBEWLR.class, remap = false)
public class ArtsAndCraftsBEWLRMixin {

    @ModifyConstant(
            method = {
                    "<init>",
                    "renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"
            },
            constant = @Constant(intValue = 15)
    )
    private static int allowModdedPots(int constant) {
        return Integer.MAX_VALUE;
    }

}

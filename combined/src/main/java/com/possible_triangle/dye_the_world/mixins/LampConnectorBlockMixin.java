package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.index.DyedFurniture;
import com.starfish_studios.another_furniture.block.LampConnectorBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LampConnectorBlock.class, remap = false)
public class LampConnectorBlockMixin {

    @Inject(
            method = "getLampByColor",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void getLampByColor(DyeColor dye, CallbackInfoReturnable<Block> cir) {
        var instance = DyedFurniture.LAMPS.get(dye);
        if (instance != null) {
            cir.setReturnValue(instance.get());
        }
    }

}

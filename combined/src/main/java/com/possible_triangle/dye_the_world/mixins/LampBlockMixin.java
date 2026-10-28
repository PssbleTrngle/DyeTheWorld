package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.index.DyedFurniture;
import com.starfish_studios.another_furniture.block.LampBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LampBlock.class, remap = false)
public class LampBlockMixin {

    @Inject(
            method = "getLampConnectorByColor",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void getLampConnectorByColor(DyeColor dye, CallbackInfoReturnable<Block> cir) {
        var instance = DyedFurniture.LAMPS_CONNECTORS.get(dye);
        if (instance != null) {
            cir.setReturnValue(instance.get());
        }
    }

}

package com.possible_triangle.dye_the_world.arts_and_crafts.mixins;

import com.kekecreations.arts_and_crafts.core.registry.ACBlocks;
import com.kekecreations.arts_and_crafts.core.registry.ACItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = {ACBlocks.class, ACItems.class}, remap = false)
public class PotsMixin {

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 15)
    )
    private static int registerModdedPots(int constant) {
        return Integer.MAX_VALUE;
    }

}

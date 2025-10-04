package com.possible_triangle.dye_the_world.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.dye_the_world.Constants;
import com.possible_triangle.dye_the_world.Dyes;
import java.util.Arrays;
import java.util.stream.Stream;
import net.blay09.mods.waystones.block.ModBlocks;
import net.minecraft.world.item.DyeColor;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ModBlocks.class, remap = false)
public class WaystonesModBlocksMixin {

    @Unique
    private static DyeColor[] extendColors(DyeColor[] original) {
        return Stream.concat(
                Arrays.stream(original),
                Dyes.dyesFor(Constants.Mods.WAYSTONES).stream()
        ).toArray(DyeColor[]::new);
    }

    @WrapOperation(
            method = {
                    "<clinit>",
                    "initialize(Lnet/blay09/mods/balm/api/block/BalmBlocks;)V",
            },
            at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/blay09/mods/waystones/block/ModBlocks;sharestoneColors:[Lnet/minecraft/world/item/DyeColor;"),
            require = 0
    )
    private static DyeColor[] overwriteSharestoneCount(Operation<DyeColor[]> original) {
        return extendColors(original.call());
    }

}

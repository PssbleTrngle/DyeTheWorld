package com.possible_triangle.dye_the_world.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.dye_the_world.Dyes;
import java.util.stream.Stream;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.quark.integration.jei.QuarkJeiPlugin;

@Mixin(value = QuarkJeiPlugin.class, remap = false)
public class QuarkJeiPluginMixin {

    @WrapOperation(
            method = "registerInfluenceRecipes(Lmezz/jei/api/registration/IRecipeRegistration;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Arrays;stream([Ljava/lang/Object;)Ljava/util/stream/Stream;"),
            require = 0
    )
    private Stream<DyeColor> colors(Object[] array, Operation<Stream<DyeColor>> original) {
        return Dyes.VANILLA_DYES.stream();
    }

}

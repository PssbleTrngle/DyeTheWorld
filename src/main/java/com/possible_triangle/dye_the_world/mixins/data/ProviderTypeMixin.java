package com.possible_triangle.dye_the_world.mixins.data;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProviderType.class)
public interface ProviderTypeMixin {

    @WrapOperation(
            method = "create(Lcom/tterrag/registrate/providers/ProviderType;Lcom/tterrag/registrate/AbstractRegistrate;Lnet/neoforged/neoforge/data/event/GatherDataEvent;Ljava/util/Map;Ljava/util/concurrent/CompletableFuture;)Lcom/tterrag/registrate/providers/RegistrateProvider;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/data/DataGenerator;getPackOutput()Lnet/minecraft/data/PackOutput;")
    )
    private static PackOutput overlayOutput(DataGenerator instance, Operation<PackOutput> original, @Local(argsOnly = true) AbstractRegistrate<?> parent) {
        var root = original.call(instance);
        if(parent instanceof DyedRegistrate) {
            return new PackOutput(root.getOutputFolder().resolve(parent.getModid()));
        }
        return root;
    }

}

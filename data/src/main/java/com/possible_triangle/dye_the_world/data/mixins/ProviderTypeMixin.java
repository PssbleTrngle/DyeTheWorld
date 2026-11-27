package com.possible_triangle.dye_the_world.data.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateProvider;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProviderType.class)
public interface ProviderTypeMixin {

    @Unique
    private static PackOutput modifedOutput(ProviderType<?> type, AbstractRegistrate<?> parent, PackOutput output) {
        if (type == ProviderType.GENERIC_SERVER) return output;
        if (!(parent instanceof DyedRegistrate registrate)) return output;

        var folder = registrate.getMod();
        return new PackOutput(output.getOutputFolder().resolve(folder));
    }

    @WrapOperation(
            method = "create(Lcom/tterrag/registrate/providers/ProviderType;Lcom/tterrag/registrate/AbstractRegistrate;Lnet/neoforged/neoforge/data/event/GatherDataEvent;Ljava/util/Map;Ljava/util/concurrent/CompletableFuture;)Lcom/tterrag/registrate/providers/RegistrateProvider;",
            at = @At(
                    value = "NEW",
                    target = "(Lcom/tterrag/registrate/providers/ProviderType;Lcom/tterrag/registrate/AbstractRegistrate;Lnet/neoforged/neoforge/data/event/GatherDataEvent;Ljava/util/Map;Lnet/minecraft/data/PackOutput;Lnet/neoforged/neoforge/common/data/ExistingFileHelper;Ljava/util/concurrent/CompletableFuture;)Lcom/tterrag/registrate/providers/ProviderType$Context;"
            )
    )
    private static <T extends RegistrateProvider> ProviderType.Context<T> modifyOutput(ProviderType<T> type, AbstractRegistrate<?> parent, GatherDataEvent event, Map<ProviderType<?>, RegistrateProvider> existing, PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> provider, Operation<ProviderType.Context<T>> original) {
        return original.call(type, parent, event, existing, modifedOutput(type, parent, output), fileHelper, provider);
    }

}

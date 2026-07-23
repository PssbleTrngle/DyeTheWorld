package com.possible_triangle.dye_the_world.mixins.data;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Map;
import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin {

    @WrapOperation(
            method = "register(ILnet/minecraft/resources/ResourceKey;Ljava/lang/Object;Lnet/minecraft/core/RegistrationInfo;)Lnet/minecraft/core/Holder$Reference;",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z")
    )
    public boolean allowDuplicateRegisters(Map<?, ?> instance, Object o, Operation<Boolean> original) {
        return false;
    }

}

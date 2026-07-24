package com.possible_triangle.dye_the_world.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.dye_depot.registry.DDDyes;
import net.mehvahdjukaar.snowyspirit.common.entity.GingyEntity;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GingyEntity.class, remap = false)
public class GingyEntityMixin {

    @WrapOperation(
            method = "finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;",
            at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/snowyspirit/common/entity/GingyEntity;setColor(Lnet/minecraft/world/item/DyeColor;)V")
    )
    private void disableDepotColors(GingyEntity instance, DyeColor collarColor, Operation<Void> original) {
        if (DDDyes.isModDye(collarColor)) {
            var vanillaColor = DyeColor.values()[instance.getRandom().nextInt(16)];
            original.call(instance, vanillaColor);
        } else {
            original.call(instance, collarColor);
        }
    }

}

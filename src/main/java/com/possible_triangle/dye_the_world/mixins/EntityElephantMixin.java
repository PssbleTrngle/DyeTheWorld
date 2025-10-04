package com.possible_triangle.dye_the_world.mixins;

import com.github.alexthe666.alexsmobs.entity.EntityElephant;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.possible_triangle.dye_the_world.Dyes;
import com.possible_triangle.dye_the_world.compat.AlexsMobsCompat;
import javax.annotation.Nullable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityElephant.class, remap = false)
public abstract class EntityElephantMixin {

    @Shadow
    @Nullable
    public abstract DyeColor getColor();

    @ModifyReturnValue(
            method = "getCarpetItemBeingWorn()Lnet/minecraft/world/item/Item;",
            at = @At("RETURN"),
            require = 0
    )
    public Item overwriteCarpetLayer(Item original) {
        var dye = getColor();
        if (Dyes.isVanilla(dye)) return original;
        return AlexsMobsCompat.getCarpet(dye);
    }

}

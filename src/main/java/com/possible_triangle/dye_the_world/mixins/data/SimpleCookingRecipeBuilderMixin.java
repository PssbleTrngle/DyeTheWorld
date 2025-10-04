package com.possible_triangle.dye_the_world.mixins.data;

import com.possible_triangle.dye_the_world.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleCookingRecipeBuilder.class)
public class SimpleCookingRecipeBuilderMixin {

    @Unique
    private static final ResourceLocation BAKING_ID = ResourceLocation.fromNamespaceAndPath(Constants.Mods.CLAYWORKS, "baking");

    @Inject(
            method = "determineRecipeCategory(Lnet/minecraft/world/item/crafting/RecipeSerializer;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/crafting/CookingBookCategory;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void allowBakingCategory(RecipeSerializer<? extends AbstractCookingRecipe> serializer, ItemLike item, CallbackInfoReturnable<CookingBookCategory> cir) {
        var serializerId = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);
        if (serializerId.equals(BAKING_ID)) {
            cir.setReturnValue(CookingBookCategory.BLOCKS);
        }
    }

}

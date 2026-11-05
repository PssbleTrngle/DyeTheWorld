package com.possible_triangle.dye_the_world.alexs_caves

import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType

object DyedAlexsCaves {
    private val TOXIC_TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ALEXS_CAVES.createId("toxic_caves"))

    val RADON_LAMPS =
        REGISTRATE.dyed { dye ->
            `object`("radon_lamp_$dye")
                .dyedBlock(dye)
                .properties { it.strength(2.0f, 11.0f) }
                .properties { it.requiresCorrectToolForDrops() }
                .properties { it.lightLevel { 15 } }
                .properties { it.sound(SoundType.GLASS) }
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Radon Lamp")
                .germanLang("${dye.germanTranslation(Genus.F)} Radonlampe")
                .withItem {
                    tab(TOXIC_TAB)
                    tag(AlexsCavesTags.Items.RADON_LAMPS)
                    recipe { context, provider ->
                        val radonBottle = BuiltInRegistries.ITEM.getOrThrow(ALEXS_CAVES.createId("radon_bottle"))

                        ShapedRecipeBuilder
                            .shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 4)
                            .pattern("DRD")
                            .pattern("RGR")
                            .pattern("DRD")
                            .define('D', dye.tag)
                            .define('R', radonBottle)
                            .define('G', Blocks.GLOWSTONE)
                            .unlockedBy("has_radon_bottle", RegistrateRecipeProvider.has(radonBottle))
                            .unlockedBy("has_glowstone", RegistrateRecipeProvider.has(Blocks.GLOWSTONE))
                            .save(provider)

                        provider.shapedDyeingRecipe(dye, AlexsCavesTags.Items.RADON_LAMPS, context)
                    }
                }.register()
        }
}

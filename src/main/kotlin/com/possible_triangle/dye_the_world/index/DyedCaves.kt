package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.ALEXS_CAVES
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.dyesFor
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
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType

object DyedCaves {
    private val TOXIC_TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation(ALEXS_CAVES, "toxic_caves"))

    val RADON_LAMPS =
        dyesFor(ALEXS_CAVES).associateWith { dye ->
            REGISTRATE
                .`object`("radon_lamp_$dye")
                .dyedBlock(dye, ALEXS_CAVES)
                .properties { it.strength(2.0f, 11.0f) }
                .properties { it.requiresCorrectToolForDrops() }
                .properties { it.lightLevel { 15 } }
                .properties { it.sound(SoundType.GLASS) }
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Radon Lamp")
                .germanLang("${dye.germanTranslation(Genus.F)} Radonlampe")
                .blockstate { c, p ->
                    val model = p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/$ALEXS_CAVES/${c.name}"))
                    p.simpleBlock(c.get(), model)
                }.withItem {
                    tab(TOXIC_TAB)
                    optionalTag(DyedTags.Items.RADON_LAMPS)
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

                        provider.shapedDyeingRecipe(dye, DyedTags.Items.RADON_LAMPS, context)
                    }
                }.register()
        }

    fun register() {
        // Loads this class
    }
}

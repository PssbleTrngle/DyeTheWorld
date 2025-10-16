package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.ELEVATORS
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.translation
import net.mehvahdjukaar.snowyspirit.common.block.GlowLightsBlock
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Items

object DyedElevators {

    private val REGISTRATE = DyedRegistrate.create(ELEVATORS)
    private val DYES = dyesFor(ELEVATORS)

    val ELEVATOR_BLOCKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("elevator_$dye")
            .dyedBlock(dye) { GlowLightsBlock(dye) }
            .blockstate { context, provider ->
                val texture = Constants.MOD_ID.createId("block/$ELEVATORS/$dye")
                val model = provider.models().cubeAll(context.name, texture)
                provider.simpleBlock(context.get(), model)
            }
            .optionalTag(DyedTags.Blocks.ELEVATORS)
            .lang("${dye.translation} Elevator")
            .germanLang("${dye.germanTranslation(Genus.M)} Aufzug")
            .withItem {
                optionalTag(DyedTags.Items.ELEVATORS)
                recipe { context, provider ->
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, context.get())
                        .pattern("WWW")
                        .pattern("WEW")
                        .pattern("WWW")
                        .define('W', dye.blockOf("wool"))
                        .defineUnlocking('E', Items.ENDER_PEARL)
                        .save(provider)

                    provider.dyeingRecipe(dye, DyedTags.Items.ELEVATORS, context)
                }
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}
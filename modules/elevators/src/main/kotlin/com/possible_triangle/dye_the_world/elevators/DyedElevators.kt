package com.possible_triangle.dye_the_world.elevators

import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.translation
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block

object DyedElevators {
    val ELEVATOR_BLOCKS =
        REGISTRATE.dyed { dye ->
            `object`("elevator_$dye")
                .dyedBlock(dye, ::Block)
                .tag(Tags.BLOCK)
                .lang("${dye.translation} Elevator")
                .germanLang("${dye.germanTranslation(Genus.M)} Aufzug")
                .withItem {
                    tag(Tags.ITEM)
                    recipe { context, provider ->
                        ShapedRecipeBuilder
                            .shaped(RecipeCategory.MISC, context.get())
                            .pattern("WWW")
                            .pattern("WEW")
                            .pattern("WWW")
                            .define('W', dye.blockOf("wool"))
                            .defineUnlocking('E', Items.ENDER_PEARL)
                            .save(provider)

                        provider.dyeingRecipe(dye, Tags.ITEM, context)
                    }
                }.register()
        }
}

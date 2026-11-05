package com.possible_triangle.dye_the_world.create_numismatics

import com.possible_triangle.dye_the_world.DyedTags
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.unlockedBy
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.translation
import com.simibubi.create.AllItems
import dev.ithundxr.createnumismatics.registry.NumismaticsTags
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items

object DyedCreateNumismatics {
    private val TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, CREATE_NUMISMATICS.createId("main"))

    val CARDS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_card")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} Card")
                .germanLang("${dye.germanTranslation(Genus.F)} Karte")
                .tab(TAB)
                .recipe { context, provider ->
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, context.get())
                        .pattern("PSD")
                        .defineUnlocking('P', AllItems.PRECISION_MECHANISM)
                        .define('S', DyedTags.Materials.IRON.plate)
                        .define('D', dye.tag)
                        .save(provider)
                }.optionalTag(NumismaticsTags.AllItemTags.CARDS.tag)
                .register()
        }

    val ID_CARDS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_id_card")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} ID Card")
                .germanLang("${dye.germanTranslation(Genus.M)} Ausweis")
                .tab(TAB)
                .recipe { context, provider ->
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, context.get())
                        .pattern(" D ")
                        .pattern("SPB")
                        .unlockedBy(AllItems.PRECISION_MECHANISM)
                        .define('P', Items.PAPER)
                        .define('S', DyedTags.Materials.IRON.plate)
                        .define('D', dye.tag)
                        .define('B', DyedTags.Materials.BRASS.nugget)
                        .save(provider)
                }.optionalTag(NumismaticsTags.AllItemTags.ID_CARDS.tag)
                .register()
        }
}

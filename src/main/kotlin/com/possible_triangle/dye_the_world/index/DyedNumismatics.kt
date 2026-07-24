package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.NUMISMATICS
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.data.flat
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.unlockedBy
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import com.simibubi.create.AllItems
import dev.ithundxr.createnumismatics.registry.NumismaticsTags
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items

object DyedNumismatics {
    private val DYES = dyesFor(NUMISMATICS)

    private val REGISTRATE = DyedRegistrate.create(NUMISMATICS)

    private val TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, NUMISMATICS.createId("main"))

    val CARDS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_card")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} Card")
                .germanLang("${dye.germanTranslation(Genus.F)} Karte")
                .tab(TAB)
                .flat("card")
                .recipe { context, provider ->
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, context.get())
                        .pattern("PSD")
                        .defineUnlocking('P', AllItems.PRECISION_MECHANISM)
                        .define('S', DyedTags.Items.IRON_PLATES)
                        .define('D', dye.tag)
                        .save(provider)
                }.optionalTag(NumismaticsTags.AllItemTags.CARDS.tag)
                .register()
        }

    val ID_CARDS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_id_card")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} ID Card")
                .germanLang("${dye.germanTranslation(Genus.M)} Ausweis")
                .tab(TAB)
                .flat("id_card")
                .recipe { context, provider ->
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, context.get())
                        .pattern(" D ")
                        .pattern("SPB")
                        .unlockedBy(AllItems.PRECISION_MECHANISM)
                        .define('P', Items.PAPER)
                        .define('S', DyedTags.Items.IRON_PLATES)
                        .define('D', dye.tag)
                        .define('B', DyedTags.Items.BRASS_NUGGETS)
                        .save(provider)
                }.optionalTag(NumismaticsTags.AllItemTags.ID_CARDS.tag)
                .register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

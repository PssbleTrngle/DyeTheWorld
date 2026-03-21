package com.possible_triangle.dye_the_world.index

import com.blackgear.vanillabackport.client.level.entities.layer.GhastHarnessLayer
import com.blackgear.vanillabackport.common.level.items.HarnessItem
import com.blackgear.vanillabackport.common.registries.ModBlocks
import com.blackgear.vanillabackport.core.data.tags.ModItemTags
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.VANILLA_BACKPORT
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.extensions.*
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object DyedVanillaBackport {

    private val DYES = dyesFor(VANILLA_BACKPORT)

    private val TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, VANILLA_BACKPORT.createId("chase_the_skies"))

    val HARNESSES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_harness")
            .dyedItem(dye, VANILLA_BACKPORT, ::HarnessItem)
            .properties { it.stacksTo(1) }
            .lang("${dye.translation} Harness")
            .germanLang("${dye.germanTranslation(Genus.I)} Geschirr")
            .optionalTag(ModItemTags.HARNESSES)
            .recipe { c, p ->
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                    .pattern("LLL")
                    .pattern("G#G")
                    .define('#', dye.blockOf("wool"))
                    .define('G', Blocks.GLASS)
                    .define('L', Items.LEATHER)
                    .unlockedBy(ModBlocks.DRIED_GHAST.get())
                    .group("harness")
                    .save(p)
            }
            .model { c, p ->
                p.generated(c, Constants.MOD_ID.createId("item/$VANILLA_BACKPORT/harness/$dye"))
            }
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .tab(TAB)
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

    private fun registerHarnessLayers() {
        HARNESSES.forEach { (dye, item) ->
            val stack = item.asStack()
            val texture = Constants.MOD_ID.createId("textures/entity/$VANILLA_BACKPORT/harness/${dye}.png")
            GhastHarnessLayer.TEXTURE_BY_ITEM[stack] = texture
        }
    }

}
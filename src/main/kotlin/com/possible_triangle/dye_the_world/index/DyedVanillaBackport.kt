package com.possible_triangle.dye_the_world.index

import com.blackgear.vanillabackport.common.level.items.CushionItem
import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags
import com.blackgear.vanillabackport.core.data.tags.ModItemTags
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.VANILLA_BACKPORT
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.colorSet
import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.extensions.*
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BundleItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.BundleContents
import net.minecraft.world.level.block.Blocks

object DyedVanillaBackport {
    private val DYES = dyesFor(VANILLA_BACKPORT)

    private val TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, VANILLA_BACKPORT.createId("vanilla_backport"))

    val HARNESSES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_harness")
                .dyedItem(dye, VANILLA_BACKPORT, ::Item)
                .properties { it.stacksTo(1) }
                .lang("${dye.translation} Harness")
                .germanLang("${dye.germanTranslation(Genus.I)} Geschirr")
                .optionalTag(ModItemTags.HARNESSES)
                .recipe { context, provider ->
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, context.get())
                        .pattern("LLL")
                        .pattern("G#G")
                        .define('#', dye.blockOf("wool"))
                        .define('G', Blocks.GLASS)
                        .define('L', Items.LEATHER)
                        .unlockedBy(ModBlocks.DRIED_GHAST.get())
                        .group("harness")
                        .save(provider)
                }.model { c, p ->
                    p.generated(c, Constants.MOD_ID.createId("item/$VANILLA_BACKPORT/harness/$dye"))
                }.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .tab(TAB)
                .register()
        }

    val BUNDLES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_bundle")
                .dyedItem(dye, VANILLA_BACKPORT, ::BundleItem)
                .properties { it.stacksTo(1) }
                .properties { it.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY) }
                .lang("${dye.translation} Bundle")
                .germanLang("${dye.germanTranslation(Genus.I)} Bündel")
                .optionalTag(ModItemTags.BUNDLES)
                .model { c, p ->
                    p.generated(c, Constants.MOD_ID.createId("item/$VANILLA_BACKPORT/bundle/$dye"))
                }.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .tab(TAB)
                .register()
        }

    private val WOOL = dyedBlockMap(VANILLA_BACKPORT, "wool")

    val WOOL_STAIRS =
        REGISTRATE.createStairs(
            WOOL,
            VANILLA_BACKPORT.createId("wool"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Wolltreppe")
                optionalTag(ModBlockTags.WOOL_STAIRS)
            },
            modifyItem = {
                tab(TAB)
                optionalTag(ModItemTags.WOOL_STAIRS)
            },
            existingTexture = true,
            stone = false,
        )

    val WOOL_SLABS =
        REGISTRATE.createSlabs(
            WOOL,
            VANILLA_BACKPORT.createId("wool"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Wollestufe")
                optionalTag(ModBlockTags.WOOL_SLABS)
            },
            modifyItem = {
                tab(TAB)
                optionalTag(ModItemTags.WOOL_SLABS)
            },
            existingTexture = true,
            stone = false,
        )

    val CUSHIONS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_cushion")
                .dyedItem(dye, VANILLA_BACKPORT) { CushionItem(it, dye) }
                .properties { it.stacksTo(16) }
                .lang("${dye.translation} Cushion")
                .germanLang("${dye.germanTranslation(Genus.I)} Kissen")
                .optionalTag(ModItemTags.CUSHIONS)
                .model { c, p ->
                    p.generated(c, Constants.MOD_ID.createId("item/$VANILLA_BACKPORT/cushion/$dye"))
                }.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .tab(TAB)
                .register()
        }

    fun register() {
        REGISTRATE.colorSet(ResourceLocation.withDefaultNamespace("bundle"), VANILLA_BACKPORT)

        REGISTRATE.register()
    }
}

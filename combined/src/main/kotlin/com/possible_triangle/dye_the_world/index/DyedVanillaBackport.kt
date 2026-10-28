package com.possible_triangle.dye_the_world.index

import com.blackgear.vanillabackport.common.level.items.CushionItem
import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags
import com.blackgear.vanillabackport.core.data.tags.ModItemTags
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.MORE_CONCRETE
import com.possible_triangle.dye_the_world.Constants.Mods.VANILLA_BACKPORT
import com.possible_triangle.dye_the_world.data.colorSet
import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder.shaped
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BundleItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.BundleContents
import net.minecraft.world.level.block.Blocks

object DyedVanillaBackport {
    private val REGISTRATE =
        DyedRegistrate.create(
            ResourceLocation.DEFAULT_NAMESPACE,
            mod = VANILLA_BACKPORT,
            migrate = true,
        )

    private val DYES = dyesFor(VANILLA_BACKPORT)

    private val TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, VANILLA_BACKPORT.createId("vanilla_backport"))

    val HARNESSES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_harness")
                .dyedItem(dye, ::Item)
                .properties { it.stacksTo(1) }
                .lang("${dye.translation} Harness")
                .germanLang("${dye.germanTranslation(Genus.I)} Geschirr")
                .optionalTag(ModItemTags.HARNESSES)
                .recipe { context, provider ->
                    provider.dyeingRecipe(dye, ModItemTags.HARNESSES, context) {
                        group("harness_dye")
                    }

                    shaped(RecipeCategory.MISC, context.get())
                        .pattern("LLL")
                        .pattern("G#G")
                        .define('#', dye.blockOf("wool"))
                        .define('G', Blocks.GLASS)
                        .define('L', Items.LEATHER)
                        .unlockedBy(ModBlocks.DRIED_GHAST.get())
                        .group("harness")
                        .save(provider)
                }.model { c, p ->
                    p.generated(c, VANILLA_BACKPORT.createId("item/harness/$dye"))
                }.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .tab(TAB)
                .register()
        }

    val BUNDLES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_bundle")
                .dyedItem(dye, ::BundleItem)
                .properties { it.stacksTo(1) }
                .properties { it.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY) }
                .lang("${dye.translation} Bundle")
                .germanLang("${dye.germanTranslation(Genus.I)} Bündel")
                .optionalTag(ModItemTags.BUNDLES)
                .model { c, p ->
                    p.generated(c, VANILLA_BACKPORT.createId("item/bundle/$dye"))
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
                addMiscData(ProviderType.RECIPE) { provider ->
                    provider.dyeingRecipe(dye, ModItemTags.WOOL_STAIRS, ::getEntry) {
                        group("wool_stairs")
                    }
                }
            },
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
                addMiscData(ProviderType.RECIPE) { provider ->
                    provider.dyeingRecipe(dye, ModItemTags.WOOL_SLABS, ::getEntry) {
                        group("wool_slabs")
                    }
                }
            },
            stone = false,
        )

    private val CONCRETE = dyedBlockMap(MORE_CONCRETE, "concrete")

    val CONCRETE_SLABS =
        REGISTRATE.createSlabs(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Betonstufe")
                optionalTag(DyedTags.Blocks.CONCRETE_SLABS)
                noMigrate()
            },
            modifyItem = {
                optionalTag(DyedTags.Items.CONCRETE_SLABS)
                tab(TAB)
            },
        )

    val CONCRETE_STAIRS =
        REGISTRATE.createStairs(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Betontreppe")
                optionalTag(DyedTags.Blocks.CONCRETE_STAIRS)
                noMigrate()
            },
            modifyItem = {
                optionalTag(DyedTags.Items.CONCRETE_STAIRS)
                tab(TAB)
            },
        )

    val CUSHIONS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_cushion")
                .dyedItem(dye) { CushionItem(it, dye) }
                .properties { it.stacksTo(16) }
                .lang("${dye.translation} Cushion")
                .germanLang("${dye.germanTranslation(Genus.I)} Kissen")
                .optionalTag(ModItemTags.CUSHIONS)
                .model { context, provider ->
                    provider.generated(context, VANILLA_BACKPORT.createId("item/cushion/$dye"))
                }.tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .recipe { context, provider ->
                    provider.dyeingRecipe(dye, ModItemTags.CUSHIONS, context) {
                        group("cushion_dye")
                    }

                    shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
                        .pattern("###")
                        .defineUnlocking('#', WOOL_SLABS[dye]!!)
                        .group("cushion")
                        .save(provider)
                }.tab(TAB)
                .register()
        }

    fun register() {
        REGISTRATE.register()

        REGISTRATE.colorSet(ResourceLocation.withDefaultNamespace("bundle"), VANILLA_BACKPORT)
    }
}

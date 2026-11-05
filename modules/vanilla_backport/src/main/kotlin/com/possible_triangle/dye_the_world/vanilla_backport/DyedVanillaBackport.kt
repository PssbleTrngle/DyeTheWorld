package com.possible_triangle.dye_the_world.vanilla_backport

import com.blackgear.vanillabackport.client.level.layer.GhastEquipmentManager
import com.blackgear.vanillabackport.common.api.modules.bundle_ui.BundleFeatures
import com.blackgear.vanillabackport.common.level.entities.decoration.Cushion
import com.blackgear.vanillabackport.common.level.items.CushionItem
import com.blackgear.vanillabackport.common.registries.blocks.ModBlocks
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags
import com.blackgear.vanillabackport.core.data.tags.ModItemTags
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.data.colorSet
import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder.shaped
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.DEFAULT_NAMESPACE
import net.minecraft.world.item.BundleItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.BundleContents
import net.minecraft.world.level.block.Blocks
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent

object DyedVanillaBackport {
    private val TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, VANILLA_BACKPORT.createId("vanilla_backport"))

    val HARNESSES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_harness")
                .dyedItem(dye, ::Item)
                .properties { it.stacksTo(1) }
                .lang("${dye.translation} Harness")
                .germanLang("${dye.germanTranslation(Genus.I)} Geschirr")
                .tag(ModItemTags.HARNESSES)
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
        REGISTRATE.dyed { dye ->
            `object`("${dye}_bundle")
                .dyedItem(dye, ::BundleItem)
                .properties { it.stacksTo(1) }
                .properties { it.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY) }
                .lang("${dye.translation} Bundle")
                .germanLang("${dye.germanTranslation(Genus.I)} Bündel")
                .tag(ModItemTags.BUNDLES)
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
                tag(ModBlockTags.WOOL_STAIRS)
            },
            modifyItem = {
                tab(TAB)
                tag(ModItemTags.WOOL_STAIRS)
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
                tag(ModBlockTags.WOOL_SLABS)
            },
            modifyItem = {
                tab(TAB)
                tag(ModItemTags.WOOL_SLABS)
                addMiscData(ProviderType.RECIPE) { provider ->
                    provider.dyeingRecipe(dye, ModItemTags.WOOL_SLABS, ::getEntry) {
                        group("wool_slabs")
                    }
                }
            },
            stone = false,
        )

    private val CONCRETE = dyedBlockMap(DEFAULT_NAMESPACE, "concrete")

    val CONCRETE_SLABS =
        REGISTRATE.createSlabs(
            CONCRETE,
            DEFAULT_NAMESPACE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Betonstufe")
                tag(VanillaTags.Blocks.CONCRETE_SLABS)
                noMigrate()
            },
            modifyItem = {
                tag(VanillaTags.Items.CONCRETE_SLABS)
                tab(TAB)
            },
        )

    val CONCRETE_STAIRS =
        REGISTRATE.createStairs(
            CONCRETE,
            DEFAULT_NAMESPACE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Betontreppe")
                tag(VanillaTags.Blocks.CONCRETE_STAIRS)
                noMigrate()
            },
            modifyItem = {
                tag(VanillaTags.Items.CONCRETE_STAIRS)
                tab(TAB)
            },
        )

    val CUSHIONS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_cushion")
                .dyedItem(dye) { CushionItem(it, dye) }
                .properties { it.stacksTo(16) }
                .lang("${dye.translation} Cushion")
                .germanLang("${dye.germanTranslation(Genus.I)} Kissen")
                .tag(ModItemTags.CUSHIONS)
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

    init {
        REGISTRATE.colorSet(ResourceLocation.withDefaultNamespace("bundle"), VANILLA_BACKPORT)
    }

    @SubscribeEvent
    private fun registerTextures(event: FMLClientSetupEvent) =
        event.enqueueWork {
            HARNESSES.forEach { (color, item) ->
                val texture = VANILLA_BACKPORT.createId("textures/entity/harness/$color.png")
                GhastEquipmentManager.register(item, texture)
            }

            BUNDLES.forEach { (color, item) ->
                BundleFeatures.register(color, item.get())
            }

            CUSHIONS.forEach { (color, item) ->
                Cushion.register(color, color.blockOf("wool"), item.get())
            }
        }
}

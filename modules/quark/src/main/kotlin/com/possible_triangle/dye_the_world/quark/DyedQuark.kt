package com.possible_triangle.dye_the_world.quark

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.quark.data.framedGlassPaneRecipes
import com.possible_triangle.dye_the_world.quark.data.framedGlassRecipes
import com.possible_triangle.dye_the_world.quark.data.generateGlassShardLoot
import com.possible_triangle.dye_the_world.quark.data.shinglesRecipes
import com.possible_triangle.dye_the_world.quark.data.stoolBlockstate
import com.possible_triangle.dye_the_world.quark.data.stoolRecipe
import com.possible_triangle.multikulti.datagen.conditions.withConditions
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.common.Tags
import org.violetmoon.quark.content.building.block.StoolBlock
import org.violetmoon.zeta.block.ZetaGlassBlock
import org.violetmoon.zeta.block.ZetaInheritedPaneBlock

object DyedQuark {
    private val TERRACOTTA = dyedBlockMap(QUARK, "terracotta")

    val GLASS_SHARDS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_shard")
                .dyedItem(dye, ::Item)
                .optionalTab(CreativeModeTabs.INGREDIENTS) {
                    flagEnabled("glass_shard")
                }.optionalTag(QuarkTags.Items.GLASS_SHARDS)
                .recipe { context, provider ->
                    val glass = dye.itemOf("stained_glass")
                    provider.withConditions(flagCondition("glass_shard")) {
                        ShapedRecipeBuilder
                            .shaped(BUILDING_BLOCKS, glass)
                            .pattern("XX")
                            .pattern("XX")
                            .defineUnlocking('X', context.get())
                            .save(provider, Constants.MOD_ID.createId("stained_${dye}_glass_from_shards"))
                    }
                }.model { context, provider ->
                    provider.generated(context, QUARK.createId("item/${context.name}"))
                }.lang("${dye.translation} Glass Shard")
                .germanLang("${dye.germanTranslation(Genus.F)} Glasscherbe")
                .register()
        }

    val STOOLS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_stool")
                .dyedBlock(dye) { StoolBlock(null, dye) }
                .migrate(name = "${dye}_quark_stool")
                .optionalTag(QuarkTags.Blocks.QUARK_STOOLS)
                .stoolBlockstate()
                .lang("${dye.translation} Stool")
                .withItem {
                    stoolRecipe()
                    optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                        flagEnabled("stools")
                    }
                }.register()
        }

    val SHINGLES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_shingles")
                .dyedBlock(dye, ::Block)
                .initialProperties { dye.blockOf("terracotta") }
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .lang("${dye.translation} Terracotta Shingles")
                .germanLang("${dye.germanTranslation(Genus.F)} Schindeln")
                .withItem {
                    shinglesRecipes()
                    optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                        flagEnabled("shingles")
                    }
                }.register()
        }

    val SHINGLES_SLABS =
        REGISTRATE.createSlabs(
            SHINGLES,
            QUARK.createId("shingles"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Schindelstufe")
            },
            modifyItem = { dye ->
                recipe { context, provider ->
                    provider.withConditions(flagCondition("shingles")) {
                        provider.slab(SHINGLES[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                        provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context, 2)
                    }
                }
                optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                    flagEnabled("shingles")
                }
            },
        )

    val SHINGLES_STAIRS =
        REGISTRATE.createStairs(
            SHINGLES,
            QUARK.createId("shingles"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Schindeltreppe")
            },
            modifyItem = { dye ->
                recipe { context, provider ->
                    provider.withConditions(flagCondition("shingles")) {
                        provider.stairs(SHINGLES[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                        provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
                    }
                }
                optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                    flagEnabled("shingles")
                }
            },
        )

    val FRAMED_GLASS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_framed_glass")
                .dyedBlock(dye) { ZetaGlassBlock(null, null, true, it) }
                .initialProperties { Blocks.GLASS }
                .properties { it.strength(3F, 10F) }
                .optionalTag(QuarkTags.Blocks.FRAMED_GLASSES)
                .optionalTag(BlockTags.IMPERMEABLE)
                .optionalTag(BlockTags.NEEDS_STONE_TOOL)
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .optionalTag(Tags.Blocks.GLASS_BLOCKS)
                .blockstate { c, p ->
                    p.simpleBlock(
                        c.get(),
                        p
                            .models()
                            .cubeAll(c.name, c.texture)
                            .translucent(),
                    )
                }.lang("${dye.translation} Framed Glass")
                .germanLang("${dye.germanTranslation(Genus.I)} gerahmtes Glas")
                .withItem {
                    optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                        flagEnabled("framed_glass")
                    }
                    optionalTag(Tags.Items.GLASS_BLOCKS)
                    model { c, p -> p.blockItem(c).translucent() }
                    framedGlassRecipes()
                }.register()
        }

    val FRAMED_GLASS_PANES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_framed_glass_pane")
                .dyedBlock(dye) {
                    val parent = FRAMED_GLASS[dye]!!.get()
                    ZetaInheritedPaneBlock(parent, null, BlockBehaviour.Properties.ofFullCopy(parent))
                }.optionalTag(QuarkTags.Blocks.FRAMED_GLASS_PANES)
                .optionalTag(BlockTags.NEEDS_STONE_TOOL)
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .optionalTag(Tags.Blocks.GLASS_PANES)
                .blockstate { c, p ->
                    val full = FRAMED_GLASS[dye]!!.get()
                    p.paneBlockWithRenderType(
                        c.get(),
                        full.texture,
                        QUARK.createId("block/framed_glass_pane_top"),
                        TRANSLUCENT,
                    )
                }.lang("${dye.translation} Framed Glass Pane")
                .germanLang("${dye.germanTranslation(Genus.F)} gerahmte Glasscheibe")
                .withItem {
                    model { c, p ->
                        val full = FRAMED_GLASS[dye]!!.get()
                        p.generated(c, full.texture).translucent()
                    }
                    optionalTag(Tags.Items.GLASS_PANES)
                    optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                        flagEnabled("framed_glass")
                    }
                    framedGlassPaneRecipes()
                }.register()
        }

    init {
        REGISTRATE.generateGlassShardLoot()
    }
}

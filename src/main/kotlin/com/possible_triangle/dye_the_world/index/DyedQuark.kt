package com.possible_triangle.dye_the_world.index

import com.google.gson.JsonObject
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.multikulti.datagen.conditions.Condition
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
import org.violetmoon.zeta.config.ConfigFlagManager
import org.violetmoon.zeta.util.zetalist.ZetaList

data class QuarkConfigCondition(
    val flag: String,
) : Condition {
    override fun JsonObject.toFabric() {
        error("no fabric support yet")
    }

    override fun JsonObject.toForge() {
        addProperty("type", "$QUARK:flag")
        addProperty("flag", flag)
    }
}

object DyedQuark {
    private val DYES = dyesFor(QUARK)

    private val TERRACOTTA = dyedBlockMap(QUARK, "terracotta")

    val FLAG_MANAGER: ConfigFlagManager by lazy {
        val instances = ZetaList.INSTANCE.zetas
        val quark = instances.find { it.modid == QUARK }
        if (quark == null) {
            Constants.LOGGER.error("Could not find quark instance, active zeta instances are (${instances.joinToString { it.modid }})")
            throw NullPointerException("Could not find Quark Instance")
        }
        quark.configManager.configFlagManager
    }

    private fun flagEnabled(flag: String) = FLAG_MANAGER.getFlag(flag)

    fun flagCondition(flag: String): QuarkConfigCondition = QuarkConfigCondition(flag)

    val GLASS_SHARDS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_shard")
                .dyedItem(dye, QUARK, ::Item)
                .optionalTab(CreativeModeTabs.INGREDIENTS) {
                    flagEnabled("glass_shard")
                }.optionalTag(DyedTags.Items.GLASS_SHARDS)
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
                    provider.generated(context, Constants.MOD_ID.createId("item/$QUARK/${context.name}"))
                }.lang("${dye.translation} Glass Shard")
                .germanLang("${dye.germanTranslation(Genus.F)} Glasscherbe")
                .register()
        }

    val STOOLS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_quark_stool")
                .dyedBlock(dye, QUARK) { StoolBlock(null, dye) }
                .optionalTag(DyedTags.Blocks.QUARK_STOOLS)
                .quarkStoolBlockstate()
                .lang("${dye.translation} Stool")
                .withItem {
                    quarkStoolRecipe()
                    optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                        flagEnabled("stools")
                    }
                }.register()
        }

    val SHINGLES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_shingles")
                .dyedBlock(dye, QUARK, ::Block)
                .initialProperties { dye.blockOf("terracotta") }
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .cubeBlockstate("shingles")
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
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_framed_glass")
                .dyedBlock(dye, QUARK) { ZetaGlassBlock(null, null, true, it) }
                .initialProperties { Blocks.GLASS }
                .properties { it.strength(3F, 10F) }
                .optionalTag(DyedTags.Blocks.FRAMED_GLASSES)
                .optionalTag(BlockTags.IMPERMEABLE)
                .optionalTag(BlockTags.NEEDS_STONE_TOOL)
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .optionalTag(Tags.Blocks.GLASS_BLOCKS)
                .blockstate { c, p ->
                    p.simpleBlock(
                        c.get(),
                        p
                            .models()
                            .cubeAll(c.name, Constants.MOD_ID.createId("block/$QUARK/${dye}_framed_glass"))
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
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_framed_glass_pane")
                .dyedBlock(dye, QUARK) {
                    val parent = FRAMED_GLASS[dye]!!.get()
                    ZetaInheritedPaneBlock(parent, null, BlockBehaviour.Properties.ofFullCopy(parent))
                }.optionalTag(DyedTags.Blocks.FRAMED_GLASS_PANES)
                .optionalTag(BlockTags.NEEDS_STONE_TOOL)
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .optionalTag(Tags.Blocks.GLASS_PANES)
                .blockstate { c, p ->
                    p.paneBlockWithRenderType(
                        c.get(),
                        Constants.MOD_ID.createId("block/$QUARK/${dye}_framed_glass"),
                        QUARK.createId("block/framed_glass_pane_top"),
                        TRANSLUCENT,
                    )
                }.lang("${dye.translation} Framed Glass Pane")
                .germanLang("${dye.germanTranslation(Genus.F)} gerahmte Glasscheibe")
                .withItem {
                    model { c, p ->
                        p.generated(c, Constants.MOD_ID.createId("block/$QUARK/${dye}_framed_glass")).translucent()
                    }
                    optionalTag(Tags.Items.GLASS_PANES)
                    optionalTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.COLORED_BLOCKS) {
                        flagEnabled("framed_glass")
                    }
                    framedGlassPaneRecipes()
                }.register()
        }

    fun register() {
        // Loads this class
    }
}

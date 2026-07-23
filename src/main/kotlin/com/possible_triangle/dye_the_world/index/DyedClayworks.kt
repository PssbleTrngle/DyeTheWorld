package com.possible_triangle.dye_the_world.index

import com.google.common.base.Suppliers.memoize
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.CLAYWORKS
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.multikulti.datagen.conditions.ModLoaded
import com.possible_triangle.multikulti.datagen.conditions.withConditions
import com.teamabnormals.clayworks.common.block.GlassDoorBlock
import com.teamabnormals.clayworks.common.block.GlassTrapDoorBlock
import com.teamabnormals.clayworks.common.item.crafting.BakingRecipe
import com.teamabnormals.clayworks.core.registry.ClayworksBlocks
import com.teamabnormals.clayworks.core.registry.ClayworksRecipes.ClayworksRecipeSerializers
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DecoratedPotBlock
import java.util.function.Supplier

object DyedClayworks {
    private val DYES = dyesFor(CLAYWORKS)

    private val TERRACOTTA = dyedBlockMap(CLAYWORKS, "terracotta")
    private val GLAZED_TERRACOTTA = dyedBlockMap(CLAYWORKS, "glazed_terracotta")
    private val CONCRETE_POWDER = dyedBlockMap(CLAYWORKS, "concrete_powder")

    val TERRACOTTA_BRICKS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_terracotta_bricks")
                .dyedBlock(dye, CLAYWORKS)
                .initialProperties { dye.blockOf("terracotta") }
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .cubeBlockstate("terracotta_brick")
                .lang("${dye.translation} Terracotta Bricks")
                .germanLang("${dye.germanTranslation(Genus.F)} Keramikziegel")
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    tab(CreativeModeTabs.BUILDING_BLOCKS)
                    recipe { c, p ->
                        val terracotta = TERRACOTTA[dye]!!
                        p.stonecutting(terracotta.asIngredient(), BUILDING_BLOCKS, c)
                        ShapedRecipeBuilder
                            .shaped(BUILDING_BLOCKS, c.get(), 4)
                            .pattern("XX")
                            .pattern("XX")
                            .defineUnlocking('X', terracotta.get())
                            .save(p)
                        p.shapedDyeingRecipe(dye, ClayworksBlocks.TERRACOTTA_BRICKS.get(), c)
                    }
                }.register()
        }

    val TERRACOTTA_BRICK_SLABS =
        REGISTRATE.createSlabs(
            TERRACOTTA_BRICKS,
            CLAYWORKS.createId("terracotta_brick"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Keramikziegelstufe")
            },
            modifyItem = { dye ->
                recipe { context, provider ->
                    provider.slab(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                    provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context, 2)
                }
            },
        )

    val TERRACOTTA_BRICK_STAIRS =
        REGISTRATE.createStairs(
            TERRACOTTA_BRICKS,
            CLAYWORKS.createId("terracotta_brick"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Keramikziegeltreppe")
            },
            modifyItem = { dye ->
                recipe { context, provider ->
                    provider.stairs(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                    provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
                }
            },
        )

    val TERRACOTTA_BRICK_WALLS =
        REGISTRATE.createWalls(
            TERRACOTTA_BRICKS,
            CLAYWORKS.createId("terracotta_brick"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.F)} Keramikziegelmauer")
            },
            modifyItem = { dye ->
                recipe { context, provider ->
                    provider.wall(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
                    provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
                }
            },
        )

    val CHISELED_TERRACOTTA_BRICKS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("chiseled_${dye}_terracotta_bricks")
                .dyedBlock(dye, CLAYWORKS)
                .initialProperties { dye.blockOf("terracotta") }
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .cubeBlockstate("chiseled_terracotta_bricks")
                .lang("Chiseled ${dye.translation} Terracotta Bricks")
                .germanLang("Gemeißelte ${dye.germanTranslation(Genus.F)} Keramikziegel")
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    tab(CreativeModeTabs.BUILDING_BLOCKS)
                    recipe { c, p ->
                        p.stonecutting(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, c)
                        p.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, c)

                        val slab = TERRACOTTA_BRICK_SLABS[dye]!!.get()
                        ShapedRecipeBuilder
                            .shaped(BUILDING_BLOCKS, c.get())
                            .pattern("#")
                            .pattern("#")
                            .define('#', slab)
                            .unlockedBy("has_slab", RegistrateRecipeProvider.has(slab))
                            .save(p)
                    }
                }.register()
        }

    val TERRACOTTA_SLABS =
        REGISTRATE.createSlabs(TERRACOTTA, CLAYWORKS.createId("terracotta"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Keramikstufe")
        }, existingTexture = true)

    val TERRACOTTA_STAIRS =
        REGISTRATE.createStairs(TERRACOTTA, CLAYWORKS.createId("terracotta"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Keramiktreppe")
        }, existingTexture = true)

    val TERRACOTTA_WALLS =
        REGISTRATE.createWalls(TERRACOTTA, CLAYWORKS.createId("terracotta"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Keramikmauer")
        }, existingTexture = true)

    @JvmField
    val DECORATED_POTS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_decorated_pot")
                .dyedBlock(dye, CLAYWORKS, ::DecoratedPotBlock)
                .initialProperties { Blocks.DECORATED_POT }
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Decorated Pot")
                .potBlockstate()
                .potLoot()
                .withItem {
                    properties { it.stacksTo(1) }
                    potItemModel()
                    clientExtension(
                        NonNullSupplier {
                            Supplier(::createPotClientExtensions)
                        },
                    )
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                }.register()
        }

    private val DYE_BY_DECORATED_POT =
        memoize {
            DECORATED_POTS.mapValues { it.value.get() }.inverse()
        }

    @JvmStatic
    fun dyeOf(block: Block): DyeColor? = DYE_BY_DECORATED_POT.get()[block]

    val STAINED_GLASS_DOORS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_stained_glass_door")
                .dyedBlock(dye, CLAYWORKS) { GlassDoorBlock(dye) }
                .lang("${dye.translation} Stained Glass Door")
                .glassDoorBlockstate()
                .glassDoorLoot()
                .optionalTag(DyedTags.Blocks.BRITTLE)
                .optionalTag(BlockTags.DOORS)
                .withItem {
                    glassDoorRecipes()
                    glassDoorItemModel()
                    optionalTag(ItemTags.DOORS)
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    tab(CreativeModeTabs.REDSTONE_BLOCKS)
                }.register()
        }

    val STAINED_GLASS_TRAPDOORS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_stained_glass_trapdoor")
                .dyedBlock(dye, CLAYWORKS) { GlassTrapDoorBlock(dye) }
                .lang("${dye.translation} Stained Glass Trapdoor")
                .glassTrapdoorBlockstate()
                .glassTrapdoorLoot()
                .optionalTag(BlockTags.TRAPDOORS)
                .withItem {
                    glassTrapdoorRecipes()
                    glassTrapdoorItemModel()
                    optionalTag(ItemTags.TRAPDOORS)
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    tab(CreativeModeTabs.REDSTONE_BLOCKS)
                }.register()
        }

    fun register() {
        REGISTRATE.addDataGenerator(ProviderType.RECIPE) { provider ->
            provider.withConditions(ModLoaded(CLAYWORKS)) {
                TERRACOTTA.forEach { (dye, terracotta) ->
                    val glazed = GLAZED_TERRACOTTA.getValue(dye)
                    provider.cooking(
                        terracotta.asIngredient(),
                        BUILDING_BLOCKS,
                        glazed,
                        0.1F,
                        100,
                        "baking",
                        ClayworksRecipeSerializers.BAKING_RECIPE.get(),
                        ::BakingRecipe,
                    )
                }
            }

            provider.withConditions(ModLoaded(CLAYWORKS)) {
                CONCRETE_POWDER.forEach { (dye, powder) ->
                    ShapedRecipeBuilder
                        .shaped(BUILDING_BLOCKS, powder.get(), 8)
                        .pattern("###")
                        .pattern("#X#")
                        .pattern("###")
                        .define('X', dye.tag)
                        .defineUnlocking('#', ClayworksBlocks.CONCRETE_POWDER.get())
                        .group("concrete_powder")
                        .save(provider, CLAYWORKS.createId(provider.safeName(powder.get())))
                }
            }
        }
    }
}

package com.possible_triangle.dye_the_world.index

import com.google.common.base.Suppliers.memoize
import com.google.gson.JsonObject
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.CLAYWORKS
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.multikulti.datagen.conditions.Condition
import com.possible_triangle.multikulti.datagen.conditions.ModLoaded
import com.possible_triangle.multikulti.datagen.conditions.withConditions
import com.teamabnormals.clayworks.common.item.crafting.BakingRecipe
import com.teamabnormals.clayworks.core.registry.ClayworksBlocks
import com.teamabnormals.clayworks.core.registry.ClayworksRecipes.ClayworksRecipeSerializers
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.CookingBookCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DecoratedPotBlock
import java.util.function.Supplier

data class ClayworksConfigCondition(val flag: String) : Condition {
    override fun JsonObject.toFabric() {
        error("no fabric support yet")
    }

    override fun JsonObject.toForge() {
        addProperty("type", "$CLAYWORKS:config")
        addProperty("value", flag)
    }
}

object DyedClayworks {

    private val DYES = dyesFor(CLAYWORKS)

    private val TERRACOTTA = dyedBlockMap(CLAYWORKS, "terracotta")
    private val GLAZED_TERRACOTTA = dyedBlockMap(CLAYWORKS, "glazed_terracotta")

    val TERRACOTTA_BRICKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_terracotta_bricks")
            .dyedBlock(dye, CLAYWORKS, ::Block)
            .initialProperties { dye.blockOf("terracotta") }
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(), p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/$CLAYWORKS/${c.name}"))
                )
            }
            .lang("${dye.translation} Terracotta Bricks")
            .germanLang("${dye.germanTranslation(Genus.F)} Keramikziegel")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                recipe { c, p ->
                    val terracotta = TERRACOTTA[dye]!!
                    p.stonecutting(terracotta.asIngredient(), BUILDING_BLOCKS, c)
                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, c.get(), 4)
                        .pattern("XX")
                        .pattern("XX")
                        .defineUnlocking('X', terracotta.get())
                        .save(p)
                    p.shapedDyeingRecipe(dye, ClayworksBlocks.TERRACOTTA_BRICKS.get(), c)
                }
            }
            .register()
    }

    val TERRACOTTA_BRICK_SLABS = REGISTRATE.createSlabs(
        TERRACOTTA_BRICKS,
        CLAYWORKS.createId("terracotta_brick"),
        modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Keramikziegelstufe")
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                val double = Constants.MOD_ID.createId("block/${dye}_terracotta_bricks")
                p.slabBlock(c.get(), double, texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.slab(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context, 2)
            }
        },
    )

    val TERRACOTTA_BRICK_STAIRS = REGISTRATE.createStairs(
        TERRACOTTA_BRICKS,
        CLAYWORKS.createId("terracotta_brick"),
        modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Keramikziegeltreppe")
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                p.stairsBlock(c.get(), texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.stairs(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
            }
        },
    )

    val TERRACOTTA_BRICK_WALLS = REGISTRATE.createWalls(
        TERRACOTTA_BRICKS,
        CLAYWORKS.createId("terracotta_brick"),
        modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Keramikziegelmauer")
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                p.wallBlock(c.get(), texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.wall(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
            }
            model { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                p.wallInventory(c.name, texture)
            }
        },
    )

    val CHISELED_TERRACOTTA_BRICKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("chiseled_${dye}_terracotta_bricks")
            .dyedBlock(dye, CLAYWORKS, ::Block)
            .initialProperties { dye.blockOf("terracotta") }
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(), p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/$CLAYWORKS/${c.name}"))
                )
            }
            .lang("Chiseled ${dye.translation} Terracotta Bricks")
            .germanLang("Gemeißelte ${dye.germanTranslation(Genus.F)} Keramikziegel")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                recipe { c, p ->
                    p.stonecutting(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, c)
                    p.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, c)

                    val slab = TERRACOTTA_BRICK_SLABS[dye]!!.get()
                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, c.get())
                        .pattern("#")
                        .pattern("#")
                        .define('#', slab)
                        .unlockedBy("has_slab", RegistrateRecipeProvider.has(slab))
                        .save(p)
                }
            }
            .register()
    }

    val TERRACOTTA_SLABS = REGISTRATE.createSlabs(TERRACOTTA, CLAYWORKS.createId("terracotta"), modifyBlock = { dye ->
        germanLang("${dye.germanTranslation(Genus.F)} Keramikstufe")
    })

    val TERRACOTTA_STAIRS = REGISTRATE.createStairs(TERRACOTTA, CLAYWORKS.createId("terracotta"), modifyBlock = { dye ->
        germanLang("${dye.germanTranslation(Genus.F)} Keramiktreppe")
    })

    val TERRACOTTA_WALLS = REGISTRATE.createWalls(TERRACOTTA, CLAYWORKS.createId("terracotta"), modifyBlock = { dye ->
        germanLang("${dye.germanTranslation(Genus.F)} Keramikmauer")
    })

    @JvmField
    val DECORATED_POTS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_decorated_pot")
            .dyedBlock(dye, CLAYWORKS, ::DecoratedPotBlock)
            .initialProperties { Blocks.DECORATED_POT }
            .properties { it.mapColor(dye) }
            .lang("${dye.translation} Decorated Pot")
            .potBlockstate()
            .potLoot()
            .withItem {
                properties { it.stacksTo(1) }
                potItemModel()
                clientExtension(NonNullSupplier {
                    Supplier(::createPotClientExtensions)
                })
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            }
            .register()
    }

    private val DYE_BY_DECORATED_POT = memoize {
        DECORATED_POTS.mapValues { it.value.get() }.inverse()
    }

    @JvmStatic
    fun dyeOf(block: Block): DyeColor? = DYE_BY_DECORATED_POT.get()[block]

    fun register() {
        REGISTRATE.addDataGenerator(ProviderType.RECIPE) { provider ->
            provider.withConditions(ModLoaded(CLAYWORKS), ClayworksConfigCondition("kiln")) {
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
                        ::BakingRecipe
                    )
                }
            }
        }
    }

}
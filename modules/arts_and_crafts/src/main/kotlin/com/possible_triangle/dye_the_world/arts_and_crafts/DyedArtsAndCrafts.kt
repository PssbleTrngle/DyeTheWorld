package com.possible_triangle.dye_the_world.arts_and_crafts

import com.kekecreations.arts_and_crafts.common.block.ChalkDustBlock
import com.kekecreations.arts_and_crafts.common.block.DyedFlowerPotBlock
import com.kekecreations.arts_and_crafts.common.block.PlasterBlock
import com.kekecreations.arts_and_crafts.core.init.ACTags
import com.kekecreations.arts_and_crafts.core.registry.ACBlocks
import com.kekecreations.arts_and_crafts.core.registry.ACEntityTypes
import com.possible_triangle.dye_the_world.arts_and_crafts.data.*
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.clayworks.data.potBlockstate
import com.possible_triangle.dye_the_world.clayworks.data.potItemModel
import com.possible_triangle.dye_the_world.clayworks.data.potLoot
import com.possible_triangle.dye_the_world.existingBlockEntity
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import net.minecraft.data.loot.BlockLootSubProvider.noDrop
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder.shaped
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DecoratedPotBlock

object DyedArtsAndCrafts {
    val SOAPSTONE =
        REGISTRATE.stoneSet(
            "soapstone",
            modifyCubes = {
                initialProperties { dye.blockOf("terracotta") }
                recipe { c, p ->
                    p.shapedDyeingRecipe(dye, ACBlocks.SOAPSTONE.get(), c)
                }
            },
            modifySlab = shapedDyeing(ACBlocks.SOAPSTONE_SLAB),
            modifyStairs = shapedDyeing(ACBlocks.SOAPSTONE_STAIRS),
            modifyWall = shapedDyeing(ACBlocks.SOAPSTONE_WALL),
        )

    val POLISHED_SOAPSTONE =
        REGISTRATE.stoneSet(
            "polished_soapstone",
            modifyCubes = {
                initialProperties { SOAPSTONE.cubes[dye]!!.value() }
                recipe { c, p ->
                    p.quadTransform(SOAPSTONE.cubes[dye]!!.value(), c)
                    p.shapedDyeingRecipe(dye, ACBlocks.POLISHED_SOAPSTONE.get(), c)
                }
            },
            modifySlab = shapedDyeing(ACBlocks.POLISHED_SOAPSTONE_SLAB),
            modifyStairs = shapedDyeing(ACBlocks.POLISHED_SOAPSTONE_STAIRS),
            modifyWall = shapedDyeing(ACBlocks.POLISHED_SOAPSTONE_WALL),
        )

    val SOAPSTONE_BRICKS =
        REGISTRATE.stoneSet(
            "soapstone_bricks",
            modifyCubes = {
                initialProperties { SOAPSTONE.cubes[dye]!!.value() }
                recipe { c, p ->
                    p.quadTransform(POLISHED_SOAPSTONE.cubes[dye]!!.value(), c)
                    p.shapedDyeingRecipe(dye, ACBlocks.SOAPSTONE_BRICKS.get(), c)
                }
            },
            modifySlab = shapedDyeing(ACBlocks.SOAPSTONE_BRICK_SLAB),
            modifyStairs = shapedDyeing(ACBlocks.SOAPSTONE_BRICK_STAIRS),
            modifyWall = shapedDyeing(ACBlocks.SOAPSTONE_BRICK_WALL),
        )

    val MUD_BRICKS =
        REGISTRATE.stoneSet(
            "mud_bricks",
            modifyCubes = {
                initialProperties { Blocks.MUD }
                properties { it.mapColor(dye) }
                recipe { c, p ->
                    p.shapedDyeingRecipe(dye, Blocks.MUD_BRICKS, c)
                }
            },
            modifySlab = shapedDyeing { Blocks.MUD_BRICK_SLAB },
            modifyStairs = shapedDyeing { Blocks.MUD_BRICK_STAIRS },
            modifyWall = shapedDyeing { Blocks.MUD_BRICK_WALL },
        )

    val CHALK =
        REGISTRATE.stoneSet(
            "chalk",
            modifyCubes = {
                initialProperties { ACBlocks.CHALK[0]!!.get() }
                properties { it.mapColor(dye) }
                recipe { c, p ->
                    p.dyeingRecipe(dye, ACBlocks.CHALK[0]!!.get(), c)
                }
            },
            modifySlab = shapelessDyeing(ACBlocks.CHALK_SLAB[0]!!),
            modifyStairs = shapelessDyeing(ACBlocks.CHALK_STAIRS[0]!!),
            modifyWall = shapelessDyeing(ACBlocks.CHALK_WALL[0]!!),
        )

    val TERRACOTTA_SHINGLES =
        REGISTRATE.stoneSet(
            "terracotta_shingles",
            modifyCubes = {
                initialProperties { ACBlocks.TERRACOTTA_SHINGLES.get() }
                properties { it.mapColor(dye) }
                recipe { c, p ->
                    p.quadTransform(ACBlocks.TERRACOTTA_SHINGLES.get(), c)
                    p.shapedDyeingRecipe(dye, ACBlocks.TERRACOTTA_SHINGLES.get(), c)
                }
                withItem()
            },
            modifySlab = shapedDyeing(ACBlocks.TERRACOTTA_SHINGLE_SLAB),
            modifyStairs = shapedDyeing(ACBlocks.TERRACOTTA_SHINGLE_STAIRS),
            modifyWall = shapedDyeing(ACBlocks.TERRACOTTA_SHINGLE_WALL),
        )

    val BRUSHES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_paintbrush")
                .item()
                .tag(ACTags.ItemTags.PAINTBRUSHES)
                .recipe { context, provider ->
                    shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
                        .pattern("DD")
                        .pattern("DB")
                        .defineUnlocking('D', dye.tag)
                        .defineUnlocking('B', Items.BRUSH)
                        .save(provider)
                }.register()
        }

    val CHALK_STICK =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_chalk_stick")
                .item()
                .tag(ACTags.ItemTags.CHALK_STICKS)
                .recipe { context, provider ->
                    provider.dyeingRecipe(dye, ACTags.ItemTags.CHALK_STICKS, context)
                    provider.stonecutting(CHALK.cubes[dye]!!.asIngredient(), RecipeCategory.MISC, context)
                }.register()
        }

    val CHALK_DUST =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_chalk_dust")
                .block { ChalkDustBlock(dye.id, it) }
                .tag(ACTags.BlockTags.CHALK_DUST)
                .chalkDustBlockstate()
                .loot { tables, block ->
                    tables.add(block, noDrop())
                }.register()
        }

    val CHALK_DRAW_PARTICLES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_chalk_draw")
                .particle()
                .sprites(ARTS_AND_CRAFTS.createId("chalk_draw/$dye/frame"), 5)
                .register()
        }

    val PLASTERS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_plaster")
                .block { PlasterBlock(dye, it) }
                .tagPaintable("plaster")
                .plasterBlockstate()
                .withItem {
                    noModel()
                    recipe { context, provider ->
                        provider.shapedDyeingRecipe(dye, ACBlocks.PLASTER.get(), context)
                    }
                }.register()
        }

    val DECORATED_POTS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_decorated_pot")
                .block(::DecoratedPotBlock)
                .potBlockstate()
                .potLoot()
                .existingBlockEntity { ACEntityTypes.CUSTOM_DECORATED_POT_BLOCK_ENTITY.get() }
                .withItem {
                    potItemModel()
                }.register()
        }

    val FLOWER_POTS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_flower_pot")
                .block(::DyedFlowerPotBlock)
                .flowerPotBlockstate()
                .existingBlockEntity { ACEntityTypes.CUSTOM_FLOWER_POT_BLOCK_ENTITY.get() }
                .withItem {
                    potItemModel()
                }.register()
        }
}

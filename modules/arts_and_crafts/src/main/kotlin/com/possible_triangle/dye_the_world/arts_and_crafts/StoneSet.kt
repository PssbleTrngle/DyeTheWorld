package com.possible_triangle.dye_the_world.arts_and_crafts

import com.possible_triangle.dye_the_world.DyedMap
import com.possible_triangle.dye_the_world.arts_and_crafts.data.paintPalette
import com.possible_triangle.dye_the_world.arts_and_crafts.data.tagPaintable
import com.possible_triangle.dye_the_world.data.BlockModifier
import com.possible_triangle.dye_the_world.data.ShapeParameters
import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.data.createWalls
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock

data class StoneSet(
    val cubes: DyedMap<Block>,
    val slabs: DyedMap<Block>,
    val stairs: DyedMap<Block>,
    val walls: DyedMap<Block>,
)

fun String.unPluralize(): String =
    if (
        endsWith("_shingles") ||
        endsWith("_bricks")
    ) {
        dropLast(1)
    } else {
        this
    }

internal fun DyedRegistrate.stoneSet(
    name: String,
    modifyCubes: BlockModifier<Block> = {},
    modifySlab: BlockModifier<SlabBlock> = {},
    modifyStairs: BlockModifier<StairBlock> = {},
    modifyWall: BlockModifier<WallBlock> = {},
    stone: Boolean = true,
): StoneSet {
    val cubes =
        dyedBlocks { dye ->
            `object`("${dye}_$name")
                .dyedBlock(dye)
                .apply(modifyCubes)
                .also { if (stone) it.tag(BlockTags.MINEABLE_WITH_PICKAXE) }
                .tagPaintable(name)
                .withItem()
        }

    val params =
        ShapeParameters(
            modid.createId(name.unPluralize()),
            cubes,
            stone,
        )

    return StoneSet(
        params.cubes,
        slabs = createSlabs(params, modifyBlock = modifySlab),
        stairs = createStairs(params, modifyBlock = modifyStairs),
        walls = createWalls(params, modifyBlock = modifyWall),
    ).also {
        paintPalette(name, it.cubes)
        paintPalette("${name}_slab", it.slabs)
        paintPalette("${name}_stairs", it.stairs)
        paintPalette("${name}_wall", it.walls)
    }
}

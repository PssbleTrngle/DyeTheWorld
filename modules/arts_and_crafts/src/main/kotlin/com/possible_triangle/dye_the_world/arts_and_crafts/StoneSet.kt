package com.possible_triangle.dye_the_world.arts_and_crafts

import com.possible_triangle.dye_the_world.data.BlockModifier
import com.possible_triangle.dye_the_world.data.ShapeParameters
import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.data.createWalls
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock

data class StoneSet(
    val cubes: Map<DyeColor, NonNullSupplier<out Block>>,
    val slabs: Map<DyeColor, NonNullSupplier<out SlabBlock>>,
    val stairs: Map<DyeColor, NonNullSupplier<out StairBlock>>,
    val walls: Map<DyeColor, NonNullSupplier<out WallBlock>>,
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

@Deprecated("use other stoneSet method instead")
internal fun DyedRegistrate.stoneSet(
    params: ShapeParameters,
    modifySlab: BlockModifier<SlabBlock> = {},
    modifyStairs: BlockModifier<StairBlock> = {},
    modifyWall: BlockModifier<WallBlock> = {},
) = StoneSet(
    params.cubes,
    slabs = createSlabs(params, modifyBlock = modifySlab),
    stairs = createStairs(params, modifyBlock = modifyStairs),
    walls = createWalls(params, modifyBlock = modifyWall),
)

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
    )
}

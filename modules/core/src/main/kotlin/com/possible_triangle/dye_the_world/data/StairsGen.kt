package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.DyedMap
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.StairBlock

fun DyedRegistrate.createStairs(
    params: ShapeParameters,
    modifyBlock: BlockModifier<StairBlock> = {},
    modifyItem: BlockItemModifier<StairBlock> = {},
) = createStairs(
    params.cubes,
    params.name,
    modifyBlock,
    modifyItem,
    params.stone,
)

@Deprecated("use ShapeParameters instead")
fun DyedRegistrate.createStairs(
    from: DyedMap<Block>,
    name: ResourceLocation,
    modifyBlock: BlockModifier<StairBlock> = {},
    modifyItem: BlockItemModifier<StairBlock> = {},
    stone: Boolean = true,
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_stairs")
        .dyedBlock(dye) { StairBlock(base.value().defaultBlockState(), it) }
        .initialProperties(base::value)
        .also { if (stone) it.tag(BlockTags.MINEABLE_WITH_PICKAXE) }
        .tag(BlockTags.STAIRS)
        .blockstate { c, p ->
            val texture = from[dye]!!.value().texture
            p.stairsBlock(c.get(), texture)
        }.apply(modifyBlock)
        .withItem {
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.STAIRS)
            recipe { c, p -> p.stairs(base.asIngredient(), RecipeCategory.BUILDING_BLOCKS, c, null, stone) }
            modifyItem()
        }.register()
}

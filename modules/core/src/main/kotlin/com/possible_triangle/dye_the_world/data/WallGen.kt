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
import net.minecraft.world.level.block.WallBlock

fun DyedRegistrate.createWalls(
    params: ShapeParameters,
    modifyBlock: BlockModifier<WallBlock> = {},
    modifyItem: BlockItemModifier<WallBlock> = {},
) = createWalls(
    params.cubes,
    params.name,
    modifyBlock,
    modifyItem,
)

@Deprecated("use ShapeParameters instead")
fun DyedRegistrate.createWalls(
    from: DyedMap<Block>,
    name: ResourceLocation,
    modifyBlock: BlockModifier<WallBlock> = {},
    modifyItem: BlockItemModifier<WallBlock> = {},
) = from.mapDyed { dye, base ->
    `object`("${dye}_${name.path}_wall")
        .block(::WallBlock)
        .initialProperties(base)
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .tag(BlockTags.WALLS)
        .blockstate { c, p ->
            val texture = from[dye]!!.value().texture
            p.wallBlock(c.get(), texture)
            p.itemModels().wallInventory(c.name, texture)
        }.apply(modifyBlock)
        .withItem {
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.WALLS)
            recipe { c, p -> p.wall(base.asIngredient(), RecipeCategory.BUILDING_BLOCKS, c) }
            noModel()
            modifyItem()
        }.register()
}

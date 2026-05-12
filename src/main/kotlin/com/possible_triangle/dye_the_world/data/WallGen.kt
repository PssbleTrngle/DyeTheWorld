package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.WallBlock

fun DyedRegistrate.createWalls(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<WallBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<WallBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_wall")
        .dyedBlock(dye, name.namespace, ::WallBlock)
        .initialProperties(base)
        .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        .optionalTag(BlockTags.WALLS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name.path}")
            p.wallBlock(c.get(), texture)
        }.withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.WALLS)
            recipe { c, p -> p.wall(base.asIngredient(), RecipeCategory.BUILDING_BLOCKS, c) }
            model { c, p ->
                val texture = dye.namespace.createId("block/${dye}_${name.path}")
                p.wallInventory(c.name, texture)
            }
            modifyItem(dye)
        }.apply { modifyBlock(dye) }
        .register()
}

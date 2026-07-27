package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.extensions.*
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
import net.minecraft.world.level.block.StairBlock

fun DyedRegistrate.createStairs(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<StairBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<StairBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
    existingTexture: Boolean = false,
    stone: Boolean = true,
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_stairs")
        .dyedBlock(dye, name.namespace) { StairBlock(base.get().defaultBlockState(), it) }
        .initialProperties(base)
        .also { if (stone) it.optionalTag(BlockTags.MINEABLE_WITH_PICKAXE) }
        .optionalTag(BlockTags.STAIRS)
        .blockstate { c, p ->
            val texture =
                if (existingTexture) {
                    dye.vanillaTexture(name)
                } else {
                    dye.texture("block", name)
                }
            p.stairsBlock(c.get(), texture)
        }.withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.STAIRS)
            recipe { c, p -> p.stairs(base.asIngredient(), RecipeCategory.BUILDING_BLOCKS, c, null, stone) }
            modifyItem(dye)
        }.apply { modifyBlock(dye) }
        .register()
}

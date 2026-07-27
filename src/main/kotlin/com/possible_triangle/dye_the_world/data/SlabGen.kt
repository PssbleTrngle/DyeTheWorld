package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import kotlin.collections.component2

fun DyedRegistrate.createSlabs(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<SlabBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<SlabBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
    existingTexture: Boolean = false,
    stone: Boolean = true,
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_slab")
        .dyedBlock(dye, name.namespace, ::SlabBlock)
        .initialProperties(base)
        .also { if (stone) it.optionalTag(BlockTags.MINEABLE_WITH_PICKAXE) }
        .optionalTag(BlockTags.SLABS)
        .blockstate { c, p ->
            val texture =
                if (existingTexture) {
                    dye.vanillaTexture(name)
                } else {
                    dye.texture("block", name)
                }
            val doubleModel = BuiltInRegistries.BLOCK.getKey(base.get()).withPrefix("block/")
            p.slabBlock(c.get(), doubleModel, texture)
        }.loot { c, p ->
            c.add(p, c.createSlabItemTable(p))
        }.withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.SLABS)
            recipe { c, p -> p.slab(base.asIngredient(), BUILDING_BLOCKS, c, null, stone) }
            modifyItem(dye)
        }.apply { modifyBlock(dye) }
        .register()
}

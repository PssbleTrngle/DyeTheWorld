package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.DyedMap
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import kotlin.collections.component2

fun DyedRegistrate.createSlabs(
    params: ShapeParameters,
    modifyBlock: BlockModifier<SlabBlock> = {},
    modifyItem: BlockItemModifier<SlabBlock> = {},
) = createSlabs(
    params.cubes,
    params.name,
    modifyBlock,
    modifyItem,
    params.stone,
)

@Deprecated("use ShapeParameters instead")
fun DyedRegistrate.createSlabs(
    from: DyedMap<Block>,
    name: ResourceLocation,
    modifyBlock: BlockModifier<SlabBlock> = {},
    modifyItem: BlockItemModifier<SlabBlock> = {},
    stone: Boolean = true,
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_slab")
        .dyedBlock(dye, ::SlabBlock)
        .initialProperties(base::value)
        .also { if (stone) it.tag(BlockTags.MINEABLE_WITH_PICKAXE) }
        .tag(BlockTags.SLABS)
        .blockstate { c, p ->
            val texture = from[dye]!!.value().texture
            val doubleModel = BuiltInRegistries.BLOCK.getKey(base.value()).withPrefix("block/")
            p.slabBlock(c.get(), doubleModel, texture)
        }.loot { c, p ->
            c.add(p, c.createSlabItemTable(p))
        }.apply(modifyBlock)
        .withItem {
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.SLABS)
            recipe { c, p -> p.slab(base.asIngredient(), BUILDING_BLOCKS, c, null, stone) }
            modifyItem()
        }.register()
}

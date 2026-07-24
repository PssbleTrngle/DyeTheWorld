package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.extensions.asIngredient
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.FenceBlock
import net.minecraft.world.level.block.FenceGateBlock

fun DyedRegistrate.createFences(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<FenceBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<FenceBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
    existingTexture: Boolean = false,
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_fence")
        .dyedBlock(dye, name.namespace, ::FenceBlock)
        .initialProperties(base)
        .optionalTag(BlockTags.FENCES)
        .blockstate { c, p ->
            val texture =
                if (existingTexture) {
                    dye.vanillaTexture(name)
                } else {
                    dye.texture("block", name)
                }
            p.fenceBlock(c.get(), texture)
        }.withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.FENCES)
            recipe { c, p ->
                p.fence(
                    base.asIngredient(),
                    RecipeCategory.BUILDING_BLOCKS,
                    c,
                    "concrete_fence",
                )
            }
            model { c, p ->
                val texture = dye.namespace.createId("block/${dye}_${name.path}")
                p.fenceInventory(c.name, texture)
            }
            modifyItem(dye)
        }.apply { modifyBlock(dye) }
        .register()
}

fun DyedRegistrate.createFenceGates(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<FenceGateBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<FenceGateBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
    existingTexture: Boolean = false,
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_fence_gate")
        .dyedBlock(dye, name.namespace) { FenceGateBlock(it, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE) }
        .initialProperties(base)
        .optionalTag(BlockTags.FENCE_GATES)
        .blockstate { c, p ->
            val texture =
                if (existingTexture) {
                    dye.vanillaTexture(name)
                } else {
                    dye.texture("block", name)
                }
            p.fenceGateBlock(c.get(), texture)
        }.withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.FENCE_GATES)
            recipe { c, p ->
                p.fenceGate(
                    base.asIngredient(),
                    RecipeCategory.BUILDING_BLOCKS,
                    c,
                    "concrete_fence_gate",
                )
            }
            model { c, p ->
                val texture = dye.namespace.createId("block/${dye}_${name.path}")
                p.fenceGate(c.name, texture)
            }
            modifyItem(dye)
        }.apply { modifyBlock(dye) }
        .register()
}

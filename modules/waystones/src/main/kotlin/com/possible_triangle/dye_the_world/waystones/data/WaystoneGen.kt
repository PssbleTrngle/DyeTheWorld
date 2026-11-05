package com.possible_triangle.dye_the_world.waystones.data

import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.hasEnchantment
import com.possible_triangle.dye_the_world.extensions.matchesState
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.waystones.WAYSTONES
import com.tterrag.registrate.builders.BlockBuilder
import net.blay09.mods.waystones.block.SharestoneBlock
import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.waystoneBlockstate(type: String) =
    blockstate { context, provider ->
        val upper = provider.models().getExistingFile(WAYSTONES.createId("block/${type}_top"))
        val bottom = provider.models().getExistingFile(WAYSTONES.createId("block/${type}_bottom"))

        provider.createVariant(context) { state ->
            val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)
            val half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF)

            val model = if (half == DoubleBlockHalf.UPPER) upper else bottom

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.yRot)
        }
    }

fun <T : Block, P> BlockBuilder<T, P>.waystoneLoot() =
    loot { tables, block ->
        val enchantments = tables.registries.lookupOrThrow(Registries.ENCHANTMENT)

        val hasSilktouch =
            MatchTool.toolMatches(
                ItemPredicate.Builder.item().hasEnchantment(
                    EnchantmentPredicate(
                        enchantments.getOrThrow(Enchantments.SILK_TOUCH),
                        MinMaxBounds.Ints.atLeast(1),
                    ),
                ),
            )

        val pool =
            LootPool
                .lootPool()
                .add(LootItem.lootTableItem(block))
                .`when`(
                    matchesState(block) {
                        hasProperty(SharestoneBlock.HALF, DoubleBlockHalf.LOWER)
                    },
                ).apply(
                    CopyComponentsFunction
                        .copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                        .`when`(hasSilktouch),
                )

        tables.add(
            block,
            LootTable.lootTable().withPool(
                tables.applyExplosionDecay(block, pool),
            ),
        )
    }

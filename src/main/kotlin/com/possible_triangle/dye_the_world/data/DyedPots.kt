package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.dye
import com.teamabnormals.blueprint.common.item.BEWLRBlockItem
import com.teamabnormals.clayworks.client.DecoratedPotBlockEntityWithoutLevelRenderer
import com.teamabnormals.clayworks.core.data.server.ClayworksLootTableProvider
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity
import net.minecraft.world.level.storage.loot.LootTable
import java.util.concurrent.Callable

fun <T : Block, P> BlockBuilder<T, P>.potLoot() = loot { tables, block ->
    val table = LootTable.lootTable()
        .withPool(ClayworksLootTableProvider.ClayworksBlockLoot.createDynamicTrimDropPool(block))
        .withPool(ClayworksLootTableProvider.ClayworksBlockLoot.createDecoratedPotPool(block))
    tables.add(block, table)
}

fun createPotItem(block: Block, properties: Item.Properties) = BEWLRBlockItem(block, properties) {
    Callable {
        BEWLRBlockItem.LazyBEWLR { dispatcher, models ->
            DecoratedPotBlockEntityWithoutLevelRenderer(
                dispatcher, models, DecoratedPotBlockEntity(
                    BlockPos.ZERO, block.defaultBlockState()
                )
            )
        }
    }
}

fun <T : Block, P> BlockBuilder<T, P>.potBlockstate() = blockstate { context, provider ->
    val model = provider.models().getBuilder(context.name)
        .texture("particle", ResourceLocation.fromNamespaceAndPath(dye.namespace, "block/${dye}_terracotta"))
    provider.simpleBlock(context.get(), model)
}

fun <T : Item, P> ItemBuilder<T, P>.potItemModel() = model { context, provider ->
    provider.withExistingParent(context.name, ResourceLocation.withDefaultNamespace("item/decorated_pot"))
}
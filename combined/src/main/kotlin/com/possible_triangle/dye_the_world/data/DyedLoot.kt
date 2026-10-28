package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.hasEnchantment
import com.possible_triangle.dye_the_world.index.DyedQuark.GLASS_SHARDS
import com.possible_triangle.dye_the_world.index.QuarkConfigCondition
import com.possible_triangle.multikulti.datagen.conditions.withConditions
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.storage.loot.IntRange
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay
import net.minecraft.world.level.storage.loot.functions.LimitCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

fun AbstractRegistrate<*>.generateGlassShardLoot() {
    addDataGenerator(ProviderType.LOOT) { tables ->
        tables.addLootAction(LootContextParamSets.BLOCK) { consumer ->
            tables.withConditions(QuarkConfigCondition("glass_shard")) {
                GLASS_SHARDS.forEach { (dye, shard) ->
                    val enchantments = tables.provider.lookupOrThrow(Registries.ENCHANTMENT)
                    val stainedGlass = dye.blockOf("stained_glass")

                    val entry =
                        AlternativesEntry.alternatives(
                            LootItem.lootTableItem(stainedGlass).`when`(
                                MatchTool.toolMatches(
                                    ItemPredicate.Builder
                                        .item()
                                        .hasEnchantment(
                                            EnchantmentPredicate(
                                                enchantments.getOrThrow(Enchantments.SILK_TOUCH),
                                                MinMaxBounds.Ints.atLeast(1),
                                            ),
                                        ),
                                ),
                            ),
                            LootItem
                                .lootTableItem(shard.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F)))
                                .apply(
                                    ApplyBonusCount.addUniformBonusCount(
                                        enchantments.getOrThrow(Enchantments.FORTUNE),
                                        1,
                                    ),
                                ).apply(LimitCount.limitCount(IntRange.range(1, 4)))
                                .apply(ApplyExplosionDecay.explosionDecay()),
                        )

                    val table = LootTable.lootTable().withPool(LootPool.lootPool().add(entry))

                    consumer.accept(stainedGlass.lootTable, table)
                }
            }
        }
    }
}

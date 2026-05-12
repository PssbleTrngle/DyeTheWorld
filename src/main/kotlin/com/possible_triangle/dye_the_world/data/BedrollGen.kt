package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.UPGRADE_AQUATIC
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.matchesState
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.teamabnormals.upgrade_aquatic.common.block.BedrollBlock
import com.teamabnormals.upgrade_aquatic.core.other.tags.UAItemTags
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.bedrollBlockstate() =
    blockstate { context, provider ->
        val texture = Constants.MOD_ID.createId("block/$UPGRADE_AQUATIC/bedroll/$dye")
        val particle = texture.withSuffix("_particle")

        val foot =
            provider
                .models()
                .withExistingParent(context.name + "_foot", UPGRADE_AQUATIC.createId("block/bedroll/template_bedroll_foot"))
                .texture("bedroll", texture)
                .texture("particle", particle)

        val head =
            provider
                .models()
                .withExistingParent(context.name + "_head", UPGRADE_AQUATIC.createId("block/bedroll/template_bedroll_head"))
                .texture("bedroll", texture)
                .texture("particle", particle)

        provider.createVariant(context, BedrollBlock.OCCUPIED) { state ->
            val facing = state.getValue(BedrollBlock.FACING)
            val part = state.getValue(BedrollBlock.PART)

            val model = if (part == BedPart.FOOT) foot else head

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.opposite.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.bedrollRecipe() =
    recipe { context, provider ->
        provider.dyeingRecipe(dye, UAItemTags.BEDROLLS, context) {
            group("bedroll")
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.bedrollItemModel() =
    model { context, provider ->
        provider.generated(context, Constants.MOD_ID.createId("item/$UPGRADE_AQUATIC/bedroll/$dye"))
    }

fun <T : Block, P> BlockBuilder<T, P>.bedrollLoot() =
    loot { tables, block ->
        tables.add(
            block,
            LootTable.lootTable().withPool(
                tables.applyExplosionDecay(
                    block,
                    LootPool
                        .lootPool()
                        .add(LootItem.lootTableItem(block))
                        .`when`(
                            matchesState(block) {
                                hasProperty(BedrollBlock.PART, BedPart.HEAD)
                            },
                        ),
                ),
            ),
        )
    }

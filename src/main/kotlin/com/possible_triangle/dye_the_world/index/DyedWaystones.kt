package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.WAYSTONES
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.ProviderType
import net.blay09.mods.waystones.block.PortstoneBlock
import net.blay09.mods.waystones.block.SharestoneBlock
import net.blay09.mods.waystones.item.ModItems
import net.blay09.mods.waystones.tag.ModBlockTags
import net.blay09.mods.waystones.tag.ModItemTags
import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

private fun <T : Block, P> BlockBuilder<T, P>.waystoneBlockstate(type: String) = blockstate { context, provider ->
    val upper = provider.models().getExistingFile(WAYSTONES.createId("block/${type}_top"))
    val bottom = provider.models().getExistingFile(WAYSTONES.createId("block/${type}_bottom"))

    provider.createVariant(context) { state ->
        val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)
        val half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF)

        val model = if (half == DoubleBlockHalf.UPPER) upper else bottom

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }
}

private fun <T : Block, P> BlockBuilder<T, P>.waystoneLoot() = loot { tables, block ->
    val enchantments = tables.registries.lookupOrThrow(Registries.ENCHANTMENT)

    val hasSilktouch = MatchTool.toolMatches(
        ItemPredicate.Builder.item().hasEnchantment(
            EnchantmentPredicate(
                enchantments.getOrThrow(Enchantments.SILK_TOUCH),
                MinMaxBounds.Ints.atLeast(1)
            )
        )
    )

    val pool = LootPool.lootPool()
        .add(LootItem.lootTableItem(block))
        .`when`(matchesState(block) {
            hasProperty(SharestoneBlock.HALF, DoubleBlockHalf.LOWER)
        })
        .apply(
            CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                .`when`(hasSilktouch)
        )

    tables.add(
        block, LootTable.lootTable().withPool(
            tables.applyExplosionDecay(block, pool)
        )
    )
}

object DyedWaystones {

    private val REGISTRATE = DyedRegistrate.create(WAYSTONES)
    private val DYES = dyesFor(WAYSTONES)

    val PORTSTONES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_portstone")
            .dyedBlock(dye) { PortstoneBlock(dye, it) }
            .lang("${dye.translation} Portstone")
            .germanLang("${dye.germanTranslation(Genus.M)} Portstein")
            .optionalTag(ModBlockTags.PORTSTONES)
            .optionalTag(ModBlockTags.IS_TELEPORT_TARGET)
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { context, provider ->
                val upper = provider.models().getExistingFile(WAYSTONES.createId("block/portstone_top"))
                val bottom = provider.models().getExistingFile(WAYSTONES.createId("block/portstone_bottom"))

                provider.createVariant(context) { state ->
                    val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                    val half = state.getValue(PortstoneBlock.HALF)

                    val model = if (half == DoubleBlockHalf.UPPER) upper else bottom

                    ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationY(facing.yRot)
                }
            }
            .waystoneBlockstate("portstone")
            .waystoneLoot()
            .withItem {
                recipe { c, p ->
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                        .pattern("DSD")
                        .pattern("SWS")
                        .pattern("BBB")
                        .define('D', dye.tag)
                        .define('S', Blocks.STONE_BRICKS)
                        .define('B', Blocks.POLISHED_ANDESITE)
                        .defineUnlocking('W', ModItems.warpStone)
                        .save(p)
                }
                model { context, provider ->
                    provider.withExistingParent(context.name, WAYSTONES.createId("item/portstone"))
                }
            }
            .register()
    }

    val SHARESTONES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_sharestone")
            .dyedBlock(dye) { SharestoneBlock(dye, it) }
            .lang("${dye.translation} Sharestone")
            .germanLang("${dye.germanTranslation(Genus.M)} Teilstein")
            .addMiscData(ProviderType.LANG) {
                it.add(
                    "tooltip.$WAYSTONES.${dye}_sharestone",
                    "Teleport to any other ${dye.translation} Sharestone"
                )
            }
            .addMiscData(DE_LANG) {
                it.add(
                    "tooltip.$WAYSTONES.${dye}_sharestone",
                    "Teleportiere zu jedem anderen ${dye.germanTranslation(Genus.M)} Teilstein"
                )
            }
            .optionalTag(ModBlockTags.SHARESTONES)
            .optionalTag(ModBlockTags.IS_TELEPORT_TARGET)
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .waystoneLoot()
            .waystoneBlockstate("sharestone")
            .withItem {
                optionalTag(ModItemTags.SHARESTONES)
                recipe { c, p ->
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                        .pattern("SSS")
                        .pattern("DWD")
                        .pattern("OOO")
                        .define('D', dye.tag)
                        .define('O', Blocks.OBSIDIAN)
                        .define('S', Blocks.STONE_BRICKS)
                        .defineUnlocking('W', ModItems.warpStone)
                        .save(p)
                }
                model { context, provider ->
                    provider.withExistingParent(context.name, WAYSTONES.createId("item/sharestone"))
                }
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}
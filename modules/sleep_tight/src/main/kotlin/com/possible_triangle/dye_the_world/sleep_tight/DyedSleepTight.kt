package com.possible_triangle.dye_the_world.sleep_tight

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.data.translateBannerPattern
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.extensions.particleOnly
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.providers.ProviderType
import net.mehvahdjukaar.moonlight.api.platform.RegHelper
import net.mehvahdjukaar.sleep_tight.SleepTight
import net.mehvahdjukaar.sleep_tight.common.HammockPart
import net.mehvahdjukaar.sleep_tight.common.blocks.HammockBlock
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent

object DyedSleepTight {
    val HAMMOCKS =
        REGISTRATE.dyed { dye ->
            `object`("hammock_$dye")
                .dyedBlock(dye) { HammockBlock(dye) }
                .lang("${dye.translation} Hammock")
                .germanLang("${dye.germanTranslation(Genus.F)} Hängematte")
                .particleOnly()
                .optionalTag(SleepTightTags.Blocks.HAMMOCKS)
                .loot { provider, block ->
                    val table =
                        LootTable
                            .lootTable()
                            .withPool(
                                provider.applyExplosionCondition(
                                    block,
                                    LootPool
                                        .lootPool()
                                        .add(
                                            AlternativesEntry.alternatives(
                                                LootItem.lootTableItem(block).whenPart(block, HammockPart.MIDDLE),
                                                LootItem.lootTableItem(block).whenPart(block, HammockPart.HEAD),
                                            ),
                                        ),
                                ),
                            )
                    provider.add(block, table)
                }.withItem {
                    tab(CreativeModeTabs.BUILDING_BLOCKS)
                    optionalTag(SleepTightTags.Items.HAMMOCKS)
                    recipe { c, p ->
                        p.dyeingRecipe(
                            dye,
                            SleepTight.HAMMOCKS[DyeColor.WHITE]!!.get(),
                            c,
                            id = Constants.MOD_ID.createId("hammock_${dye}_dyeing"),
                        ) {
                            group("hammock")
                        }

                        ShapedRecipeBuilder
                            .shaped(RecipeCategory.BUILDING_BLOCKS, c.get())
                            .pattern("CCC")
                            .pattern("SSS")
                            .defineUnlocking('S', Items.STRING)
                            .defineUnlocking('C', dye.blockOf("carpet"))
                            .save(p)
                    }
                }.register()
        }

    init {
        REGISTRATE.addDataGenerator(ProviderType.BLOCKSTATE) { provider ->
            REGISTRATE.dyes.forEach { dye ->
                provider
                    .models()
                    .getBuilder("block/${dye}_bed")
                    .texture("particle", dye.namespace.createId("block/${dye}_wool"))
            }
        }

        REGISTRATE.translateBannerPattern(SLEEP_TIGHT.createId("moon"), "Moon")
    }

    @SubscribeEvent
    private fun registerClientResources(event: FMLClientSetupEvent) =
        event.enqueueWork {
            RegHelper.registerDynamicResourceProvider(SleepThighDynamicResources())
        }
}

private fun <T : LootPoolSingletonContainer.Builder<out T>> T.whenPart(
    block: Block,
    part: HammockPart,
): T =
    `when`(
        matchesState(block) {
            hasProperty(HammockBlock.PART, part)
        },
    )

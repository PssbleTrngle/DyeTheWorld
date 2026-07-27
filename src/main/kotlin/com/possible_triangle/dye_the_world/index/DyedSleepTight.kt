package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.SLEEP_TIGHT
import com.possible_triangle.dye_the_world.data.flat
import com.possible_triangle.dye_the_world.data.particleOnly
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
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

object DyedSleepTight {
    private val DYES = dyesFor(SLEEP_TIGHT)

    private val REGISTRATE = DyedRegistrate.create(SLEEP_TIGHT)

    val HAMMOCKS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("hammock_$dye")
                .dyedBlock(dye) { HammockBlock(dye) }
                .lang("${dye.translation} Hammock")
                .germanLang("${dye.germanTranslation(Genus.F)} Hängematte")
                .particleOnly("hammock")
                .optionalTag(DyedTags.Blocks.SLEEP_TIGHT_HAMMOCKS)
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
                    optionalTag(DyedTags.Items.SLEEP_TIGHT_HAMMOCKS)
                    flat("hammock")
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

    fun register() {
        REGISTRATE.register()
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

package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.UPGRADE_AQUATIC
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.VANILLA_DYES
import com.possible_triangle.dye_the_world.data.bedrollBlockstate
import com.possible_triangle.dye_the_world.data.bedrollItemModel
import com.possible_triangle.dye_the_world.data.bedrollLoot
import com.possible_triangle.dye_the_world.data.bedrollRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.cleaningRecipe
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.withNamespace
import com.teamabnormals.upgrade_aquatic.common.block.BedrollBlock
import com.teamabnormals.upgrade_aquatic.core.other.tags.UABlockTags
import com.teamabnormals.upgrade_aquatic.core.other.tags.UAItemTags
import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.CreativeModeTabs

object DyedAquatic {
    val BEDROLLS =
        dyesFor(UPGRADE_AQUATIC).associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_bedroll")
                .dyedBlock(dye, UPGRADE_AQUATIC) { BedrollBlock(dye, it) }
                .initialProperties { UABlocks.BEDROLL.get() }
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Bedroll")
                .bedrollBlockstate()
                .bedrollLoot()
                .optionalTag(UABlockTags.BEDROLLS)
                .withItem {
                    tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    optionalTag(UAItemTags.BEDROLLS)
                    bedrollRecipe()
                    bedrollItemModel()
                }.register()
        }

    init {
        REGISTRATE.addDataGenerator(ProviderType.RECIPE) { provider ->
            provider.withNamespace(UPGRADE_AQUATIC) {
                provider.cleaningRecipe(
                    BuiltInRegistries.ITEM.getOrThrow(UPGRADE_AQUATIC.createId("bedroll")),
                    UAItemTags.BEDROLLS,
                )

                VANILLA_DYES.forEach { dye ->
                    provider.dyeingRecipe(dye, UAItemTags.BEDROLLS, {
                        BuiltInRegistries.ITEM.getOrThrow(UPGRADE_AQUATIC.createId("${dye}_bedroll"))
                    }, id = UPGRADE_AQUATIC.createId("dye_${dye}_bedroll")) {
                        group("bedroll")
                    }
                }
            }
        }
    }

    fun register() {
        // Loads this class
    }
}

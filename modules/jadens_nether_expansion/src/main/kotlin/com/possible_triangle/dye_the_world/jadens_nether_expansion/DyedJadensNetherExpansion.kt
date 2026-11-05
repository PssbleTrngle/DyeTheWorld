package com.possible_triangle.dye_the_world.jadens_nether_expansion

import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedstoneLampBlock
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

object DyedJadensNetherExpansion {
    val STAINED_REDSTONE_LAMPS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_stained_redstone_lamp")
                .dyedBlock(dye, ::RedstoneLampBlock)
                .lang("${dye.translation} Stained Redstone Lamp")
                .initialProperties { Blocks.REDSTONE_LAMP }
                .properties { it.mapColor(dye) }
                .stainedRedstoneLamp()
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .tag(BlockTags.create(JADENS_NETHER_EXPANSION.createId("sounds/glowstone")))
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    recipe { context, provider ->
                        provider.shapedDyeingRecipe(dye, Blocks.REDSTONE_LAMP, context)
                    }
                }.register()
        }

    private fun BlockBuilder<*, *>.stainedRedstoneLamp() =
        blockstate { context, provider ->
            provider.createVariant(context) { state ->
                val lit = state.getValue(RedstoneLampBlock.LIT)
                val suffix = if (lit) "_on" else ""
                val model =
                    provider.models().cubeAll(
                        context.name + suffix,
                        JADENS_NETHER_EXPANSION.createId("block/stained_redstone_lamp/${dye}$suffix"),
                    )
                ConfiguredModel
                    .builder()
                    .modelFile(model)
            }
        }
}

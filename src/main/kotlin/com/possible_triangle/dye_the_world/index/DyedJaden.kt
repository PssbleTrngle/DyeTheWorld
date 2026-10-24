package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.JADENS_NETHER_EXPANSION
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedstoneLampBlock
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

object DyedJaden {
    private val REGISTRATE = DyedRegistrate.create(JADENS_NETHER_EXPANSION)

    private val DYES = dyesFor(JADENS_NETHER_EXPANSION)

    val STAINED_REDSTONE_LAMPS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_stained_redstone_lamp")
                .dyedBlock(dye, ::RedstoneLampBlock)
                .lang("${dye.translation} Stained Restone Lamp")
                .initialProperties { Blocks.REDSTONE_LAMP }
                .properties { it.mapColor(dye) }
                .stainedRedstoneLamp()
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .optionalTag(BlockTags.create(JADENS_NETHER_EXPANSION.createId("sounds/glowstone")))
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    recipe { context, provider ->
                        provider.shapedDyeingRecipe(dye, Blocks.REDSTONE_LAMP, context)
                    }
                }.register()
        }

    fun BlockBuilder<*, *>.stainedRedstoneLamp() =
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

    fun register() {
        REGISTRATE.register()
    }
}

package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CONNECTED_GLASS
import com.possible_triangle.dye_the_world.data.connectedGlassBlockState
import com.possible_triangle.dye_the_world.data.connectedPaneBlockState
import com.possible_triangle.dye_the_world.data.connectedPaneItemModel
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.IronBarsBlock
import net.neoforged.neoforge.common.Tags

object DyedConnectedGlass {
    private val REGISTRATE = DyedRegistrate.create(CONNECTED_GLASS)
    private val DYES = dyesFor(CONNECTED_GLASS)

    private fun createPanes(
        type: String,
        fullBlocks: Map<DyeColor, NonNullSupplier<out Block>>,
        block: BlockBuilder<*, *>.() -> Unit,
    ) = DYES.associateWith { dye ->
        REGISTRATE
            .`object`("${type}_glass_${dye}_pane")
            .dyedBlock(dye) { IronBarsBlock(it) }
            .optionalTag(Tags.Blocks.GLASS_PANES)
            .connectedPaneBlockState(type)
            .loot { provider, block -> provider.dropWhenSilkTouch(block) }
            .withItem {
                optionalTag(Tags.Items.GLASS_PANES)
                connectedPaneItemModel(type)
                recipe { context, provider ->
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, context.get())
                        .pattern("###")
                        .pattern("###")
                        .defineUnlocking('#', fullBlocks[dye]!!.get())
                        .save(provider)
                }
            }.apply(block)
            .register()
    }

    private fun createFull(
        type: String,
        block: BlockBuilder<*, *>.() -> Unit,
    ) = DYES.associateWith { dye ->
        REGISTRATE
            .`object`("${type}_glass_$dye")
            .dyedBlock(dye, ::Block)
            .optionalTag(Tags.Blocks.GLASS_BLOCKS)
            .connectedGlassBlockState(type)
            .loot { provider, block -> provider.dropWhenSilkTouch(block) }
            .withItem {
                optionalTag(Tags.Items.GLASS_BLOCKS)
            }.apply(block)
            .register()
    }

    val BORDERLESS =
        createFull("borderless") {
            lang("Connecting ${dye.translation} Stained Glass")
        }

    val BORDERLESS_PANES =
        createPanes("borderless", BORDERLESS) {
            lang("Connecting ${dye.translation} Stained Glass Pane")
        }

    val SCRATCHED =
        createFull("scratched") {
            lang("Scratched ${dye.translation} Stained Glass")
        }

    val SCRATCHED_PANES =
        createPanes("scratched", SCRATCHED) {
            lang("Scratched ${dye.translation} Stained Glass Pane")
        }

    val CLEAR =
        createFull("clear") {
            lang("Clear ${dye.translation} Stained Glass")
        }

    val CLEAR_PANES =
        createPanes("clear", CLEAR) {
            lang("Clear ${dye.translation} Stained Glass Pane")
        }

    val TINTED =
        createFull("tinted_borderless") {
            lang("Connecting Tinted ${dye.translation} Stained Glass")
        }

    fun register() {
        REGISTRATE.register()
    }
}

package com.possible_triangle.dye_the_world.farmers_delight

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.farmers_delight.data.canvasSignBlockstate
import com.possible_triangle.dye_the_world.farmers_delight.data.canvasSignItemModel
import com.possible_triangle.dye_the_world.farmers_delight.data.canvasSignRecipes
import com.possible_triangle.dye_the_world.farmers_delight.data.createDyeRecipes
import com.possible_triangle.dye_the_world.farmers_delight.data.hangingCanvasSignRecipes
import com.possible_triangle.dye_the_world.farmers_delight.world.block.DyedCeilingHangingCanvasSignBlock
import com.possible_triangle.dye_the_world.farmers_delight.world.block.DyedStandingCanvasSignBlock
import com.possible_triangle.dye_the_world.farmers_delight.world.block.DyedWallCanvasSignBlock
import com.possible_triangle.dye_the_world.farmers_delight.world.block.DyedWallHangingCanvasSignBlock
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.SignItem
import net.minecraft.world.level.block.Block
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes
import vectorwing.farmersdelight.common.registry.ModBlocks
import vectorwing.farmersdelight.common.tag.ModTags

object DyedFarmersDelight {
    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, FARMERS_DELIGHT.createId(FARMERS_DELIGHT))

    val CANVAS_WALL_SIGNS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_canvas_wall_sign")
                .dyedBlock(dye) { DyedWallCanvasSignBlock(it, dye) }
                .initialProperties { ModBlocks.RED_CANVAS_WALL_SIGN.get() }
                .lang { it.descriptionId + ".wall" }
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.CANVAS_SIGN.get() }
                .register()
        }

    val CANVAS_SIGNS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_canvas_sign")
                .dyedBlock(dye) { DyedStandingCanvasSignBlock(dye) }
                .initialProperties { ModBlocks.RED_CANVAS_SIGN.get() }
                .lang("${dye.translation} Canvas Sign")
                .germanLang("${dye.germanTranslation(Genus.I)} Canvas Schild")
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.CANVAS_SIGN.get() }
                .withItem(dye.signItem(CANVAS_WALL_SIGNS)) {
                    tag(ModTags.Items.CANVAS_SIGNS)
                    canvasSignRecipes()
                    canvasSignItemModel()
                    tab(TAB)
                }.register()
        }

    val HANGING_CANVAS_WALL_SIGNS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_wall_hanging_canvas_sign")
                .dyedBlock(dye) { DyedWallHangingCanvasSignBlock(it, dye) }
                .initialProperties { ModBlocks.RED_HANGING_CANVAS_WALL_SIGN.get() }
                .lang { it.descriptionId + ".wall" }
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.HANGING_CANVAS_SIGN.get() }
                .register()
        }

    val HANGING_CANVAS_SIGNS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_hanging_canvas_sign")
                .dyedBlock(dye) { DyedCeilingHangingCanvasSignBlock(dye) }
                .initialProperties { ModBlocks.RED_HANGING_CANVAS_SIGN.get() }
                .lang("${dye.translation} Hanging Canvas Sign")
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.HANGING_CANVAS_SIGN.get() }
                .withItem(dye.signItem(HANGING_CANVAS_WALL_SIGNS)) {
                    tag(ModTags.Items.HANGING_CANVAS_SIGNS)
                    hangingCanvasSignRecipes()
                    canvasSignItemModel()
                    tab(TAB)
                }.register()
        }

    init {
        migrateBlockEntity("canvas_sign", FARMERS_DELIGHT)
        migrateBlockEntity("hanging_canvas_sign", FARMERS_DELIGHT)

        REGISTRATE.createDyeRecipes()
    }
}

private fun <T : Block> DyeColor.signItem(wallSigns: Map<DyeColor, NonNullSupplier<out Block>>) =
    { block: T, properties: Item.Properties ->
        SignItem(
            properties,
            block,
            wallSigns[this]!!.get(),
        )
    }

// TODO move to core module
private val DARK_BACKGROUNDS = listOf("indigo", "ginger", "maroon", "navy", "rose", "teal", "verdant")

val DyeColor.isDarkBackground get() = DARK_BACKGROUNDS.contains(serializedName)

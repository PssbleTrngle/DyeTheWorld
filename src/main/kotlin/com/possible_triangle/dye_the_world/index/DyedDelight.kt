package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.FARMERS_DELIGHT
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.canvasSignBlockstate
import com.possible_triangle.dye_the_world.data.canvasSignItemModel
import com.possible_triangle.dye_the_world.data.canvasSignRecipes
import com.possible_triangle.dye_the_world.data.hangingCanvasSignRecipes
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.`object`.block.DyedCeilingHangingCanvasSignBlock
import com.possible_triangle.dye_the_world.`object`.block.DyedStandingCanvasSignBlock
import com.possible_triangle.dye_the_world.`object`.block.DyedWallCanvasSignBlock
import com.possible_triangle.dye_the_world.`object`.block.DyedWallHangingCanvasSignBlock
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.SignItem
import net.minecraft.world.level.block.Block
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes
import vectorwing.farmersdelight.common.registry.ModBlocks
import vectorwing.farmersdelight.common.tag.ModTags

object DyedDelight {
    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation(FARMERS_DELIGHT, FARMERS_DELIGHT))

    val CANVAS_WALL_SIGNS =
        dyesFor(FARMERS_DELIGHT).associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_canvas_wall_sign")
                .dyedBlock(dye, FARMERS_DELIGHT) { DyedWallCanvasSignBlock(it, dye) }
                .initialProperties { ModBlocks.RED_CANVAS_WALL_SIGN.get() }
                .lang { it.descriptionId + ".wall" }
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.CANVAS_SIGN.get() }
                .register()
        }

    val CANVAS_SIGNS =
        dyesFor(FARMERS_DELIGHT).associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_canvas_sign")
                .dyedBlock(dye, FARMERS_DELIGHT) { DyedStandingCanvasSignBlock(dye) }
                .initialProperties { ModBlocks.RED_CANVAS_SIGN.get() }
                .lang("${dye.translation} Canvas Sign")
                .germanLang("${dye.germanTranslation(Genus.I)} Canvas Schild")
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.CANVAS_SIGN.get() }
                .withItem(dye.signItem(CANVAS_WALL_SIGNS)) {
                    optionalTag(ModTags.Items.CANVAS_SIGNS)
                    canvasSignRecipes()
                    canvasSignItemModel()
                    tab(TAB)
                }.register()
        }

    val HANGING_CANVAS_WALL_SIGNS =
        dyesFor(FARMERS_DELIGHT).associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_wall_hanging_canvas_sign")
                .dyedBlock(dye, FARMERS_DELIGHT) { DyedWallHangingCanvasSignBlock(it, dye) }
                .initialProperties { ModBlocks.RED_HANGING_CANVAS_WALL_SIGN.get() }
                .lang { it.descriptionId + ".wall" }
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.HANGING_CANVAS_SIGN.get() }
                .register()
        }

    val HANGING_CANVAS_SIGNS =
        dyesFor(FARMERS_DELIGHT).associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_hanging_canvas_sign")
                .dyedBlock(dye, FARMERS_DELIGHT) { DyedCeilingHangingCanvasSignBlock(dye) }
                .initialProperties { ModBlocks.RED_HANGING_CANVAS_SIGN.get() }
                .lang("${dye.translation} Hanging Canvas Sign")
                .canvasSignBlockstate()
                .existingBlockEntity { ModBlockEntityTypes.HANGING_CANVAS_SIGN.get() }
                .withItem(dye.signItem(HANGING_CANVAS_WALL_SIGNS)) {
                    optionalTag(ModTags.Items.HANGING_CANVAS_SIGNS)
                    hangingCanvasSignRecipes()
                    canvasSignItemModel()
                    tab(TAB)
                }.register()
        }

    fun register() {
        migrateBlockEntity("canvas_sign", FARMERS_DELIGHT)
        migrateBlockEntity("hanging_canvas_sign", FARMERS_DELIGHT)
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

private val DARK_BACKGROUNDS = listOf("indigo", "ginger", "maroon", "navy", "rose", "teal", "verdant")

val DyeColor.isDarkBackground get() = DARK_BACKGROUNDS.contains(serializedName)

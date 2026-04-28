package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.FARMERS_DELIGHT
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import vectorwing.farmersdelight.common.tag.ModTags

fun <T : Item, P> ItemBuilder<T, P>.canvasSignRecipes() = recipe { context, provider ->
    provider.dyeingRecipe(dye, ModTags.Items.CANVAS_SIGNS, context) {
        group("canvas_sign")
    }
}

fun <T : Item, P> ItemBuilder<T, P>.hangingCanvasSignRecipes() = recipe { context, provider ->
    provider.dyeingRecipe(dye, ModTags.Items.HANGING_CANVAS_SIGNS, context) {
        group("hanging_canvas_sign")
    }
}

fun <T : Item, P> ItemBuilder<T, P>.canvasSignItemModel() = model { context, provider ->
    provider.generated(context, Constants.MOD_ID.createId("item/$FARMERS_DELIGHT/${context.name}"))
}

fun <T : Block, P> BlockBuilder<T, P>.canvasSignBlockstate() = blockstate { context, provider ->
    val model = provider.models().getExistingFile(FARMERS_DELIGHT.createId("block/canvas_sign"))
    provider.simpleBlock(context.get(), model)
}
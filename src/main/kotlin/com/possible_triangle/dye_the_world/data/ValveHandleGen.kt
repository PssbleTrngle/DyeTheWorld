package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.simibubi.create.AllTags
import com.simibubi.create.content.kinetics.crank.ValveHandleBlock
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.core.Direction
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : ValveHandleBlock, P> BlockBuilder<T, P>.valveBlockstate() = blockstate { context, provider ->
    val model = provider.models().withExistingParent(context.name, CREATE.createId("block/valve_handle"))
        .texture("3", Constants.MOD_ID.createId("block/$CREATE/valve_handle/$dye"))

    provider.createVariant(context) { state ->
        val facing = state.getValue(ValveHandleBlock.FACING)

        val xRot = when (facing) {
            Direction.DOWN -> 180
            Direction.UP -> 0
            else -> 90
        }

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationX(xRot)
            .rotationY(facing.yRot)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.valveRecipe() = recipe { context, provider ->
    provider.dyeingRecipe(dye, AllTags.AllItemTags.VALVE_HANDLES.tag, context)
}
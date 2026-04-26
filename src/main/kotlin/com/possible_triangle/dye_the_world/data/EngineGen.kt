package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_SIMULATED
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.simibubi.create.content.contraptions.bearing.SailBlock
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import dev.simulated_team.simulated.content.blocks.portable_engine.PortableEngineBlock
import dev.simulated_team.simulated.content.blocks.symmetric_sail.SymmetricSailBlock
import net.minecraft.core.Direction
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : PortableEngineBlock, P> BlockBuilder<T, P>.portableEngineBlockstate() = blockstate { context, provider ->
    val parent = CREATE_SIMULATED.createId("block/portable_engine/block")
    val model = provider.models().withExistingParent(context.name, parent)
        .texture("0", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/portable_engine/$dye"))
        .texture("particle", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/portable_engine/$dye"))

    provider.createVariant(context, BlockStateProperties.LIT) { state ->
        val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.portableEngineItemModel() = model { context, provider ->
    val parent = CREATE_SIMULATED.createId("block/portable_engine/item")
    provider.withExistingParent(context.name, parent)
        .texture("0", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/portable_engine/$dye"))
        .texture("particle", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/portable_engine/$dye"))
}
package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_SIMULATED
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.textureAndParticle
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.portableEngineBlockstate() =
    blockstate { context, provider ->
        val parent = CREATE_SIMULATED.createId("block/portable_engine/block")
        val model =
            provider
                .models()
                .withExistingParent(context.name, parent)
                .textureAndParticle("0", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/portable_engine/$dye"))

        provider.createVariant(context, BlockStateProperties.LIT) { state ->
            val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.portableEngineItemModel() =
    model { context, provider ->
        val parent = CREATE_SIMULATED.createId("block/portable_engine/item")
        provider
            .withExistingParent(context.name, parent)
            .textureAndParticle("0", Constants.MOD_ID.createId("block/$CREATE_SIMULATED/portable_engine/$dye"))
    }

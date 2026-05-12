package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.simibubi.create.content.redstone.nixieTube.DoubleFaceAttachedBlock
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlock
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.ConfiguredModel

private val DoubleFaceAttachedBlock.DoubleAttachFace.xRot
    get() =
        when (this) {
            DoubleFaceAttachedBlock.DoubleAttachFace.FLOOR -> 0
            DoubleFaceAttachedBlock.DoubleAttachFace.CEILING -> 180
            else -> 90
        }

fun <T : Block, P> BlockBuilder<T, P>.nixieTubeBlockstate() =
    blockstate { context, provider ->
        val model = provider.models().getExistingFile(CREATE.createId("block/nixie_tube/block"))

        provider.createVariant(context) { state ->
            val facing = state.getValue(NixieTubeBlock.FACING)
            val face = state.getValue(NixieTubeBlock.FACE)

            val xRot = face.xRot
            val yRot =
                if (xRot == 90) {
                    facing.yRot
                } else {
                    (facing.yRot + 180) % 360
                }

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(yRot)
                .rotationX(xRot)
        }
    }

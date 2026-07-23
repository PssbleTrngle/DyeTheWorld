package com.possible_triangle.dye_the_world.data

import com.ninni.twigs.block.enums.SiltPotBlock
import com.possible_triangle.dye_the_world.Constants.Mods.TWIGS
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.siltPotBlockstate() =
    blockstate { context, provider ->
        val parent = TWIGS.createId("block/template_silt_pot")

        provider.createVariant(context) { state ->
            val filled = state.getValue(SiltPotBlock.FILLED)
            val topTexture = if (filled) "top_filled" else "top"
            val model =
                provider
                    .models()
                    .withExistingParent(context.name, parent)
                    .texture("side", dye.texture(TWIGS.createId("silt_pot")))
                    .texture("bottom", dye.texture(TWIGS.createId("silt_pot"), "bottom"))
                    .texture("top", dye.texture(TWIGS.createId("silt_pot"), topTexture))

            ConfiguredModel
                .builder()
                .modelFile(model)
        }
    }

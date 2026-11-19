package com.possible_triangle.dye_the_world.arts_and_crafts.data

import com.kekecreations.arts_and_crafts.neoforge.datagen.client.ArtsAndCraftsBlockStateProvider
import com.possible_triangle.dye_the_world.arts_and_crafts.ARTS_AND_CRAFTS
import com.possible_triangle.dye_the_world.extensions.noBlockState
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.world.level.block.Block

fun <T : Block, P> BlockBuilder<T, P>.plasterBlockstate() =
    apply {
        noBlockState()
        addMiscData(ProviderType.GENERIC_CLIENT) { generic ->
            generic.add {
                object : ArtsAndCraftsBlockStateProvider(it.output, ARTS_AND_CRAFTS, it.existingFileHelper) {
                    override fun registerStatesAndModels() {
                        plasterWithItem(entry, dye)
                    }
                }
            }
        }
    }

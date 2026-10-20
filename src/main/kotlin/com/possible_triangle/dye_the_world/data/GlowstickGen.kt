package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.SPELUNKERY
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.texture
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

fun <T : Item, P> ItemBuilder<T, P>.glowstickItemModel() =
    model { context, provider ->
        provider.handheld(context)
    }

fun <T : Block, P> BlockBuilder<T, P>.glowstickBlockState() =
    blockstate { context, provider ->
        val model =
            provider
                .models()
                .withExistingParent(context.name, SPELUNKERY.createId("block/glowstick"))
                .texture("1", context.texture)
                .texture("particle", context.texture)

        provider.directionalBlock(context.get(), model)
    }

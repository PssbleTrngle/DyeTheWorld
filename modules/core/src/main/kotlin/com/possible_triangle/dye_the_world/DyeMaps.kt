package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.extensions.createId
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredHolder

typealias DyedMap<T> = Map<DyeColor, Holder<T>>

fun dyedBlockMap(
    modid: String,
    type: String,
): DyedMap<Block> =
    dyesFor(modid).associateWith {
        val id = it.namespace.createId("${it}_$type")
        DeferredHolder.create(Registries.BLOCK, id)
    }

fun dyedItemMap(
    modid: String,
    type: String,
): DyedMap<Item> =
    dyesFor(modid).associateWith {
        val id = it.namespace.createId("${it}_$type")
        DeferredHolder.create(Registries.ITEM, id)
    }

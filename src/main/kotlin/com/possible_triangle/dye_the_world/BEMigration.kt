package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.mixins.BlockEntityTypeAccessor
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType

fun migrateBlockEntity(name: String, namespace: String) {
    BuiltInRegistries.BLOCK_ENTITY_TYPE.addAlias(Constants.MOD_ID.createId(name), namespace.createId(name))
}

fun BlockEntityType<*>.allowBlock(vararg blocks: Block) {
    val accessor = this as BlockEntityTypeAccessor
    accessor.validBlocks = accessor.validBlocks + blocks
}

fun <B : Block, P> BlockBuilder<B, P>.existingBlockEntity(type: () -> BlockEntityType<*>) = apply {
    owner.addRegisterCallback(Registries.BLOCK_ENTITY_TYPE) {
        type().allowBlock(entry)
    }
}
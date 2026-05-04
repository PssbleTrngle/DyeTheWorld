package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_SIMULATED
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.textureAndParticle
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.tterrag.registrate.builders.AbstractBuilder
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

internal enum class Position : StringRepresentable {
    SINGLE,
    LEFT,
    RIGHT,
    MIDDLE;

    override fun getSerializedName() = this.name.lowercase()
}

internal val NAMEPLATE_POSITION: EnumProperty<Position> = EnumProperty.create("position", Position::class.java)

private val AbstractBuilder<*, *, *, *>.texture
    get() = CREATE_SIMULATED.createId("block/nameplate/${dye}_nameplate")

fun <T : Block, P> BlockBuilder<T, P>.nameplateBlockstate() = blockstate { context, provider ->
    val models = Position.entries.associateWith {
        val type = it.serializedName
        val parent = CREATE_SIMULATED.createId("block/nameplate/block_$type")
        provider.models()
            .withExistingParent("${context.name}_${type}", parent)
            .textureAndParticle("0", texture)
    }

    provider.createVariant(context) { state ->
        val position = state.getValue(NAMEPLATE_POSITION)
        val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)

        ConfiguredModel.builder()
            .modelFile(models[position]!!)
            .rotationY(facing.yRot)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.nameplateItemModel() = model { context, provider ->
    val parent = CREATE_SIMULATED.createId("block/nameplate/item")
    provider.withExistingParent(context.name, parent)
        .texture("0", texture)
}
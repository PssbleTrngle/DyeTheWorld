package com.possible_triangle.dye_the_world.data

import com.google.gson.JsonObject
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CONNECTED_GLASS
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.dye
import com.supermartijn642.fusion.api.model.DefaultModelTypes
import com.supermartijn642.fusion.api.model.ModelInstance
import com.supermartijn642.fusion.api.model.data.ConnectingModelData
import com.supermartijn642.fusion.api.model.data.ConnectingModelDataBuilder
import com.supermartijn642.fusion.api.predicate.ConnectionDirection
import com.supermartijn642.fusion.api.predicate.DefaultConnectionPredicates
import com.supermartijn642.fusion.api.texture.DefaultTextureTypes
import com.supermartijn642.fusion.api.texture.data.ConnectingTextureData
import com.supermartijn642.fusion.api.texture.data.ConnectingTextureLayout
import com.supermartijn642.fusion.api.util.Pair
import com.supermartijn642.fusion.model.ModelTypeRegistryImpl
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.Builder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.PipeBlock.PROPERTY_BY_DIRECTION
import net.minecraft.world.level.block.state.properties.Property
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder
import net.neoforged.neoforge.client.model.generators.BlockModelProvider
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder
import net.neoforged.neoforge.client.model.generators.ModelFile.ExistingModelFile
import net.neoforged.neoforge.common.data.ExistingFileHelper

private class ConnectingLoader(
    model: BlockModelBuilder,
    fileHelper: ExistingFileHelper,
) : CustomLoaderBuilder<BlockModelBuilder>(
    "fusion".createId("model"),
    model,
    fileHelper,
    false
) {
    private val builder = ConnectingModelData.builder()

    fun with(factory: ConnectingModelDataBuilder.() -> Unit) = apply {
        builder.factory()
    }

    override fun toJson(json: JsonObject): JsonObject {
        val instance = ModelInstance.of(DefaultModelTypes.CONNECTING, builder.build())
        return ModelTypeRegistryImpl.serializeModelData(instance)
    }
}

private fun BlockModelProvider.connected(
    path: String,
    factory: ConnectingModelDataBuilder.() -> Unit
): BlockModelBuilder {
    return getBuilder(path)
        .customLoader(::ConnectingLoader)
        .with(factory)
        .end()
}

private fun Builder<*, *, *, *>.glassTexture(type: String): ResourceLocation {
    return Constants.MOD_ID.createId("block/${CONNECTED_GLASS}/${type}/${dye}")
}

private fun Builder<*, *, *, *>.edgeTexture(type: String): ResourceLocation {
    return dye.namespace.createId("block/${dye}_stained_glass_pane_top")
}

fun <T : Block, P> BlockBuilder<T, P>.connectedPaneBlockState(type: String) = apply {
    fusionModifier { context, provider ->
        provider.modifier(Constants.MOD_ID.createId("pane_culling_fix"))
            .paneCullingFix(true)
            .target(context.get())
    }

    blockstate { context, provider ->
        fun ConnectingModelDataBuilder.textures() = apply {
            texture("pane", glassTexture(type))
            texture("edge", edgeTexture(type))
        }

        val post = provider.models().connected("${context.name}_post") {
            parent(CONNECTED_GLASS.createId("block/template_glass_pane_post"))
            textures()
            defaultConnections(DefaultConnectionPredicates.isSameBlock())
        }

        val notUpOrDown = DefaultConnectionPredicates.isDirection(
            *ConnectionDirection.entries
                .filter { it !== ConnectionDirection.TOP && it !== ConnectionDirection.BOTTOM }
                .toTypedArray()
        )

        fun <T : Comparable<T>> matchesState(key: Property<T>, value: T) = DefaultConnectionPredicates.matchState(
            context.get(), Pair.of(key, value)
        )

        val sides = PROPERTY_BY_DIRECTION.filterKeys { it.axis.isHorizontal }.mapValues { (direction, property) ->
            val parent = when (direction) {
                Direction.SOUTH, Direction.WEST -> "template_glass_pane_side_alt"
                else -> "template_glass_pane_side"
            }

            provider.models().connected("${context.name}_side_$direction") {
                parent(CONNECTED_GLASS.createId("block/$parent"))
                textures()
                defaultConnections(matchesState(property, true).or(notUpOrDown))
            }
        }

        val noSide = provider.models().connected("${context.name}_noside") {
            parent(CONNECTED_GLASS.createId("block/template_glass_pane_noside"))
            textures()
            defaultConnections(DefaultConnectionPredicates.isSameBlock())
        }

        val noSideAlt = provider.models().connected("${context.name}_noside_alt") {
            parent(CONNECTED_GLASS.createId("block/template_glass_pane_noside_alt"))
            textures()
            defaultConnections(DefaultConnectionPredicates.isSameBlock())
        }

        val builder = provider.getMultipartBuilder(context.get())

        builder.part()
            .modelFile(post)
            .addModel()

        sides.forEach { (direction, model) ->
            val property = PROPERTY_BY_DIRECTION[direction]!!
            builder
                .part()
                .rotationY(if (direction.axis == Direction.Axis.X) 90 else 0)
                .modelFile(model)
                .addModel()
                .condition(property, true)

            builder
                .part()
                .rotationY(
                    when (direction) {
                        Direction.SOUTH -> 90
                        Direction.WEST -> 270
                        else -> 0
                    }
                )
                .modelFile(
                    when (direction) {
                        Direction.SOUTH, Direction.EAST -> noSideAlt
                        else -> noSide
                    }
                )
                .addModel()
                .condition(property, false)
        }
    }
}

fun <T : Block, P> BlockBuilder<T, P>.connectedGlassBlockState(type: String) = apply {
    fusionMetadata { _, provider ->
        provider.addTextureMetadata(
            glassTexture(type),
            DefaultTextureTypes.CONNECTING,
            ConnectingTextureData.builder().layout(ConnectingTextureLayout.PIECED).build(),
        )
    }

    blockstate { context, provider ->
        val model = provider.models().connected(context.name) {
            parent("minecraft".createId("block/cube_all"))
            texture("all", glassTexture(type))
        }

        provider.simpleBlock(context.get(), model)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.connectedPaneItemModel(type: String) = model { context, provider ->
    val parent = ExistingModelFile(CONNECTED_GLASS.createId("pane_item_template"), provider.existingFileHelper)
    parent.assertExistence()
    provider.getBuilder(context.name)
        .parent(parent)
        .texture("all", glassTexture(type))
        .texture("edge", edgeTexture(type))
}
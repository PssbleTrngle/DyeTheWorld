package com.possible_triangle.dye_the_world.extensions

import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockModelProvider
import net.neoforged.neoforge.client.model.generators.ModelBuilder

val TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent")

fun <T : ModelBuilder<T>> ModelBuilder<T>.translucent() = renderType(TRANSLUCENT)

fun <T : ModelBuilder<T>> ModelBuilder<T>.textureAndParticle(
    key: String,
    texture: ResourceLocation,
) = apply {
    texture(key, texture)
    texture("particle", texture)
}

fun BlockModelProvider.particleOnly(
    context: DataGenContext<Block, out Block>,
    texture: ResourceLocation,
) = getBuilder(context.name).texture("particle", texture)

fun <T : Block, P> BlockBuilder<T, P>.particleOnly(texture: ResourceLocation) =
    blockstate { context, provider ->
        provider.simpleBlock(context.get(), provider.models().particleOnly(context, texture))
    }

fun <T : Block, P> BlockBuilder<T, P>.particleOnly() = particleOnly(owner.modid.createId("block/$name"))

val DataGenContext<*, *>.texture: ResourceLocation
    get() =
        if (entry is Item) {
            itemTexture
        } else {
            blockTexture
        }

val DataGenContext<*, *>.blockTexture: ResourceLocation
    get() = id.withPrefix("block/")

val DataGenContext<*, *>.itemTexture: ResourceLocation
    get() = id.withPrefix("item/")

val Block.texture get() = BuiltInRegistries.BLOCK.getKey(this).withPrefix("block/")

val Item.texture get() = BuiltInRegistries.ITEM.getKey(this).withPrefix("item/")

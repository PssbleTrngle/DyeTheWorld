package com.possible_triangle.dye_the_world.extensions

import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.model.generators.ModelBuilder

val TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent")

fun <T : ModelBuilder<T>> ModelBuilder<T>.translucent() = renderType(TRANSLUCENT)

fun <T : ModelBuilder<T>> ModelBuilder<T>.textureAndParticle(key: String, texture: ResourceLocation) = apply {
    texture(key, texture)
    texture("particle", texture)
}


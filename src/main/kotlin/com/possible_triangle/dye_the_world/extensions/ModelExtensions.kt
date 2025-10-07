package com.possible_triangle.dye_the_world.extensions

import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.model.generators.ModelBuilder

val TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent")

fun ModelBuilder<*>.translucent() = renderType(TRANSLUCENT)
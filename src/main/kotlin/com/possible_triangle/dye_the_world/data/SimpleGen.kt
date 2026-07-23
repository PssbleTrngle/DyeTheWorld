package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.mod
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockModelProvider

fun DyeColor.vanillaTexture(type: ResourceLocation): ResourceLocation = namespace.createId("block/${this}_${type.path}")

fun DyeColor.texture(
    type: ResourceLocation,
    suffix: String? = null,
): ResourceLocation =
    Constants.MOD_ID.createId("block/${type.namespace}/${type.path}/$this").let {
        if (suffix != null) {
            it.withSuffix("_$suffix")
        } else {
            it
        }
    }

fun BlockModelProvider.dyedCube(
    context: DataGenContext<Block, out Block>,
    type: ResourceLocation,
    dye: DyeColor,
) = cubeAll(context.name, dye.texture(type))

fun <T : Block, P> BlockBuilder<T, P>.cubeBlockstate(path: String) =
    blockstate { context, provider ->
        provider.simpleBlock(context.get(), provider.models().dyedCube(context, mod.createId(path), dye))
    }

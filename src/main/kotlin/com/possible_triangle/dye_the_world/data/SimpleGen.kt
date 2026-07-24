package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.mod
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateItemModelProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.BlockModelProvider

fun DyeColor.vanillaTexture(type: ResourceLocation): ResourceLocation = namespace.createId("block/${this}_${type.path}")

fun DyeColor.texture(
    path: String,
    name: ResourceLocation,
    suffix: String? = null,
): ResourceLocation =
    Constants.MOD_ID.createId("$path/${name.namespace}/${name.path}/$this").let {
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
) = cubeAll(context.name, dye.texture("block", type))

fun <T : Block, P> BlockBuilder<T, P>.cubeBlockstate(path: String) =
    blockstate { context, provider ->
        provider.simpleBlock(context.get(), provider.models().dyedCube(context, mod.createId(path), dye))
    }

fun RegistrateItemModelProvider.dyedFlat(
    context: DataGenContext<Item, out Item>,
    type: ResourceLocation,
    dye: DyeColor,
) = generated(context, dye.texture("item", type))

fun <T : Item, P> ItemBuilder<T, P>.flat(path: String) =
    model { context, provider ->
        provider.dyedFlat(context, mod.createId(path), dye)
    }

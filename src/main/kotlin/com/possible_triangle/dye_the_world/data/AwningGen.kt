package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.textureAndParticle
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.supplementaries.common.block.blocks.AwningBlock
import net.mehvahdjukaar.supplementaries.reg.ModRegistry
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun <T : AwningBlock, P> BlockBuilder<T, P>.awningBlockstate() =
    blockstate { context, provider ->
        fun texture(suffix: String = "") = Constants.MOD_ID.createId("block/$SUPPLEMENTARIES/awnings/awning_${dye}$suffix")

        provider.createVariant(context) { state ->
            val facing = state.getValue(AwningBlock.FACING)
            val bottom = state.getValue(AwningBlock.BOTTOM)
            val slanted = state.getValue(AwningBlock.SLANTED)

            val slantedSuffix = if (slanted) "_slanted" else ""
            val halfSuffix = if (bottom) "bottom" else "top"
            val type = halfSuffix + slantedSuffix

            val parent = SUPPLEMENTARIES.createId("block/awnings/$type")
            val model =
                provider
                    .models()
                    .withExistingParent("${context.name}_$type", parent)
                    .textureAndParticle("1", texture())
                    .texture("up", texture("_side"))

            ConfiguredModel
                .builder()
                .modelFile(model)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.awningItemModel() =
    model { context, provider ->
        provider
            .withExistingParent(context.name, SUPPLEMENTARIES.createId("item/awning"))
            .texture("1", Constants.MOD_ID.createId("block/$SUPPLEMENTARIES/awnings/awning_$dye"))
            .texture("up", Constants.MOD_ID.createId("block/$SUPPLEMENTARIES/awnings/awning_${dye}_side"))
    }

fun <T : Item, P> ItemBuilder<T, P>.awningRecipe() =
    recipe { context, provider ->
        provider.dyeingRecipe(dye, ModRegistry.AWNINGS[null]!!.get(), context::get)
    }

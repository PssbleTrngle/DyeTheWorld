package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_AERONAUTICS
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.defineUnlocking
import com.possible_triangle.dye_the_world.extensions.textureAndParticle
import com.possible_triangle.dye_the_world.registrate.dye
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe
import com.simibubi.create.foundation.data.BlockStateGen
import com.tterrag.registrate.builders.AbstractBuilder
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block

private val AbstractBuilder<*, *, *, *>.texture
    get() =
        Constants.MOD_ID.createId("block/$CREATE_AERONAUTICS/envelope/$dye")

fun <T : Block, P> BlockBuilder<T, P>.envelopeShaftBlockstate() = blockstate { context, provider ->
    val parent = CREATE_AERONAUTICS.createId("block/envelope_encased_shaft/block")
    val model = provider.models().withExistingParent(context.name, parent)
        .textureAndParticle("0", texture)
    BlockStateGen.axisBlock(context, provider) { model }
}

fun <T : Block, P> BlockBuilder<T, P>.envelopeBlockstate() = blockstate { context, provider ->
    val model = provider.models().cubeAll(name, texture)
    provider.simpleBlock(context.get(), model)
}

fun <T : Item, P> ItemBuilder<T, P>.envelopeRecipe() = recipe { context, provider ->
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 4)
        .pattern("WS")
        .pattern("SW")
        .defineUnlocking('W', dye.blockOf("wool"))
        .defineUnlocking('S', Items.STICK)
        .save(provider)

    ItemApplicationRecipe.Builder(::DeployerApplicationRecipe, context.id)
        .output(context.get(), 3)
        .require(dye.blockOf("wool"))
        .require(Items.STICK)
        .build(provider)
}
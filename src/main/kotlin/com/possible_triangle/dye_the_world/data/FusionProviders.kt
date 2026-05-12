package com.possible_triangle.dye_the_world.data

import com.supermartijn642.fusion.api.provider.FusionBlockModelModifierProvider
import com.supermartijn642.fusion.api.provider.FusionTextureMetadataProvider
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateProvider
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import net.minecraft.data.PackOutput
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.LogicalSide

class FusionModifierProvider(
    output: PackOutput,
    private val owner: AbstractRegistrate<*>,
) : FusionBlockModelModifierProvider(owner.modid, output),
    RegistrateProvider {
    companion object {
        val TYPE =
            ProviderType.register("Fusion Modifiers") { owner, event, _ ->
                FusionModifierProvider(event.generator.packOutput, owner)
            }
    }

    override fun generate() {
        owner.genData(TYPE, this)
    }

    override fun getSide() = LogicalSide.CLIENT
}

fun <T : Block, P> BlockBuilder<T, P>.fusionModifier(factory: NonNullBiConsumer<DataGenContext<Block, T>, FusionModifierProvider>) =
    setData(FusionModifierProvider.TYPE, factory)

class FusionMetadataProvider(
    output: PackOutput,
    private val owner: AbstractRegistrate<*>,
) : FusionTextureMetadataProvider(owner.modid, output),
    RegistrateProvider {
    companion object {
        val TYPE =
            ProviderType.register("Fusion Metadata") { owner, event, _ ->
                FusionMetadataProvider(event.generator.packOutput, owner)
            }
    }

    override fun generate() {
        owner.genData(TYPE, this)
    }

    override fun getSide() = LogicalSide.CLIENT
}

fun <T : Block, P> BlockBuilder<T, P>.fusionMetadata(factory: NonNullBiConsumer<DataGenContext<Block, T>, FusionMetadataProvider>) =
    setData(FusionMetadataProvider.TYPE, factory)

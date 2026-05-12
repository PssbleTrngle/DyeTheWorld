package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.getOrThrow
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateProvider
import net.mehvahdjukaar.moonlight.api.misc.BlockAndItem
import net.mehvahdjukaar.moonlight.core.set.ColorSetModification
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.neoforged.fml.LogicalSide
import net.neoforged.neoforge.common.conditions.ModLoadedCondition
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.common.data.JsonCodecProvider
import java.util.concurrent.CompletableFuture

private val PROVIDER_TYPE =
    ProviderType.registerProvider("color sets") { context ->
        ColorSetProvider(context.output, context.provider, context.parent, context.type, context.fileHelper)
    }

private class ColorSetProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    private val owner: AbstractRegistrate<*>,
    private val type: ProviderType<ColorSetProvider>,
    existingFileHelper: ExistingFileHelper,
) : JsonCodecProvider<ColorSetModification>(
        output,
        PackOutput.Target.DATA_PACK,
        "color_sets",
        PackType.SERVER_DATA,
        ColorSetModification.CODEC,
        lookupProvider,
        owner.modid,
        existingFileHelper,
    ),
    RegistrateProvider {
    override fun gather() {
        owner.genData(type, this)
    }

    override fun getSide() = LogicalSide.SERVER
}

private val colorSets =
    mapOf(
        ResourceLocation.withDefaultNamespace("bundle") to Constants.Mods.VANILLA_BACKPORT,
    )

fun AbstractRegistrate<*>.generateColorSetModifications() {
    addDataGenerator(PROVIDER_TYPE) { provider ->
        colorSets.forEach { (id, modId) ->
            val dyes = dyesFor(modId)

            provider.conditionally(Constants.MOD_ID.createId("${id.path}s")) { builder ->
                builder
                    .addCondition(ModLoadedCondition(modId))
                    .withCarrier(
                        ColorSetModification(
                            dyes
                                .associateWith { Constants.MOD_ID.createId("${it.serializedName}_${id.path}") }
                                .mapValues { BuiltInRegistries.ITEM.getOrThrow(it.value) }
                                .mapValues { BlockAndItem(null, it.value) }
                                .mapKeys { it.key.serializedName },
                            false,
                            id,
                        ),
                    )
            }
        }
    }
}

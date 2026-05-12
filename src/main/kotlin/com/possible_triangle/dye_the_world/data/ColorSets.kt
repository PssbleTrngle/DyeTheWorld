package com.possible_triangle.dye_the_world.data

import com.mojang.serialization.JsonOps
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.dyesFor
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateProvider
import net.mehvahdjukaar.moonlight.core.set.ColorSetModification
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.JsonCodecProvider
import net.minecraftforge.fml.LogicalSide
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

private val PROVIDER_TYPE =
    ProviderType.register("color sets") { owner, context ->
        ColorSetProvider(context.generator.packOutput, context.lookupProvider, owner, context.existingFileHelper)
    }

private class ColorSetProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    private val owner: AbstractRegistrate<*>,
    existingFileHelper: ExistingFileHelper,
) : JsonCodecProvider<ColorSetModification>(
        output,
        existingFileHelper,
        owner.modid,
        JsonOps.INSTANCE,
        PackType.SERVER_DATA,
        "color_sets",
        ColorSetModification.CODEC,
        hashMapOf(),
    ),
    RegistrateProvider {
    private val entries = hashMapOf<ResourceLocation, ColorSetModification>()

    override fun gather(consumer: BiConsumer<ResourceLocation, ColorSetModification>) {
        owner.genData(PROVIDER_TYPE, this)
        entries.forEach(consumer)
    }

    fun add(
        id: ResourceLocation,
        set: ColorSetModification,
    ) {
        entries[id] = set
    }

    override fun getSide() = LogicalSide.SERVER
}

private val colorSets =
    mapOf(
        ResourceLocation("bundle") to Constants.Mods.VANILLA_BACKPORT,
    )

fun AbstractRegistrate<*>.generateColorSetModifications() {
    addDataGenerator(PROVIDER_TYPE) { provider ->
        colorSets.forEach { (id, modId) ->
            val dyes = dyesFor(modId)

            /* TODO waiting for moonlight
            provider.add(
                Constants.MOD_ID.createId("${id.path}s"),
                ColorSetModification(
                    dyes
                        .associateWith { Constants.MOD_ID.createId("${it.serializedName}_${id.path}") }
                        .mapValues { BuiltInRegistries.ITEM.getOrThrow(it.value) }
                        .mapValues { BlockAndItem(Blocks.AIR, it.value) }
                        .mapKeys { it.key.serializedName },
                    false,
                    id
                )
            )
             */
        }
    }
}

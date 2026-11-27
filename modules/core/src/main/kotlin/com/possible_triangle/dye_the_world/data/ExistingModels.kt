package com.possible_triangle.dye_the_world.data

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.neoforged.neoforge.common.data.ExistingFileHelper

private val MODEL = ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".json", "models")

fun AbstractRegistrate<*>.registerExistingFile(vararg existing: ResourceLocation) {
    addDataGenerator(ProviderType.BLOCKSTATE) { provider ->
        existing.forEach {
            provider.models().existingFileHelper.trackGenerated(it, MODEL)
        }
    }
}

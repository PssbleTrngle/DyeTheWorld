package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.extensions.createId
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.server.packs.PackType
import net.minecraftforge.common.data.ExistingFileHelper

private val EXISTING =
    listOf(
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/both_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/both"),
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/none_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/none"),
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/left_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/left"),
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/right_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/chair/right"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/both_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/both"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/none_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/none"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/left_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/left"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/right_cropped"),
        Constants.Mods.CREATE_INTERIORS.createId("block/floor_chair/right"),
    )

private val MODEL = ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".json", "models")

fun AbstractRegistrate<*>.registerExistingFiles() {
    addDataGenerator(ProviderType.BLOCKSTATE) { provider ->
        EXISTING.forEach {
            provider.models().existingFileHelper.trackGenerated(it, MODEL)
        }
    }
}

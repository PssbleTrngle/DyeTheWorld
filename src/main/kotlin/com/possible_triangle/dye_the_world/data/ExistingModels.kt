package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.extensions.createId
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.server.packs.PackType
import net.neoforged.neoforge.common.data.ExistingFileHelper

private val EXISTING = listOf(
    Constants.Mods.CREATE_SIMULATED.createId("block/symmetric_sail/block"),
    Constants.Mods.CREATE_SIMULATED.createId("block/portable_engine/block"),
    Constants.Mods.CREATE_SIMULATED.createId("block/portable_engine/item"),
    Constants.Mods.CREATE_SIMULATED.createId("block/handle/block_vertical"),
    Constants.Mods.CREATE_SIMULATED.createId("block/handle/block_horizontal"),
    Constants.Mods.CREATE_SIMULATED.createId("block/handle/item"),
    Constants.Mods.CREATE_SIMULATED.createId("block/nameplate/block_single"),
    Constants.Mods.CREATE_SIMULATED.createId("block/nameplate/block_left"),
    Constants.Mods.CREATE_SIMULATED.createId("block/nameplate/block_right"),
    Constants.Mods.CREATE_SIMULATED.createId("block/nameplate/block_middle"),
    Constants.Mods.CREATE_SIMULATED.createId("block/nameplate/item"),
    Constants.Mods.CREATE_AERONAUTICS.createId("block/envelope_encased_shaft/block"),
    Constants.Mods.CREATE_AERONAUTICS.createId("block/envelope_encased_shaft/item"),
)

private val MODEL = ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".json", "models");

fun AbstractRegistrate<*>.registerExistingFiles() {
    addDataGenerator(ProviderType.BLOCKSTATE) { provider ->
        EXISTING.forEach {
            provider.models().existingFileHelper.trackGenerated(it, MODEL)
        }
    }
}
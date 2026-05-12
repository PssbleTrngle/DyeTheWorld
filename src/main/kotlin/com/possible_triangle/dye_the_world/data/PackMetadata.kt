package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.DetectedVersion
import net.minecraft.data.metadata.PackMetadataGenerator
import net.minecraft.network.chat.Component.literal
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.pack.PackMetadataSection

fun DyedRegistrate.generatePackMetadata() {
    addDataGenerator(ProviderType.GENERIC_SERVER) { provider ->
        provider.add {
            PackMetadataGenerator(it.output)
                .add(
                    PackMetadataSection.TYPE,
                    PackMetadataSection(
                        literal("${Constants.MOD_ID} resources"),
                        DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                    ),
                )
        }
    }
}

package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.DetectedVersion
import net.minecraft.data.metadata.PackMetadataGenerator
import net.minecraft.network.chat.Component.literal
import net.minecraft.server.packs.OverlayMetadataSection
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.pack.PackMetadataSection
import net.minecraft.util.InclusiveRange
import net.neoforged.neoforge.common.conditions.ModLoadedCondition
import net.neoforged.neoforge.common.conditions.WithConditions
import net.neoforged.neoforge.common.data.GeneratingOverlayMetadataSection
import kotlin.collections.component1

fun DyedRegistrate.generatePackMetadata() {
    addDataGenerator(ProviderType.GENERIC_SERVER) { provider ->
        val version = DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES)

        provider.add {
            PackMetadataGenerator(it.output)
                .add(
                    PackMetadataSection.TYPE,
                    PackMetadataSection(
                        literal("${Constants.MOD_ID} resources"),
                        version,
                    ),
                ).add(
                    GeneratingOverlayMetadataSection.NEOFORGE_TYPE,
                    GeneratingOverlayMetadataSection(
                        DyedRegistrate.createOverlays().map { (modId, overlay) ->
                            WithConditions(
                                OverlayMetadataSection.OverlayEntry(InclusiveRange(version, version), overlay),
                                ModLoadedCondition(modId),
                            )
                        },
                    ),
                )
        }
    }
}

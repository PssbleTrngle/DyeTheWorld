package com.possible_triangle.dye_the_world.compat

import com.google.gson.JsonObject
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.namespace
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper.isModLoaded
import net.mehvahdjukaar.moonlight.api.resources.ResType
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicClientResourceProvider
import net.mehvahdjukaar.moonlight.api.resources.pack.PackGenerationStrategy
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask
import net.minecraft.resources.ResourceLocation.withDefaultNamespace
import net.minecraft.world.item.DyeColor
import java.util.function.Consumer

class SleepThighDynamicResources :
    DynamicClientResourceProvider(
        Constants.MOD_ID.createId("sleep_thigh"),
        PackGenerationStrategy.REGEN_ON_EVERY_RELOAD,
    ) {
    companion object {
        private val DYES = dyesFor(Constants.Mods.SLEEP_TIGHT)
        private val NAMESPACES = DYES.map { it.namespace }.distinct()
    }

    override fun gatherSupportedNamespaces() = NAMESPACES

    override fun regenerateDynamicAssets(executor: Consumer<ResourceGenTask>) {
        executor.accept(
            ResourceGenTask { manager, sink ->
                val whiteBed = manager.getResource(ResType.BLOCKSTATES.getPath(withDefaultNamespace("white_bed")))
                if (whiteBed.isPresent && whiteBed.get().sourcePackId() != "vanilla") return@ResourceGenTask
                if (isModLoaded("enhancedblockentities") || isModLoaded("betterbeds")) return@ResourceGenTask

                DYES.forEach { dye ->
                    val json = createBlockstateJson(dye)
                    sink.addJson(
                        dye.namespace.createId("${dye}_bed"),
                        json,
                        ResType.BLOCKSTATES,
                    )
                }
            },
        )
    }

    private fun createBlockstateJson(dye: DyeColor): JsonObject =
        JsonObject().apply {
            add(
                "variants",
                JsonObject().apply {
                    add(
                        "",
                        JsonObject().apply {
                            addProperty(
                                "model",
                                Constants.Mods.SLEEP_TIGHT
                                    .createId("block/${dye}_bed")
                                    .toString(),
                            )
                        },
                    )
                },
            )
        }

    override fun addDynamicTranslations(event: AfterLanguageLoadEvent) {
        // Nothing
    }
}

package com.possible_triangle.dye_the_world.arts_and_crafts.data

import com.kekecreations.arts_and_crafts.common.item.palette.PaintbrushPalette
import com.kekecreations.arts_and_crafts.core.registry.ACRegistries
import com.possible_triangle.dye_the_world.Constants.MOD_ID
import com.possible_triangle.dye_the_world.DyedMap
import com.possible_triangle.dye_the_world.arts_and_crafts.ARTS_AND_CRAFTS
import com.possible_triangle.dye_the_world.arts_and_crafts.DyedArtsAndCrafts
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.inverse
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateProvider
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.server.packs.PackType
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.neoforged.fml.LogicalSide
import net.neoforged.neoforge.common.conditions.ModLoadedCondition
import net.neoforged.neoforge.common.data.JsonCodecProvider
import java.util.concurrent.CompletableFuture

private class PaintPaletteProvider(
    context: ProviderType.Context<PaintPaletteProvider>,
) : JsonCodecProvider<PaintbrushPalette>(
        context.output,
        PackOutput.Target.DATA_PACK,
        DIR,
        PackType.SERVER_DATA,
        PaintbrushPalette.CODEC,
        CompletableFuture.completedFuture(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY)),
        context.parent.modid,
        context.fileHelper,
    ),
    RegistrateProvider {
    private val owner = context.parent

    companion object {
        private val DIR =
            ACRegistries.PAINTBRUSH_PALETTE
                .location()
                .toString()
                .replace(':', '/')

        val PROVIDER = ProviderType.registerProvider("Paint Palettes", ::PaintPaletteProvider)
    }

    init {
        owner.dataGenInitializer.addDependency(PROVIDER, ProviderType.DYNAMIC)
    }

    override fun gather() {
        owner.genData(PROVIDER, this)
    }

    override fun getSide() = LogicalSide.SERVER
}

fun AbstractRegistrate<*>.paintPalette(
    name: String,
    map: DyedMap<Block>,
) {
    val tag = paintableTag(name)

    addDataGenerator(PaintPaletteProvider.PROVIDER) { provider ->
        val id = MOD_ID.createId(name)

        provider.conditionally(id) { builder ->
            builder.addCondition(ModLoadedCondition(ARTS_AND_CRAFTS))
            builder.withCarrier(
                PaintbrushPalette(
                    BuiltInRegistries.BLOCK.getOrCreateTag(tag),
                    DyedArtsAndCrafts.BRUSHES.inverse().mapValues {
                        map[it.value]!!
                    },
                ),
            )
        }
    }
}

fun AbstractRegistrate<*>.paintableTag(name: String): TagKey<Block> =
    TagKey.create(Registries.BLOCK, modid.createId(name).withPrefix("paintable/"))

fun <T : BlockBuilder<*, out AbstractRegistrate<*>>> T.tagPaintable(name: String) =
    apply {
        tag(owner.paintableTag(name))
    }

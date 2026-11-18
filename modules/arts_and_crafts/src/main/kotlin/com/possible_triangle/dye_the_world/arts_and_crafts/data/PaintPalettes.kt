package com.possible_triangle.dye_the_world.arts_and_crafts.data

import com.kekecreations.arts_and_crafts.common.item.palette.PaintbrushPalette
import com.kekecreations.arts_and_crafts.core.registry.ACRegistries
import com.possible_triangle.dye_the_world.arts_and_crafts.DyedArtsAndCrafts
import com.possible_triangle.dye_the_world.extensions.inverse
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags

fun AbstractRegistrate<*>.A(id: ResourceLocation) {
    dataGenInitializer.add(ACRegistries.PAINTBRUSH_PALETTE) {
        val blocks = it.lookup(Registries.BLOCK)

        it.register(
            id, PaintbrushPalette(
            blocks.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE),
            DyedArtsAndCrafts.BRUSHES.inverse().mapValues {
                DyedArtsAndCrafts.POLISHED_SOAPSTONE.cubes[it.value]
            }
        ))
    }
}

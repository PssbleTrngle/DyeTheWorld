package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor

fun AbstractRegistrate<*>.translateBannerPattern(
    dyes: Collection<DyeColor>,
    pattern: ResourceLocation,
    translation: String,
) {
    addDataGenerator(ProviderType.LANG) { provider ->
        dyes.forEach { dye ->
            provider.add("block.${pattern.namespace}.banner.${pattern.path}.$dye", "${dye.translation} $translation")
        }
    }
}

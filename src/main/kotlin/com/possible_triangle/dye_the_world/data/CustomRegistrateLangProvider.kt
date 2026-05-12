package com.possible_triangle.dye_the_world.data

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateProvider
import com.tterrag.registrate.util.nullness.NonNullBiFunction
import net.minecraft.data.PackOutput
import net.minecraftforge.common.data.LanguageProvider
import net.minecraftforge.fml.LogicalSide

class CustomRegistrateLangProvider(
    output: PackOutput,
    private val owner: AbstractRegistrate<*>,
    private val type: ProviderType<CustomRegistrateLangProvider>,
    locale: String,
) : LanguageProvider(output, owner.modid, locale),
    RegistrateProvider {
    companion object {
        fun providerType(locale: String): ProviderType<CustomRegistrateLangProvider> =
            ProviderType.register("lang/$locale") { type ->
                NonNullBiFunction { owner, event ->
                    CustomRegistrateLangProvider(event.generator.packOutput, owner, type, locale)
                }
            }
    }

    override fun getSide() = LogicalSide.CLIENT

    override fun addTranslations() {
        owner.genData(type, this)
    }
}

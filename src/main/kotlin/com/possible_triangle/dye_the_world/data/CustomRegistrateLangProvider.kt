package com.possible_triangle.dye_the_world.data

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateProvider
import com.tterrag.registrate.util.nullness.NonNullBiFunction
import net.minecraft.data.PackOutput
import net.neoforged.fml.LogicalSide
import net.neoforged.neoforge.common.data.LanguageProvider

class CustomRegistrateLangProvider(
    output: PackOutput,
    private val owner: AbstractRegistrate<*>,
    private val type: ProviderType<CustomRegistrateLangProvider>,
    locale: String,
) : LanguageProvider(output, owner.modid, locale), RegistrateProvider {

    companion object {
        fun providerType(locale: String): ProviderType<CustomRegistrateLangProvider> {
            return ProviderType.register("lang/$locale") { type ->
                NonNullBiFunction { owner, event ->
                    CustomRegistrateLangProvider(event.generator.packOutput, owner, type, locale)
                }
            }
        }
    }

    override fun getSide() = LogicalSide.CLIENT

    override fun addTranslations() {
        owner.genData(type, this);
    }
}
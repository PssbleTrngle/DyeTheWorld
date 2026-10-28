package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CHALK
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.DE_LANG
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.Util.makeDescriptionId

object DyedChalk {
    private val REGISTRATE = DyedRegistrate.create(CHALK)

    private val DYES = dyesFor(CHALK)

    fun register() {
        DYES.forEach { dye ->
            val descriptionId = makeDescriptionId("item", CHALK.createId("${dye}_chalk"))

            REGISTRATE.addDataGenerator(ProviderType.LANG) {
                it.add(descriptionId, "${dye.translation} Chalk")
            }

            REGISTRATE.addDataGenerator(DE_LANG) {
                it.add(descriptionId, "${dye.germanTranslation(Genus.F)} Kreide")
            }
        }

        REGISTRATE.register()
    }
}

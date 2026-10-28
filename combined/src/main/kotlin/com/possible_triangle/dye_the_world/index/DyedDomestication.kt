package com.possible_triangle.dye_the_world.index

import com.evandev.redomesticate.content.block.PetBedBlock
import com.possible_triangle.dye_the_world.Constants.Mods.REDOMESTICATE
import com.possible_triangle.dye_the_world.data.petBedBlockstate
import com.possible_triangle.dye_the_world.data.petBedRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object DyedDomestication {
    private val REGISTRATE = DyedRegistrate.create(REDOMESTICATE, migrate = true)

    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, REDOMESTICATE.createId(REDOMESTICATE))

    val PET_BEDS =
        dyesFor(REDOMESTICATE).associateWith { dye ->
            REGISTRATE
                .`object`("pet_bed_$dye")
                .dyedBlock(dye) { PetBedBlock(dye.serializedName, dye) }
                .lang("${dye.translation} Pet Bed")
                .petBedBlockstate()
                .withItem {
                    tab(TAB)
                    optionalTag(DyedTags.Items.PET_BEDS)
                    petBedRecipe()
                }.register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

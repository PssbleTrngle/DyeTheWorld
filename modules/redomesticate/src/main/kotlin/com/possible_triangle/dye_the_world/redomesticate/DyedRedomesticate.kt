package com.possible_triangle.dye_the_world.redomesticate

import com.evandev.redomesticate.content.block.PetBedBlock
import com.possible_triangle.dye_the_world.DyedTags
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.redomesticate.data.petBedBlockstate
import com.possible_triangle.dye_the_world.redomesticate.data.petBedRecipe
import com.possible_triangle.dye_the_world.translation
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object DyedRedomesticate {
    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, REDOMESTICATE.createId(REDOMESTICATE))

    val PET_BEDS =
        REGISTRATE.dyed { dye ->
            `object`("pet_bed_$dye")
                .dyedBlock(dye) { PetBedBlock(dye.serializedName, dye) }
                .lang("${dye.translation} Pet Bed")
                .petBedBlockstate()
                .withItem {
                    tab(TAB)
                    optionalTag(DyedTags.ITEMS.create(REDOMESTICATE, "pet_beds"))
                    petBedRecipe()
                }.register()
        }
}

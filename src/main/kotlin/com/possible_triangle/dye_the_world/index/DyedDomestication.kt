package com.possible_triangle.dye_the_world.index

import com.evandev.redomesticate.content.block.PetBedBlock
import com.possible_triangle.dye_the_world.Constants.Mods.REDOMESTICATE
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.petBedBlockstate
import com.possible_triangle.dye_the_world.data.petBedRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.translation
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.PushReaction

object DyedDomestication {
    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, REDOMESTICATE.createId(REDOMESTICATE))

    val PET_BEDS =
        dyesFor(REDOMESTICATE).associateWith { dye ->
            REGISTRATE
                .`object`("pet_bed_$dye")
                .dyedBlock(dye, REDOMESTICATE) { PetBedBlock(dye.serializedName, dye) }
                .properties { it.strength(0.8F) }
                .properties { it.pushReaction(PushReaction.BLOCK) }
                .properties { it.noOcclusion() }
                .properties { it.sound(SoundType.WOOD) }
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Pet Bed")
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .petBedBlockstate()
                .withItem {
                    tab(TAB)
                    optionalTag(DyedTags.Items.PET_BEDS)
                    petBedRecipe()
                }.register()
        }

    fun register() {
        // Loads this class
    }
}

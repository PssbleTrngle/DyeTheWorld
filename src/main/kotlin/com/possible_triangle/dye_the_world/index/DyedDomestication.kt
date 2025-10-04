package com.possible_triangle.dye_the_world.index

import com.github.alexthe668.domesticationinnovation.server.block.PetBedBlock
import com.possible_triangle.dye_the_world.Constants.Mods.DOMESTICATION_INNOVATION
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.petBedBlockstate
import com.possible_triangle.dye_the_world.data.petBedRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.extensions.withItem
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.PushReaction

object DyedDomestication {

    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, DOMESTICATION_INNOVATION.createId(DOMESTICATION_INNOVATION))

    val PET_BEDS = dyesFor(DOMESTICATION_INNOVATION).associateWith { dye ->
        REGISTRATE.`object`("pet_bed_${dye}")
            .dyedBlock(dye, DOMESTICATION_INNOVATION) { PetBedBlock(dye.serializedName, dye) }
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
            }
            .register()
    }


    fun register() {
        // Loads this class
    }

}
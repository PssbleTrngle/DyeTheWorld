package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.MORE_CONCRETE
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.germanTranslation
import net.minecraft.tags.BlockTags

object DyedConcrete {
    private val CONCRETE = dyedBlockMap(MORE_CONCRETE, "concrete")

    val CONCRETE_SLABS =
        REGISTRATE.createSlabs(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Betonstufe")
        }, existingTexture = true)

    val CONCRETE_STAIRS =
        REGISTRATE.createStairs(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Betontreppe")
        }, existingTexture = true)

    val CONCRETE_WALLS =
        REGISTRATE.createWalls(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Betonmaur")
        }, existingTexture = true)

    val CONCRETE_FENCES =
        REGISTRATE.createFences(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.M)} Betonzaun")
            optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        }, existingTexture = true)

    val CONCRETE_FENCE_GATES =
        REGISTRATE.createFenceGates(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.M)} Betonzauntor")
            optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        }, existingTexture = true)

    val CONCRETE_BUTTONS =
        REGISTRATE.createButtons(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.M)} Betonknopf")
            optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        }, existingTexture = true)

    val CONCRETE_LEVERS =
        REGISTRATE.createLevers(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.M)} Betonhebel")
            optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        }, existingTexture = true)

    val CONCRETE_PRESSURE_PLATES =
        REGISTRATE.createPressurePlates(CONCRETE, MORE_CONCRETE.createId("concrete"), modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.M)} Betondruckplatte")
            optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        }, existingTexture = true)

    fun register() {
        // Loads this class
    }
}

package com.possible_triangle.dye_the_world.more_concrete

import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.more_concrete.data.createButtons
import com.possible_triangle.dye_the_world.more_concrete.data.createLevers
import com.possible_triangle.dye_the_world.more_concrete.data.createPressurePlates
import net.minecraft.tags.BlockTags

object DyedMoreConcrete {
    private val CONCRETE = dyedBlockMap(MORE_CONCRETE, "concrete")

    val CONCRETE_SLABS =
        REGISTRATE.createSlabs(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
                germanLang("${dye.germanTranslation(Genus.F)} Betonstufe")
            },
        )

    val CONCRETE_STAIRS =
        REGISTRATE.createStairs(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
                germanLang("${dye.germanTranslation(Genus.F)} Betontreppe")
            },
        )

    val CONCRETE_WALLS =
        REGISTRATE.createWalls(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
                germanLang("${dye.germanTranslation(Genus.F)} Betonmauer")
            },
        )

    val CONCRETE_FENCES =
        REGISTRATE.createFences(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.M)} Betonzaun")
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
            },
        )

    val CONCRETE_FENCE_GATES =
        REGISTRATE.createFenceGates(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.M)} Betonzauntor")
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
            },
        )

    val CONCRETE_BUTTONS =
        REGISTRATE.createButtons(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.M)} Betonknopf")
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
            },
        )

    val CONCRETE_LEVERS =
        REGISTRATE.createLevers(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.M)} Betonhebel")
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
            },
        )

    val CONCRETE_PRESSURE_PLATES =
        REGISTRATE.createPressurePlates(
            CONCRETE,
            MORE_CONCRETE.createId("concrete"),
            modifyBlock = { dye ->
                germanLang("${dye.germanTranslation(Genus.M)} Betondruckplatte")
                tag(BlockTags.MINEABLE_WITH_PICKAXE)
            },
        )
}

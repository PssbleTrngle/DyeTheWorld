package com.possible_triangle.dye_the_world.index

import com.ninni.twigs.TwigsTags
import com.ninni.twigs.block.enums.SiltPotBlock
import com.ninni.twigs.registry.TwigsBlockEntityType
import com.ninni.twigs.registry.TwigsBlocks
import com.ninni.twigs.registry.TwigsItems
import com.possible_triangle.dye_the_world.Constants.Mods.TWIGS
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.colorSet
import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.data.createWalls
import com.possible_triangle.dye_the_world.data.cubeBlockstate
import com.possible_triangle.dye_the_world.data.siltPotBlockstate
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.existingBlockEntity
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.world.item.CreativeModeTabs

object DyedTwigs {
    private val DYES = dyesFor(TWIGS)

    val SILT_SHINGLES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_silt_shingles")
                .dyedBlock(dye, TWIGS)
                .initialProperties(NonNullSupplier(TwigsBlocks.SILT_SHINGLES::get))
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Silt Shingles")
                .cubeBlockstate("silt_shingle")
                .optionalTag(TwigsTags.PACKED_SILT_BLOCK)
                .withItem {
                    tab(CreativeModeTabs.BUILDING_BLOCKS)
                    optionalTag(TwigsTags.PACKED_SILT_ITEM)
                    recipe { c, p ->
                        p.shapedDyeingRecipe(dye, TwigsItems.SILT_SHINGLES.get(), c)
                    }
                }.register()
        }

    val SILT_SHINGLES_SLABS =
        REGISTRATE.createSlabs(
            SILT_SHINGLES,
            TWIGS.createId("silt_shingle"),
            modifyBlock = { optionalTag(TwigsTags.PACKED_SILT_BLOCK) },
            modifyItem = { optionalTag(TwigsTags.PACKED_SILT_ITEM) },
        )

    val SILT_SHINGLES_STAIRS =
        REGISTRATE.createStairs(
            SILT_SHINGLES,
            TWIGS.createId("silt_shingle"),
            modifyBlock = { optionalTag(TwigsTags.PACKED_SILT_BLOCK) },
            modifyItem = { optionalTag(TwigsTags.PACKED_SILT_ITEM) },
        )

    val SILT_SHINGLES_WALL =
        REGISTRATE.createWalls(
            SILT_SHINGLES,
            TWIGS.createId("silt_shingle"),
            modifyBlock = { optionalTag(TwigsTags.PACKED_SILT_BLOCK) },
            modifyItem = { optionalTag(TwigsTags.PACKED_SILT_ITEM) },
        )

    val PACKED_SILT =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_packed_silt")
                .dyedBlock(dye, TWIGS)
                .initialProperties(NonNullSupplier(TwigsBlocks.PACKED_SILT::get))
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Packed Silt")
                .cubeBlockstate("packed_silt")
                .optionalTag(TwigsTags.PACKED_SILT_BLOCK)
                .withItem {
                    tab(CreativeModeTabs.BUILDING_BLOCKS)
                    optionalTag(TwigsTags.PACKED_SILT_ITEM)
                    recipe { c, p ->
                        p.shapedDyeingRecipe(dye, TwigsBlocks.PACKED_SILT.get(), c)
                    }
                }.register()
        }

    val SILT_POTS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_silt_pot")
                .dyedBlock(dye, TWIGS, ::SiltPotBlock)
                .initialProperties(NonNullSupplier(TwigsBlocks.SILT_POT::get))
                .properties { it.mapColor(dye) }
                .lang("${dye.translation} Silt Pot")
                .siltPotBlockstate()
                .optionalTag(TwigsTags.SILT_POTS_BLOCK)
                .existingBlockEntity { TwigsBlockEntityType.SILT_POT.get() }
                .withItem {
                    tab(CreativeModeTabs.BUILDING_BLOCKS)
                    optionalTag(TwigsTags.SILT_POTS_ITEM)
                    recipe { c, p ->
                        p.dyeingRecipe(dye, TwigsBlocks.SILT_POT.get(), c)
                    }
                }.register()
        }

    fun register() {
        REGISTRATE.colorSet(TWIGS.createId("silt_pot"))
    }
}

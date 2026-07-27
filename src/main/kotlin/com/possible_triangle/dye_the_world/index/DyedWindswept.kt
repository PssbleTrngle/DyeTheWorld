package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.WINDSWEPT
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.data.cubeBlockstate
import com.possible_triangle.dye_the_world.data.translateBannerPattern
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.rosemods.windswept.core.other.tags.WindsweptBlockTags
import com.rosemods.windswept.core.registry.WindsweptBlocks
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs

object DyedWindswept {
    private val DYES = dyesFor(WINDSWEPT)

    private val PINECONE_SHINGLES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_pinecone_shingles")
                .dyedBlock(dye, WINDSWEPT)
                .cubeBlockstate("pinecone_shingle")
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .optionalTag(WindsweptBlockTags.PINECONE_NOTE_BLOCKS)
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    recipe { context, provider ->
                        provider.shapedDyeingRecipe(dye, WindsweptBlocks.PINECONE_SHINGLES.get(), context)
                    }
                }.register()
        }

    private val PINECONE_SHINGLE_STAIRS =
        REGISTRATE.createStairs(PINECONE_SHINGLES, WINDSWEPT.createId("pinecone_shingle"), modifyBlock = {
            optionalTag(BlockTags.MINEABLE_WITH_AXE)
        }, stone = false)

    private val PINECONE_SHINGLE_SLABS =
        REGISTRATE.createSlabs(PINECONE_SHINGLES, WINDSWEPT.createId("pinecone_shingle"), modifyBlock = {
            optionalTag(BlockTags.MINEABLE_WITH_AXE)
        }, stone = false)

    fun register() {
        REGISTRATE.translateBannerPattern(DYES, ResourceLocation("$WINDSWEPT.snow_charge"), "Snow Charge")
        REGISTRATE.translateBannerPattern(DYES, ResourceLocation("$WINDSWEPT.snow_golem"), "Snow Golem")
        REGISTRATE.translateBannerPattern(DYES, ResourceLocation("$WINDSWEPT.rose_flower"), "Rose Flower")
    }
}

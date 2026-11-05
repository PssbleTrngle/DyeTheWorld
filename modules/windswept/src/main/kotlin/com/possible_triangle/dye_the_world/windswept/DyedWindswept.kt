package com.possible_triangle.dye_the_world.windswept

import com.possible_triangle.dye_the_world.data.createSlabs
import com.possible_triangle.dye_the_world.data.createStairs
import com.possible_triangle.dye_the_world.data.translateBannerPattern
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.rosemods.windswept.core.other.tags.WindsweptBlockTags
import com.rosemods.windswept.core.registry.WindsweptBlocks
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs

object DyedWindswept {
    private val PINECONE_SHINGLES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_pinecone_shingles")
                .dyedBlock(dye)
                .tag(BlockTags.MINEABLE_WITH_AXE)
                .tag(WindsweptBlockTags.PINECONE_NOTE_BLOCKS)
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    recipe { context, provider ->
                        provider.shapedDyeingRecipe(dye, WindsweptBlocks.PINECONE_SHINGLES.get(), context)
                    }
                }.register()
        }

    private val PINECONE_SHINGLE_STAIRS =
        REGISTRATE.createStairs(PINECONE_SHINGLES, WINDSWEPT.createId("pinecone_shingle"), modifyBlock = {
            tag(BlockTags.MINEABLE_WITH_AXE)
        }, stone = false)

    private val PINECONE_SHINGLE_SLABS =
        REGISTRATE.createSlabs(PINECONE_SHINGLES, WINDSWEPT.createId("pinecone_shingle"), modifyBlock = {
            tag(BlockTags.MINEABLE_WITH_AXE)
        }, stone = false)

    init {
        REGISTRATE.translateBannerPattern(WINDSWEPT.createId("snow_charge"), "Snow Charge")
        REGISTRATE.translateBannerPattern(WINDSWEPT.createId("snow_golem"), "Snow Golem")
        REGISTRATE.translateBannerPattern(WINDSWEPT.createId("rose_flower"), "Rose Flower")
    }
}

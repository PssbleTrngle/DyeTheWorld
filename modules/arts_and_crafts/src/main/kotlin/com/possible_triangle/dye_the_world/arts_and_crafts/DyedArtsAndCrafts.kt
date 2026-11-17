package com.possible_triangle.dye_the_world.arts_and_crafts

import com.kekecreations.arts_and_crafts.core.registry.ACBlocks
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.quadTransform
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.dye_the_world.translation
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks

object DyedArtsAndCrafts {

    init {
        REGISTRATE.defaultCreativeTab(
            ResourceKey.create(
                Registries.CREATIVE_MODE_TAB,
                ARTS_AND_CRAFTS.createId("configurable_tab"),
            )
        )
    }

    val SOAPSTONE =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_soapstone")
                .dyedBlock(dye)
                .initialProperties { dye.blockOf("terracotta") }
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .lang("${dye.translation} Soapstone")
                // TODO paintable tags
                .withItem {
                    recipe { c, p ->
                        p.shapedDyeingRecipe(dye, ACBlocks.SOAPSTONE.get(), c)
                    }
                }.register()
        }

    val POLISHED_SOAPSTONE =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_polished_soapstone")
                .dyedBlock(dye)
                .initialProperties { SOAPSTONE[dye]!!.value() }
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .lang("${dye.translation} Polished Soapstone")
                // TODO paintable tags
                .withItem {
                    recipe { c, p ->
                        p.quadTransform(SOAPSTONE[dye]!!.get(), c)
                        p.shapedDyeingRecipe(dye, ACBlocks.POLISHED_SOAPSTONE.get(), c)
                    }
                }.register()
        }

    val SOAPSTONE_BRICKS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_soapstone_bricks")
                .dyedBlock(dye)
                .initialProperties { SOAPSTONE[dye]!!.value() }
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .lang("${dye.translation} Soapstone Bricks")
                // TODO paintable tags
                .withItem {
                    recipe { c, p ->
                        p.quadTransform(POLISHED_SOAPSTONE[dye]!!.get(), c)
                        p.shapedDyeingRecipe(dye, ACBlocks.SOAPSTONE_BRICKS.get(), c)
                    }
                }.register()
        }

    val MUD_BRICKS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_mud_bricks")
                .dyedBlock(dye)
                .initialProperties { Blocks.MUD }
                .properties { it.mapColor(dye) }
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .lang("${dye.translation} Mud Bricks")
                // TODO paintable tags
                .withItem {
                    recipe { c, p ->
                        p.shapedDyeingRecipe(dye, Blocks.MUD_BRICKS, c)
                    }
                }.register()
        }

    val CHALK =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_chalk")
                .dyedBlock(dye)
                .initialProperties { ACBlocks.CHALK[0]!!.get() }
                .properties { it.mapColor(dye) }
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .lang("${dye.translation} Chalk")
                // TODO paintable tags
                .withItem {
                    recipe { c, p ->
                        p.shapedDyeingRecipe(dye, ACBlocks.CHALK[0]!!.get(), c)
                    }
                }.register()
        }

    val TERRACOTTA_SHINGLES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_terracotta_shingles")
                .dyedBlock(dye)
                .initialProperties { ACBlocks.TERRACOTTA_SHINGLES.get() }
                .properties { it.mapColor(dye) }
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .lang("${dye.translation} Terracotta Shingles")
                // TODO paintable tags
                .withItem {
                    recipe { c, p ->
                        p.quadTransform(ACBlocks.TERRACOTTA_SHINGLES.get(), c)
                        p.shapedDyeingRecipe(dye, ACBlocks.TERRACOTTA_SHINGLES.get(), c)
                    }
                }.register()
        }
}

package com.possible_triangle.dye_the_world.index

import com.kipti.bnb.content.trinkets.chair.ChairBlock
import com.kipti.bnb.content.trinkets.chair.ChairBlockStateGen
import com.kipti.bnb.content.trinkets.nixie.large_nixie_tube.LargeNixieTubeBlockNixie
import com.kipti.bnb.content.trinkets.nixie.large_nixie_tube.LargeNixieTubeBlockStateGen
import com.kipti.bnb.content.trinkets.nixie.nixie_board.NixieBoardBlockNixie
import com.kipti.bnb.content.trinkets.nixie.nixie_board.NixieBoardBlockStateGen
import com.kipti.bnb.registry.content.blocks.BnbTrinketBlocks
import com.kipti.bnb.registry.core.BnbTags
import com.kipti.bnb.registry.core.BnbTags.BnbBlockTags
import com.possible_triangle.dye_the_world.Constants.Mods.BITS_N_BOBS
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.translation
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags

object DyedBits {
    val DYES = dyesFor(BITS_N_BOBS)

    val REGISTRATE = DyedRegistrate.create(BITS_N_BOBS)

    val CHAIRS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_chair")
                .dyedBlock(dye) { ChairBlock(it, dye) }
                .lang("${dye.translation} Chair")
                .germanLang("${dye.germanTranslation(Genus.M)} Stuhl")
                .optionalTag(BnbBlockTags.CHAIRS.tag)
                .optionalTag(BlockTags.MINEABLE_WITH_AXE)
                .blockstate(ChairBlockStateGen.dyedChair(dye.serializedName))
                .withItem {
                    optionalTag(BnbTags.BnbItemTags.CHAIRS.tag)
                    model { c, p ->
                        p
                            .withExistingParent(c.name, BITS_N_BOBS.createId("block/chair/item"))
                            .texture("2", BITS_N_BOBS.createId("block/chair/chair_$dye"))
                    }
                }.recipe { c, p ->
                    ShapelessRecipeBuilder
                        .shapeless(RecipeCategory.MISC, c.get())
                        .requiresUnlocking(dye.blockOf("wool"))
                        .requires(ItemTags.WOODEN_STAIRS)
                        .save(p)

                    p.dyeingRecipe(dye, BnbTags.BnbItemTags.CHAIRS.tag, c)
                }.register()
        }

    val NIXIE_BOARDS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_nixie_board")
                .dyedBlock(dye) { NixieBoardBlockNixie(it, dye) }
                .lang("${dye.translation} Nixie Board")
                .germanLang("${dye.germanTranslation(Genus.I)} Nixie-Board")
                .optionalTag(BnbBlockTags.NIXIE_BOARDS.tag)
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate(NixieBoardBlockStateGen::nixieBoard)
                .loot { p, b -> p.dropOther(b, BnbTrinketBlocks.NIXIE_BOARD) }
                .register()
        }

    val LARGE_NIXIE_TUBES =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_large_nixie_tube")
                .dyedBlock(dye) { LargeNixieTubeBlockNixie(it, dye) }
                .lang("${dye.translation} Large Nixie Tube")
                .germanLang("${dye.germanTranslation(Genus.F)} große Nixie-Röhre")
                .optionalTag(BnbBlockTags.NIXIE_BOARDS.tag)
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate(LargeNixieTubeBlockStateGen::nixieTube)
                .loot { p, b -> p.dropOther(b, BnbTrinketBlocks.LARGE_NIXIE_TUBE) }
                .register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

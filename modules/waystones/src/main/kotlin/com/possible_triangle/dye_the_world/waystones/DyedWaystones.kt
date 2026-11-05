package com.possible_triangle.dye_the_world.waystones

import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.waystones.data.waystoneBlockstate
import com.possible_triangle.dye_the_world.waystones.data.waystoneLoot
import com.tterrag.registrate.providers.ProviderType
import net.blay09.mods.waystones.block.PortstoneBlock
import net.blay09.mods.waystones.block.SharestoneBlock
import net.blay09.mods.waystones.item.ModItems
import net.blay09.mods.waystones.tag.ModBlockTags
import net.blay09.mods.waystones.tag.ModItemTags
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

object DyedWaystones {
    val PORTSTONES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_portstone")
                .dyedBlock(dye) { PortstoneBlock(dye, it) }
                .lang("${dye.translation} Portstone")
                .germanLang("${dye.germanTranslation(Genus.M)} Portstein")
                .tag(ModBlockTags.PORTSTONES)
                .tag(ModBlockTags.IS_TELEPORT_TARGET)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate { context, provider ->
                    val upper = provider.models().getExistingFile(WAYSTONES.createId("block/portstone_top"))
                    val bottom = provider.models().getExistingFile(WAYSTONES.createId("block/portstone_bottom"))

                    provider.createVariant(context) { state ->
                        val facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                        val half = state.getValue(PortstoneBlock.HALF)

                        val model = if (half == DoubleBlockHalf.UPPER) upper else bottom

                        ConfiguredModel
                            .builder()
                            .modelFile(model)
                            .rotationY(facing.yRot)
                    }
                }.waystoneBlockstate("portstone")
                .waystoneLoot()
                .withItem {
                    recipe { c, p ->
                        ShapedRecipeBuilder
                            .shaped(RecipeCategory.MISC, c.get())
                            .pattern("DSD")
                            .pattern("SWS")
                            .pattern("BBB")
                            .define('D', dye.tag)
                            .define('S', Blocks.STONE_BRICKS)
                            .define('B', Blocks.POLISHED_ANDESITE)
                            .defineUnlocking('W', ModItems.warpStone)
                            .save(p)
                    }
                    model { context, provider ->
                        provider.withExistingParent(context.name, WAYSTONES.createId("item/portstone"))
                    }
                }.register()
        }

    val SHARESTONES =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_sharestone")
                .dyedBlock(dye) { SharestoneBlock(dye, it) }
                .lang("${dye.translation} Sharestone")
                .germanLang("${dye.germanTranslation(Genus.M)} Teilstein")
                .addMiscData(ProviderType.LANG) {
                    it.add(
                        "tooltip.$WAYSTONES.${dye}_sharestone",
                        "Teleport to any other ${dye.translation} Sharestone",
                    )
                }.addMiscData(DE_LANG) {
                    it.add(
                        "tooltip.$WAYSTONES.${dye}_sharestone",
                        "Teleportiere zu jedem anderen ${dye.germanTranslation(Genus.M)} Teilstein",
                    )
                }.tag(ModBlockTags.SHARESTONES)
                .tag(ModBlockTags.IS_TELEPORT_TARGET)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .waystoneLoot()
                .waystoneBlockstate("sharestone")
                .withItem {
                    tag(ModItemTags.SHARESTONES)
                    recipe { c, p ->
                        ShapedRecipeBuilder
                            .shaped(RecipeCategory.MISC, c.get())
                            .pattern("SSS")
                            .pattern("DWD")
                            .pattern("OOO")
                            .define('D', dye.tag)
                            .define('O', Blocks.OBSIDIAN)
                            .define('S', Blocks.STONE_BRICKS)
                            .defineUnlocking('W', ModItems.warpStone)
                            .save(p)
                    }
                    model { context, provider ->
                        provider.withExistingParent(context.name, WAYSTONES.createId("item/sharestone"))
                    }
                }.register()
        }
}

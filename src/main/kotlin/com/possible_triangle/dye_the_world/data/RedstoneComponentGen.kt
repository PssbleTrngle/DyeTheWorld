package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.MORE_CONCRETE
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.ButtonBlock
import net.minecraft.world.level.block.LeverBlock
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.state.properties.AttachFace
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.neoforged.neoforge.client.model.generators.ConfiguredModel

fun DyedRegistrate.createLevers(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<LeverBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<LeverBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_lever")
        .dyedBlock(dye, name.namespace, ::LeverBlock)
        .initialProperties(base)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name.path}")

            // on and off being switched here is weird but correctly, it's also how it is for the vanilla lever ¯\_(ツ)_/¯
            val off = p.models().withExistingParent("${c.name}_on", MORE_CONCRETE.createId("block/lever_model_on"))
                .texture("base", texture)
            val on = p.models().withExistingParent(c.name, MORE_CONCRETE.createId("block/lever_model"))
                .texture("base", texture)


            p.createVariant(c) { state ->
                val facing = state.getValue(LeverBlock.FACING)
                val face = state.getValue(LeverBlock.FACE)
                val powered = state.getValue(LeverBlock.POWERED)

                val model = if (powered) on else off

                val rotX = when (face) {
                    AttachFace.FLOOR -> 0
                    AttachFace.WALL -> 90
                    AttachFace.CEILING -> 180
                }

                ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationX(rotX)
                    .rotationY((if (face == AttachFace.CEILING) facing.opposite else facing).yRot)
            }
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.REDSTONE_BLOCKS)
            recipe { c, p ->
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, c.get())
                    .pattern("|")
                    .pattern("#")
                    .define('|', Items.STICK)
                    .defineUnlocking('#', base.get())
                    .group("concrete_lever")
                    .save(p)
            }
            model { c, p -> p.blockItem(c) }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}

fun DyedRegistrate.createButtons(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<ButtonBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<ButtonBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_button")
        .dyedBlock(dye, name.namespace) { ButtonBlock(BlockSetType.STONE, 20, it) }
        .initialProperties(base)
        .optionalTag(BlockTags.BUTTONS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name.path}")
            p.buttonBlock(c.get(), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.REDSTONE_BLOCKS)
            optionalTag(ItemTags.BUTTONS)
            recipe { c, p ->
                p.singleItemUnfinished(base.asIngredient(), RecipeCategory.REDSTONE, c, 1, 1)
                    .group("concrete_button")
                    .save(p)
            }
            model { c, p ->
                val texture = dye.namespace.createId("block/${dye}_${name.path}")
                p.buttonInventory(c.name, texture)
            }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}

fun DyedRegistrate.createPressurePlates(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<PressurePlateBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<PressurePlateBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_pressure_plate")
        .dyedBlock(dye, name.namespace) { PressurePlateBlock(BlockSetType.STONE, it) }
        .initialProperties(base)
        .optionalTag(BlockTags.PRESSURE_PLATES)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name.path}")
            p.pressurePlateBlock(c.get(), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.REDSTONE_BLOCKS)
            recipe { c, p ->
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, c.get())
                    .pattern("##")
                    .defineUnlocking('#', base.get())
                    .group("concrete_pressure_plate")
                    .save(p)
            }
            model { c, p ->
                val texture = dye.namespace.createId("block/${dye}_${name.path}")
                p.pressurePlate(c.name, texture)
            }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}
package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.MOD_ID
import com.possible_triangle.dye_the_world.Constants.Mods
import com.possible_triangle.dye_the_world.Constants.Mods.CLAYWORKS
import com.possible_triangle.dye_the_world.extensions.TRANSLUCENT
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.getOrThrow
import com.possible_triangle.dye_the_world.extensions.translucent
import com.possible_triangle.dye_the_world.itemOf
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.DataIngredient
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.TrapDoorBlock

fun <T : Item, P> ItemBuilder<T, P>.glassDoorRecipes() =
    recipe { context, provider ->
        provider.door(
            DataIngredient.items(dye.itemOf("stained_glass")),
            RecipeCategory.REDSTONE,
            context,
            "stained_glass_door",
        )

        val glassTrapdoor = BuiltInRegistries.BLOCK.getOrThrow(CLAYWORKS.createId("glass_door"))
        provider.dyeingRecipe(dye, glassTrapdoor, context) {
            group("stained_glass_door")
        }
    }

fun <T : DoorBlock, P> BlockBuilder<T, P>.glassDoorBlockstate() =
    blockstate { context, provider ->
        fun texture(suffix: String) = MOD_ID.createId("block/$CLAYWORKS/${dye}_stained_glass_door_$suffix")
        provider.doorBlockWithRenderType(context.get(), texture("bottom"), texture("top"), TRANSLUCENT)
    }

fun <T : Block, P> BlockBuilder<T, P>.glassDoorLoot() =
    loot { provider, block ->
        provider.dropWhenSilkTouch(block)
    }

fun <T : Item, P> ItemBuilder<T, P>.glassDoorItemModel() =
    model { context, provider ->
        val texture = MOD_ID.createId("item/$CLAYWORKS/${dye}_stained_glass_door")
        provider.generated(context, texture).translucent()
    }

fun <T : Item, P> ItemBuilder<T, P>.glassTrapdoorRecipes() =
    recipe { context, provider ->
        provider.square(
            DataIngredient.items(dye.itemOf("stained_glass")),
            RecipeCategory.REDSTONE,
            context,
            true,
        )

        val glassTrapdoor = BuiltInRegistries.BLOCK.getOrThrow(CLAYWORKS.createId("glass_trapdoor"))
        provider.dyeingRecipe(dye, glassTrapdoor, context) {
            group("stained_glass_trapdoor")
        }
    }

fun <T : TrapDoorBlock, P> BlockBuilder<T, P>.glassTrapdoorBlockstate() =
    blockstate { context, provider ->
        val texture = Mods.DYE_DEPOT.createId("block/${dye}_stained_glass")
        provider.trapdoorBlockWithRenderType(context.get(), texture, true, TRANSLUCENT)
    }

fun <T : Block, P> BlockBuilder<T, P>.glassTrapdoorLoot() =
    loot { provider, block ->
        provider.dropWhenSilkTouch(block)
    }

fun <T : Item, P> ItemBuilder<T, P>.glassTrapdoorItemModel() =
    model { context, provider ->
        provider.withExistingParent(context.name, context.id.withPrefix("block/").withSuffix("_bottom")).translucent()
    }

package com.possible_triangle.dye_the_world.arts_and_crafts.data

import com.possible_triangle.dye_the_world.data.BlockModifier
import com.possible_triangle.dye_the_world.registrate.additionalRecipe
import com.possible_triangle.dye_the_world.registrate.dye
import com.possible_triangle.dye_the_world.registrate.dyeingRecipe
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import net.minecraft.world.level.block.Block
import java.util.function.Supplier

internal fun <T : Block> shapedDyeing(from: Supplier<out Block>): BlockModifier<T> =
    {
        additionalRecipe { provider, context ->
            provider.shapedDyeingRecipe(dye, from.get(), context)
        }
    }

internal fun <T : Block> shapelessDyeing(from: Supplier<out Block>): BlockModifier<T> =
    {
        additionalRecipe { provider, context ->
            provider.dyeingRecipe(dye, from.get(), context)
        }
    }

package com.possible_triangle.dye_the_world.index

import com.crispytwig.naturalist.registry.NaturalistRegistry
import com.possible_triangle.dye_the_world.Constants.Mods.NATURALIST
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.requiresUnlocking
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import net.minecraft.core.component.DataComponents
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData

object DyedNaturalist {
    private val DYES = dyesFor(NATURALIST)

    private val REGISTRATE = DyedRegistrate.create(NATURALIST)

    val SNAIL_SHELLS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("snail_shell_$dye")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} Snail Shell")
                .recipe { context, provider ->
                    val nbt =
                        CompoundTag().apply {
                            putInt("Color", dye.id)
                        }
                    val dyedStack =
                        ItemStack(NaturalistRegistry.SNAIL_SHELL.get()).apply {
                            set(DataComponents.CUSTOM_DATA, CustomData.of(nbt))
                        }
                    ShapelessRecipeBuilder
                        .shapeless(RecipeCategory.MISC, dyedStack)
                        .requiresUnlocking(NaturalistRegistry.SNAIL_SHELL.get())
                        .requires(dye.tag)
                        .save(provider, context.id)
                }.register()
        }

    val SNAILS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("snail_$dye")
                .dyedItem(dye, ::Item)
                .lang("${dye.translation} Snail")
                .register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

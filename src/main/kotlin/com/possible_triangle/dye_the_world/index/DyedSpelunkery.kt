package com.possible_triangle.dye_the_world.index

import com.ordana.spelunkery.blocks.GlowstickBlock
import com.ordana.spelunkery.entities.ThrownGlowstickEntity
import com.ordana.spelunkery.items.GlowstickItem
import com.ordana.spelunkery.reg.ModBlocks
import com.ordana.spelunkery.reg.ModTags
import com.possible_triangle.dye_the_world.Constants.Mods.SPELUNKERY
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.glowstickBlockState
import com.possible_triangle.dye_the_world.data.glowstickItemModel
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.shapedDyeingRecipe
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.world.item.CreativeModeTabs

object DyedSpelunkery {
    val GLOW_STICKS =
        dyesFor(SPELUNKERY).associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_glowstick")
                .dyedBlock(dye, SPELUNKERY) { GlowstickBlock(it) }
                .initialProperties(NonNullSupplier(ModBlocks.GLOWSTICK::get))
                .lang("${dye.translation} Glowstick")
                .glowstickBlockState()
                .withItem({ block, it -> GlowstickItem(dye, block, it) }) {
                    tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                    optionalTag(ModTags.GLOWSTICKS)
                    glowstickItemModel()
                    recipe { c, p ->
                        p.shapedDyeingRecipe(dye, ModTags.GLOWSTICKS, c) {
                            group("glowsticks")
                        }
                    }
                }.onRegister {
                    ThrownGlowstickEntity.DYE_COLOR_TO_BLOCK[dye] = it
                }.register()
        }

    fun register() {
        // Loads this class
    }
}

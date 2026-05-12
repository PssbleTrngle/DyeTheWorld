package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.SNOWY_SPIRIT
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import net.mehvahdjukaar.snowyspirit.common.block.GlowLightsBlock
import net.mehvahdjukaar.snowyspirit.common.block.GumdropButton
import net.mehvahdjukaar.snowyspirit.common.items.GlowLightsItem
import net.minecraft.world.item.CreativeModeTabs

object DyedSnowySpirit {
    private val REGISTRATE = DyedRegistrate.create(SNOWY_SPIRIT)
    private val DYES = dyesFor(SNOWY_SPIRIT)

    val GLOW_LIGHTS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("glow_lights_$dye")
                .dyedBlock(dye) { GlowLightsBlock(dye) }
                .glowLightsBlockstate()
                .optionalTag(DyedTags.Blocks.GLOW_LIGHTS)
                .lang("${dye.translation} Glow Lights")
                .register()
        }

    val GLOW_LIGHTS_ITEMS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("glow_lights_$dye")
                .dyedItem(dye) { GlowLightsItem(GLOW_LIGHTS[dye]!!.get()) }
                .lang("${dye.translation} Glow Lights")
                .tab(CreativeModeTabs.COLORED_BLOCKS)
                .tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .glowLightsModel()
                .glowLightsRecipe()
                .optionalTag(DyedTags.Items.GLOW_LIGHTS)
                .register()
        }

    val GUMDROPS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("gumdrop_$dye")
                .dyedBlock(dye) { GumdropButton(dye) }
                .lang("${dye.translation} Gumdrop")
                .gumdropBlockstate()
                .optionalTag(DyedTags.Blocks.GUMDROPS)
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                    optionalTag(DyedTags.Items.GUMDROPS)
                    gumdropModel()
                    gumdropRecipe()
                }.register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

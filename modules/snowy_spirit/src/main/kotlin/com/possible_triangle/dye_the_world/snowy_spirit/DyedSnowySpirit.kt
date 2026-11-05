package com.possible_triangle.dye_the_world.snowy_spirit

import com.possible_triangle.dye_the_world.extensions.noLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.snowy_spirit.data.glowLightsBlockstate
import com.possible_triangle.dye_the_world.snowy_spirit.data.glowLightsRecipe
import com.possible_triangle.dye_the_world.snowy_spirit.data.gumdropBlockstate
import com.possible_triangle.dye_the_world.snowy_spirit.data.gumdropModel
import com.possible_triangle.dye_the_world.snowy_spirit.data.gumdropRecipe
import com.possible_triangle.dye_the_world.translation
import net.mehvahdjukaar.snowyspirit.common.block.GlowLightsBlock
import net.mehvahdjukaar.snowyspirit.common.block.GumdropButton
import net.mehvahdjukaar.snowyspirit.common.items.GlowLightsItem
import net.minecraft.world.item.CreativeModeTabs

object DyedSnowySpirit {
    val GLOW_LIGHTS =
        REGISTRATE.dyed { dye ->
            `object`("glow_lights_$dye")
                .dyedBlock(dye) { GlowLightsBlock(dye) }
                .glowLightsBlockstate()
                .optionalTag(SnowySpiritTags.Blocks.GLOW_LIGHTS)
                .lang("${dye.translation} Glow Lights")
                .register()
        }

    val GLOW_LIGHTS_ITEMS =
        REGISTRATE.dyed { dye ->
            `object`("glow_lights_$dye")
                .dyedItem(dye) { GlowLightsItem(GLOW_LIGHTS[dye]!!.get()) }
                .noLang()
                .tab(CreativeModeTabs.COLORED_BLOCKS)
                .tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .glowLightsRecipe()
                .optionalTag(SnowySpiritTags.Items.GLOW_LIGHTS)
                .register()
        }

    val GUMDROPS =
        REGISTRATE.dyed { dye ->
            `object`("gumdrop_$dye")
                .dyedBlock(dye) { GumdropButton(it, dye) }
                .lang("${dye.translation} Gumdrop")
                .gumdropBlockstate()
                .optionalTag(SnowySpiritTags.Blocks.GUMDROPS)
                .withItem {
                    tab(CreativeModeTabs.COLORED_BLOCKS)
                    tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                    optionalTag(SnowySpiritTags.Items.GUMDROPS)
                    gumdropModel()
                    gumdropRecipe()
                }.register()
        }
}

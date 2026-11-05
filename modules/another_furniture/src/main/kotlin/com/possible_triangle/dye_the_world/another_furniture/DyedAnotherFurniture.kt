package com.possible_triangle.dye_the_world.another_furniture

import com.possible_triangle.dye_the_world.another_furniture.data.*
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.noLang
import com.possible_triangle.dye_the_world.extensions.withItem
import com.starfish_studios.another_furniture.block.*
import com.starfish_studios.another_furniture.registry.AFBlockTags
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.starfish_studios.another_furniture.registry.AFItemTags
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object DyedAnotherFurniture {
    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ANOTHER_FURNITURE.createId(ANOTHER_FURNITURE))

    val SOFAS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_sofa")
                .dyedBlock(dye, ::SofaBlock)
                .initialProperties { AFBlocks.RED_SOFA.get() }
                .tag(AFBlockTags.SOFAS)
                .sofaBlockstate()
                .withItem {
                    sofaRecipes()
                    tag(AFItemTags.SOFAS)
                    tab(TAB)
                }.register()
        }

    val STOOLS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_stool")
                .dyedBlock(dye, ::StoolBlock)
                .initialProperties { AFBlocks.RED_STOOL.get() }
                .tag(AFBlockTags.STOOLS)
                .stoolBlockstate()
                .withItem {
                    stoolRecipes()
                    tag(AFItemTags.STOOLS)
                    tab(TAB)
                }.register()
        }

    val TALL_STOOLS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_tall_stool")
                .dyedBlock(dye, ::TallStoolBlock)
                .initialProperties { AFBlocks.RED_TALL_STOOL.get() }
                .tag(AFBlockTags.TALL_STOOLS)
                .tallStoolBlockstate()
                .withItem {
                    tallStoolRecipes()
                    tag(AFItemTags.TALL_STOOLS)
                    tab(TAB)
                }.register()
        }

    val CURTAINS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_curtain")
                .dyedBlock(dye, ::CurtainBlock)
                .initialProperties { AFBlocks.RED_CURTAIN.get() }
                .tag(AFBlockTags.CURTAINS)
                .curtainBlockstate()
                .curtainLoot()
                .withItem {
                    curtainRecipes()
                    curtainItemModel()
                    tag(AFItemTags.CURTAINS)
                    tab(TAB)
                }.register()
        }

    @JvmField
    val LAMPS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_lamp")
                .dyedBlock(dye) { LampBlock(dye, it) }
                .initialProperties { AFBlocks.RED_LAMP.get() }
                .tag(AFBlockTags.LAMPS)
                .lampBlockstate()
                .withItem {
                    lampRecipes()
                    tag(AFItemTags.LAMPS)
                    tab(TAB)
                }.register()
        }

    @JvmField
    val LAMPS_CONNECTORS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_lamp_connector")
                .dyedBlock(dye) { LampConnectorBlock(dye, it) }
                .noLang()
                .loot { t, b -> t.dropOther(b, LAMPS[dye]!!.get()) }
                .initialProperties { AFBlocks.RED_LAMP_CONNECTOR.get() }
                .lampConnectorBlockstate()
                .register()
        }
}

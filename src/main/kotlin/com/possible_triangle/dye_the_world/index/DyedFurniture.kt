package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.noLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.starfish_studios.another_furniture.block.*
import com.starfish_studios.another_furniture.registry.AFBlockTags
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.starfish_studios.another_furniture.registry.AFItemTags
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object DyedFurniture {

    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ANOTHER_FURNITURE.createId(ANOTHER_FURNITURE))

    val SOFAS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_sofa")
            .dyedBlock(dye, ANOTHER_FURNITURE, ::SofaBlock)
            .initialProperties { AFBlocks.RED_SOFA.get() }
            .optionalTag(AFBlockTags.SOFAS)
            .sofaBlockstate()
            .withItem {
                sofaRecipes()
                optionalTag(AFItemTags.SOFAS)
                tab(TAB)
            }
            .register()
    }

    val STOOLS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_stool")
            .dyedBlock(dye, ANOTHER_FURNITURE, ::StoolBlock)
            .initialProperties { AFBlocks.RED_STOOL.get() }
            .optionalTag(AFBlockTags.STOOLS)
            .stoolBlockstate()
            .withItem {
                stoolRecipes()
                optionalTag(AFItemTags.STOOLS)
                tab(TAB)
            }
            .register()
    }

    val TALL_STOOLS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_tall_stool")
            .dyedBlock(dye, ANOTHER_FURNITURE, ::TallStoolBlock)
            .initialProperties { AFBlocks.RED_TALL_STOOL.get() }
            .optionalTag(AFBlockTags.TALL_STOOLS)
            .tallStoolBlockstate()
            .withItem {
                tallStoolRecipes()
                optionalTag(AFItemTags.TALL_STOOLS)
                tab(TAB)
            }
            .register()
    }

    val CURTAINS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_curtain")
            .dyedBlock(dye, ANOTHER_FURNITURE, ::CurtainBlock)
            .initialProperties { AFBlocks.RED_CURTAIN.get() }
            .optionalTag(AFBlockTags.CURTAINS)
            .curtainBlockstate()
            .curtainLoot()
            .withItem {
                curtainRecipes()
                curtainItemModel()
                optionalTag(AFItemTags.CURTAINS)
                tab(TAB)
            }
            .register()
    }

    @JvmField
    val LAMPS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_lamp")
            .dyedBlock(dye, ANOTHER_FURNITURE) { LampBlock(dye, it) }
            .initialProperties { AFBlocks.RED_LAMP.get() }
            .optionalTag(AFBlockTags.LAMPS)
            .lampBlockstate()
            .withItem {
                lampRecipes()
                lampItemModel()
                optionalTag(AFItemTags.LAMPS)
                tab(TAB)
            }
            .register()
    }

    @JvmField
    val LAMPS_CONNECTORS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_lamp_connector")
            .dyedBlock(dye, ANOTHER_FURNITURE) { LampConnectorBlock(dye, it) }
            .noLang()
            .loot { t, b -> t.dropOther(b, LAMPS[dye]!!.get()) }
            .initialProperties { AFBlocks.RED_LAMP_CONNECTOR.get() }
            .lampConnectorBlockstate()
            .register()
    }

    fun register() {
        // Loads this class
    }

}
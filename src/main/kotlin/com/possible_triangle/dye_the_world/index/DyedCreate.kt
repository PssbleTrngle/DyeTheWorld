package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.withItem
import com.simibubi.create.AllBlocks
import com.simibubi.create.AllTags
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock
import com.simibubi.create.content.contraptions.bearing.SailBlock
import com.simibubi.create.content.equipment.toolbox.ToolboxBlock
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlock
import net.minecraft.tags.BlockTags

object DyedCreate {

    private val REGISTRATE = DyedRegistrate(CREATE)

    val NIXIE_TUBES = dyesFor(CREATE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_nixie_tube")
            .block { NixieTubeBlock(it, dye) }
            .initialProperties { AllBlocks.ORANGE_NIXIE_TUBE.get() }
            .lang("${dye.translation} Nixie Tube")
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .nixieTubeBlockstate()
            .loot { t, b -> t.dropOther(b, AllBlocks.ORANGE_NIXIE_TUBE) }
            .register()
    }

    val TOOLBOXES = dyesFor(CREATE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_toolbox")
            .block { ToolboxBlock(it, dye) }
            .lang("${dye.translation} Toolbox")
            .tag(AllTags.AllBlockTags.TOOLBOXES.tag)
            .toolboxLoot()
            .toolboxBlockstate(dye)
            .withItem {
                tag(AllTags.AllItemTags.TOOLBOXES.tag)
                toolboxItemModel(dye)
            }
            .register()
    }

    val SEATS = dyesFor(CREATE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_seat")
            .block { SeatBlock(it, dye) }
            .lang("${dye.translation} Seat")
            .tag(AllTags.AllBlockTags.SEATS.tag)
            .seatBlockstate(dye)
            .withItem {
                tag(AllTags.AllItemTags.SEATS.tag)
            }
            .register()
    }

    val SAILS = dyesFor(CREATE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_sail")
            .block { SailBlock.withCanvas(it, dye) }
            .lang("${dye.translation} Sail")
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .sailBlockstate()
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}
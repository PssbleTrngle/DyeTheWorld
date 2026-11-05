package com.possible_triangle.dye_the_world.supplementaries

import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.supplementaries.data.sackBlockstate
import com.possible_triangle.dye_the_world.supplementaries.data.sackItemModel
import com.possible_triangle.dye_the_world.translation
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.util.ColorRGBA
import net.minecraft.world.item.DyeColor

private val DyeColor.rgba get() = ColorRGBA(textColor)

object DyedSupplementariesSquared {
    val SACKS =
        REGISTRATE_SUPPLEMENTARIES_SQUARED.dyed { dye ->
            `object`("sack_$dye")
                .dyedBlock(dye) { SackBlock(dye.rgba, it) }
                .lang("${dye.translation} Sack")
                .sackBlockstate()
                .loot { t, b -> t.add(b, t.createShulkerBoxDrop(b)) }
                .withItem {
                    sackItemModel()
                }.register()
        }
}

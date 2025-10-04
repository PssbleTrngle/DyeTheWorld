package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CHALK
import com.possible_triangle.dye_the_world.data.chalkBlockstate
import com.possible_triangle.dye_the_world.data.chalkBoxModel
import com.possible_triangle.dye_the_world.data.chalkItemModel
import com.possible_triangle.dye_the_world.data.chalkRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import io.github.mortuusars.chalk.Chalk
import io.github.mortuusars.chalk.block.ChalkMarkBlock
import io.github.mortuusars.chalk.item.ChalkItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item

object DyedChalk {

    private val REGISTRATE = DyedRegistrate.create(CHALK)

    private val DYES = dyesFor(CHALK)

    val CHALK_MARKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_chalk_mark")
            .dyedBlock(dye) { ChalkMarkBlock(dye, it) }
            .optionalTag(Chalk.Tags.Blocks.CHALK_MARKS)
            .lang("${dye.translation} Chalk Mark")
            .chalkBlockstate()
            .register()
    }

    val CHALKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_chalk")
            .dyedItem(dye) { ChalkItem(dye, it) }
            .optionalTag(Chalk.Tags.Items.CHALKS)
            .optionalTag(dye.tag)
            .lang("${dye.translation} Chalk")
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .chalkItemModel()
            .chalkRecipe()
            .register()
    }

    val CHALK_BOX = REGISTRATE.`object`("chalk_box")
        .item(::Item)
        .chalkBoxModel()
        .register()

    fun registerDatagen() {
        REGISTRATE.register()
    }

}
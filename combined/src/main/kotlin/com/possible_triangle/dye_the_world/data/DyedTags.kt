package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.index.DyedTags
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.simibubi.create.AllTags
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.DyeColor

fun DyedRegistrate.generateTags() {
    DyeColor.entries.forEach { dye ->
        DyedTags.Items.COMFORTS_HAMMOCKS.addOptional(Mods.COMFORTS.createId("hammock_$dye"))
        DyedTags.Items.SLEEPING_BAGS.addOptional(Mods.COMFORTS.createId("sleeping_bag_$dye"))
    }

    BlockTags.MINEABLE_WITH_AXE.addOptional(AllTags.AllBlockTags.SEATS.tag)
}

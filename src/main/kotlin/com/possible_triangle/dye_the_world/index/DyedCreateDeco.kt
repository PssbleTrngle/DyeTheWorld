package com.possible_triangle.dye_the_world.index

import com.github.talrey.createdeco.api.CDTags
import com.github.talrey.createdeco.blocks.ShippingContainerBlock
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_DECO
import com.possible_triangle.dye_the_world.data.placardBlockstate
import com.possible_triangle.dye_the_world.data.placardRecipe
import com.possible_triangle.dye_the_world.data.shippingContainerBlockstate
import com.possible_triangle.dye_the_world.data.shippingContainerRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.registrate.DyedRegistrate
import com.possible_triangle.dye_the_world.translation
import com.simibubi.create.AllTags.AllBlockTags
import com.simibubi.create.content.decoration.placard.PlacardBlock
import net.minecraft.tags.BlockTags

object DyedCreateDeco {
    private val REGISTRATE = DyedRegistrate.create(CREATE_DECO)

    private val DYES = dyesFor(CREATE_DECO)

    val SHIPPING_CONTAINERS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_shipping_container")
                .dyedBlock(dye) { ShippingContainerBlock(it, dye) }
                .lang("${dye.translation} Shipping Container")
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .shippingContainerBlockstate()
                .withItem {
                    shippingContainerRecipe()
                }.register()
        }

    val PLACARDS =
        DYES.associateWith { dye ->
            REGISTRATE
                .`object`("${dye}_placard")
                .dyedBlock(dye, ::PlacardBlock)
                .lang("${dye.translation} Placard")
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .optionalTag(AllBlockTags.SAFE_NBT.tag)
                .placardBlockstate()
                .withItem {
                    optionalTag(CDTags.PLACARD)
                    placardRecipe()
                }.register()
        }

    fun register() {
        REGISTRATE.register()
    }
}

package com.possible_triangle.dye_the_world.create_deco

import com.github.talrey.createdeco.api.CreateDecoTags
import com.github.talrey.createdeco.blocks.ShippingContainerBlock
import com.possible_triangle.dye_the_world.create_deco.data.placardBlockstate
import com.possible_triangle.dye_the_world.create_deco.data.placardRecipe
import com.possible_triangle.dye_the_world.create_deco.data.shippingContainerBlockstate
import com.possible_triangle.dye_the_world.create_deco.data.shippingContainerRecipe
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.translation
import com.simibubi.create.AllTags.AllBlockTags
import com.simibubi.create.content.decoration.placard.PlacardBlock
import net.minecraft.tags.BlockTags

object DyedCreateDeco {
    val SHIPPING_CONTAINERS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_shipping_container")
                .dyedBlock(dye) { ShippingContainerBlock(it, dye) }
                .lang("${dye.translation} Shipping Container")
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .shippingContainerBlockstate()
                .withItem {
                    shippingContainerRecipe()
                }.register()
        }

    val PLACARDS =
        REGISTRATE.dyed { dye ->
            `object`("${dye}_placard")
                .dyedBlock(dye, ::PlacardBlock)
                .lang("${dye.translation} Placard")
                .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .optionalTag(AllBlockTags.SAFE_NBT.tag)
                .placardBlockstate()
                .withItem {
                    optionalTag(CreateDecoTags.PLACARDS)
                    placardRecipe()
                }.register()
        }
}

package com.possible_triangle.dye_the_world.supplementaries

import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.translation
import net.minecraft.resources.ResourceLocation

object DyedAmendments {
    val CEILING_BANNERS =
        REGISTRATE_AMENDMENTS.dyed { dye ->
            `object`("ceiling_banner_$dye")
                .dyedBlock(dye)
                .lang("${dye.translation} Banner")
                .tag(SupplementariesTags.Blocks.CEILING_BANNERS)
                .blockstate { context, provider ->
                    val model = provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/banner"))
                    provider.simpleBlock(context.get(), model)
                }.register()
        }
}

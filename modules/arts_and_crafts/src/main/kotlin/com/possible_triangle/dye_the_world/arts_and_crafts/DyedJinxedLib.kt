package com.possible_triangle.dye_the_world.arts_and_crafts

import com.kekecreations.jinxedlib.core.util.JinxedCreativeCategoryUtils
import com.possible_triangle.dye_the_world.dyeOrder

object DyedJinxedLib {
    init {
        dyeOrder { origin, dye ->
            JinxedCreativeCategoryUtils.colourOrder.add(
                JinxedCreativeCategoryUtils.colourOrder.indexOf(origin) + 1,
                dye,
            )

            JinxedCreativeCategoryUtils.colourOrderBackToFront.add(
                JinxedCreativeCategoryUtils.colourOrderBackToFront.indexOf(origin),
                dye,
            )
        }
    }
}

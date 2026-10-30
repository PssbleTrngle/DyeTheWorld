package com.possible_triangle.dye_the_world

import com.possible_triangle.multikulti.datagen.conditions.ModLoaded
import com.possible_triangle.multikulti.datagen.conditions.withConditions

fun Any.withNamespace(
    namespace: String,
    block: () -> Unit,
) = withConditions(ModLoaded(namespace), block = block)

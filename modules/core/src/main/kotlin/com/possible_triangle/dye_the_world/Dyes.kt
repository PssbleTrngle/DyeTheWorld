@file:JvmName("Dyes")

package com.possible_triangle.dye_the_world

import com.ninni.dye_depot.registry.DDDyes
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.getOrThrow
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.*

@JvmField
val DEPOT_DYES = DDDyes.entries.map { it.get() }

@JvmField
val VANILLA_DYES = DyeColor.entries.subList(0, 16)

@Suppress("UNUSED_PARAMETER")
fun dyesFor(modid: String): List<DyeColor> = DEPOT_DYES

const val DYE_DEPOT = "dye_depot"

val DyeColor.namespace: String
    get() {
        return if (DDDyes.isModDye(this)) {
            DYE_DEPOT
        } else {
            "minecraft"
        }
    }

fun DyeColor.itemOf(type: String): Item {
    val id = namespace.createId("${this}_$type")
    return BuiltInRegistries.ITEM.getOrThrow(id)
}

fun DyeColor.blockOf(type: String): Block {
    val id = namespace.createId("${this}_$type")
    return BuiltInRegistries.BLOCK.getOrThrow(id)
}

val DyeColor.translation
    get() =
        serializedName.split("_").joinToString(" ") { part ->
            part.replaceFirstChar { it.uppercase(Locale.ROOT) }
        }

val DyeColor?.isVanilla get() = this == null || !DDDyes.isModDye(this)

private val DYE_ITEMS = DyeColor.entries.associateWith { NonNullSupplier.lazy { it.itemOf("dye") } }

val DyeColor.item: Item get() = DYE_ITEMS.getValue(this).get()

private val ORDERED_DYES =
    listOf(
        DyeColor.BROWN to DDDyes.MAROON.get(),
        DyeColor.BROWN to DDDyes.ROSE.get(),
        DyeColor.RED to DDDyes.CORAL.get(),
        DyeColor.RED to DDDyes.GINGER.get(),
        DyeColor.ORANGE to DDDyes.TAN.get(),
        DyeColor.ORANGE to DDDyes.BEIGE.get(),
        DyeColor.YELLOW to DDDyes.AMBER.get(),
        DyeColor.YELLOW to DDDyes.OLIVE.get(),
        DyeColor.LIME to DDDyes.FOREST.get(),
        DyeColor.GREEN to DDDyes.VERDANT.get(),
        DyeColor.GREEN to DDDyes.TEAL.get(),
        DyeColor.CYAN to DDDyes.MINT.get(),
        DyeColor.CYAN to DDDyes.AQUA.get(),
        DyeColor.BLUE to DDDyes.SLATE.get(),
        DyeColor.BLUE to DDDyes.NAVY.get(),
        DyeColor.BLUE to DDDyes.INDIGO.get(),
    )

fun dyeOrder(block: (DyeColor, DyeColor) -> Unit) {
    ORDERED_DYES.forEach { (origin, dye) -> block(origin, dye) }
}

private val DARK_BACKGROUNDS = listOf("indigo", "ginger", "maroon", "navy", "rose", "teal", "verdant")

val DyeColor.isDarkBackground get() = DARK_BACKGROUNDS.contains(serializedName)

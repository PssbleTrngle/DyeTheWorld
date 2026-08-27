package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.Constants.Mods.CHALK
import com.possible_triangle.dye_the_world.dyesFor
import io.github.mortuusars.chalk.Chalk
import io.github.mortuusars.chalk.Config
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.DyedItemColor
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent

object ChalkCompat {
    private val DYES = dyesFor(CHALK)

    @JvmStatic
    fun registerColors(map: MutableMap<DyeColor, Int>) {
        DYES.forEach {
            map[it] = it.fireworkColor
        }
    }

    fun addCreativeTabEntries(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES) return

        if (Config.Server.ADD_DYED_CHALKS_TO_TAB.get()) {
            DYES.forEach {
                val stack = ItemStack(Chalk.Items.CHALK.get())
                stack.set(DataComponents.DYED_COLOR, DyedItemColor(it.fireworkColor, true))
                event.accept(stack)
            }
        }
    }
}

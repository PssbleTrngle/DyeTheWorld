package com.possible_triangle.dye_the_world.chalk

import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.extensions.DE_LANG
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.translation
import com.tterrag.registrate.providers.ProviderType
import io.github.mortuusars.chalk.Chalk
import io.github.mortuusars.chalk.Config
import net.minecraft.Util.makeDescriptionId
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.DyedItemColor
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent

object DyedChalk {
    init {
        REGISTRATE.dyes.forEach { dye ->
            val descriptionId = makeDescriptionId("item", CHALK.createId("${dye}_chalk"))

            REGISTRATE.addDataGenerator(ProviderType.LANG) {
                it.add(descriptionId, "${dye.translation} Chalk")
            }

            REGISTRATE.addDataGenerator(DE_LANG) {
                it.add(descriptionId, "${dye.germanTranslation(Genus.F)} Kreide")
            }
        }
    }

    @SubscribeEvent
    private fun addCreativeTabEntries(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey != CreativeModeTabs.TOOLS_AND_UTILITIES) return

        if (Config.Server.ADD_DYED_CHALKS_TO_TAB.get()) {
            REGISTRATE.dyes.forEach {
                val stack = ItemStack(Chalk.Items.CHALK.get())
                stack.set(DataComponents.DYED_COLOR, DyedItemColor(it.fireworkColor, true))
                event.accept(stack)
            }
        }
    }

    @JvmStatic
    fun registerColors(map: MutableMap<DyeColor, Int>) {
        REGISTRATE.dyes.forEach {
            map[it] = it.fireworkColor
        }
    }
}

package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_RAILWAYS
import com.possible_triangle.dye_the_world.extensions.createId
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

sealed class DyedTags<T>(private val registry: ResourceKey<Registry<T>>) {

    object Items : DyedTags<Item>(Registries.ITEM) {
        val DYED = DyeColor.entries.associateWith {
            loader("dyed/$it")
        }

        val HAMMOCKS = create(Mods.COMFORTS, "hammocks")
        val SLEEPING_BAGS = create(Mods.COMFORTS, "sleeping_bags")
        val GLASS_SHARDS = create(Mods.QUARK, "shards")
        val PET_BEDS = create(Mods.DOMESTICATION_INNOVATION, "pet_beds")
        val RADON_LAMPS = create(Mods.ALEXS_CAVES, "radon_lamps")
        val IRON_PLATES = loader("plates/iron")
        val CONDUCTOR_CAPS = create(CREATE_RAILWAYS, "conductor_caps")
        val CHAIRS = create(Mods.CREATE_INTERIORS, "chairs")
        val FLOOR_CHAIRS = create(Mods.CREATE_INTERIORS, "floor_chairs")
        val NON_CLEANABLE = create(Mods.SUPPLEMENTARIES, "non_cleanable")
        val AWNINGS = create(Mods.SUPPLEMENTARIES, "awnings")
    }

    object Blocks : DyedTags<Block>(Registries.BLOCK) {
        val DYED = DyeColor.entries.associateWith {
            loader("dyed/$it")
        }

        val FLAGS = create(Mods.SUPPLEMENTARIES, "flags")
        val QUARK_STOOLS = create(Mods.QUARK, "stools")
        val FRAMED_GLASSES = create(Mods.QUARK, "framed_glasses")
        val FRAMED_GLASS_PANES = create(Mods.QUARK, "framed_glass_panes")
        val CEILING_BANNERS = create(Mods.AMENDMENTS, "ceiling_banners")
        val BUNTINGS = create(Mods.SUPPLEMENTARIES, "buntings")
        val CHAIRS = create(Mods.CREATE_INTERIORS, "chairs")
        val FLOOR_CHAIRS = create(Mods.CREATE_INTERIORS, "floor_chairs")
        val NON_CLEANABLE = create(Mods.SUPPLEMENTARIES, "non_cleanable")
        val BOUNCY_BLOCKS = create(Mods.SUPPLEMENTARIES, "bouncy_blocks")
        val AWNINGS = create(Mods.SUPPLEMENTARIES, "awnings")
        val MINEABLE_SHEAR = create("mineable/shear")
        val MINEABLE_KNIFE = create(Mods.FARMERS_DELIGHT, "mineable/knife")
        val BRITTLE = create(Mods.CREATE, "brittle")
    }

    protected fun create(namespace: String, path: String): TagKey<T> = TagKey.create(registry, namespace.createId(path))
    protected fun loader(path: String): TagKey<T> = create("forge", path)
    protected fun create(path: String): TagKey<T> = TagKey.create(registry, ResourceLocation.withDefaultNamespace(path))


}
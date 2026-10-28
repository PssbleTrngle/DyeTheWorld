package com.possible_triangle.dye_the_world.extensions

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.extensions.getOrThrow
import com.possible_triangle.dye_the_world.registrate.DyedBuilder
import com.possible_triangle.multikulti.platform.LOGGER
import com.tterrag.registrate.builders.Builder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation

/**
 * after a recent refactor, lots of blocks have changed their namespace or only there ID
 * mainly changing the namespace from "dye_the_world" -> the respective mods namespace
 * this method marks the old ID and tells neoforge to register an alias for it, so worlds using the old IDs don't lose
 * these blocks & items
 */
fun <T : Builder<*, *, *, *>> T.migrate(id: ResourceLocation) =
    apply {
        if (this is DyedBuilder) {
            migrateFrom = id
        }
    }

fun <T : Builder<*, *, *, *>> T.migrate(
    namespace: String = Constants.MOD_ID,
    name: String = this.name,
) = migrate(namespace.createId(name))

internal fun <T> T.doMigration() where T : Builder<*, *, *, *>, T : DyedBuilder {
    onRegister {
        val oldId = migrateFrom ?: return@onRegister
        val registry = BuiltInRegistries.REGISTRY.getOrThrow(registryKey.location())
        val newId = owner.modid.createId(name)
        registry.addAlias(oldId, newId)
        LOGGER.debug("migrating {} {} to {}", registryKey.location().path, oldId, newId)
    }
}

/**
 * For blocks/items that have been added after the refactor.
 * Sometimes the auto-migration would have conflicts (ex: moreconcrete & vanillabackports)
 * This method can be used to mark the block as needing no registry alias
 */
fun <T : Builder<*, *, *, *>> T.noMigrate() =
    apply {
        if (this is DyedBuilder) {
            migrateFrom = null
        }
    }

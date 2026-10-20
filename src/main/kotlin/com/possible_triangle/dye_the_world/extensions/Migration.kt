package com.possible_triangle.dye_the_world.extensions

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.extensions.getOrThrow
import com.possible_triangle.dye_the_world.registrate.DyedBuilder
import com.possible_triangle.multikulti.platform.LOGGER
import com.tterrag.registrate.builders.Builder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation

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

package com.possible_triangle.dye_the_world

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.Serializer
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.conditions.ICondition
import net.minecraftforge.common.crafting.conditions.IConditionSerializer
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun stubLootCondition(key: ResourceLocation) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(Registries.LOOT_CONDITION_TYPE, key) { LootItemConditionType(StubLootCondition) }
    }
}

private class StubLootCondition : LootItemCondition {
    override fun getType(): LootItemConditionType = throw IllegalStateException("Serialization not supported")

    override fun test(context: LootContext?): Boolean = false

    companion object : Serializer<StubLootCondition> {
        override fun serialize(
            json: JsonObject,
            condition: StubLootCondition,
            context: JsonSerializationContext,
        ): Unit = throw IllegalStateException("Serialization not supported")

        override fun deserialize(
            json: JsonObject,
            context: JsonDeserializationContext,
        ) = StubLootCondition()
    }
}

fun stubRecipeCondition(key: ResourceLocation) {
    CraftingHelper.register(StubRecipeCondition.Serializer(key))
}

private class StubRecipeCondition : ICondition {
    override fun getID(): ResourceLocation = throw IllegalStateException("Serialization not supported")

    override fun test(context: ICondition.IContext?): Boolean = false

    class Serializer(
        private val id: ResourceLocation,
    ) : IConditionSerializer<StubRecipeCondition> {
        override fun write(
            json: JsonObject,
            condition: StubRecipeCondition,
        ): Unit = throw IllegalStateException("Serialization not supported")

        override fun read(json: JsonObject) = StubRecipeCondition()

        override fun getID() = id
    }
}

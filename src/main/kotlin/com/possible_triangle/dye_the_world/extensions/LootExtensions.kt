package com.possible_triangle.dye_the_world.extensions

import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.ItemSubPredicates

fun ItemPredicate.Builder.hasEnchantment(predicate: EnchantmentPredicate): ItemPredicate.Builder {
    return withSubPredicate(
        ItemSubPredicates.ENCHANTMENTS,
        ItemEnchantmentsPredicate.enchantments(
            listOf(
                predicate
            )
        )
    )
}
package com.griffins.fours_movement;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class TripleJumpEnchantment extends Enchantment {

	public TripleJumpEnchantment() {
		super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public int getMinPower(int level) {
		return level * 15;
	}
}

package com.griffins.movement;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class TripleJumpEnchantment extends Enchantment {

	public TripleJumpEnchantment() {
		super(Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
	}

	@Override
	public int getMinPower(int level) {
		return level * 1;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}
}

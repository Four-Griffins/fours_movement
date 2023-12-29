package com.griffins.fours_movement;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Constants {
	public static final Identifier AIRJUMP = new Identifier("fours_movement", "airjump");
	public static final Identifier LUNGE = new Identifier("fours_movement", "lunge");
	public static final Identifier AIRDASH = new Identifier("fours_movement", "airdash");
	public static final AirdashEnchantment AIRDASH_ENCHANTMENT = new AirdashEnchantment();

	public static final Identifier TJUMP = new Identifier("fours_movement", "triplejump");
	public static final TripleJumpEnchantment TJUMP_ENCHANTMENT = new TripleJumpEnchantment();

	public static final SoundEvent AIRDASH_SOUND = SoundEvent.createFixedRangeEvent(AIRDASH, 16f);
	public static final SoundEvent AIRJUMP_SOUND = SoundEvent.createFixedRangeEvent(AIRJUMP, 16f);
	public static final SoundEvent LUNGE_SOUND = SoundEvent.createFixedRangeEvent(LUNGE, 16f);
}

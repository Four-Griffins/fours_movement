package com.griffins.fours_movement;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.Random;

public class MovementEffects {
	private final static Random random = new Random();

	public static void airjump_effect(LivingEntity local, LivingEntity effect) {
		World world = local.getWorld();
		world.playSound(local, effect.getBlockPos(), Constants.AIRJUMP_SOUND, SoundCategory.PLAYERS, 0.6f, 3);

		for(int i = 0; i < 10; i++) {
			world.addParticle(
				ParticleTypes.CRIT,
				effect.getParticleX(.5), effect.getY(), effect.getParticleZ(.5),
				random.nextGaussian() * .2, random.nextDouble(.1) + .4, random.nextGaussian() * .2
			);
		}
	}

	public static void lunge_effect(LivingEntity local, LivingEntity effect) {
		World world = local.getWorld();
		world.playSound(local, effect.getBlockPos(), Constants.LUNGE_SOUND, SoundCategory.PLAYERS, .6f, 3f);

		for(int i = 0; i < 5; i++) {
			world.addParticle(
				ParticleTypes.CLOUD,
				effect.getParticleX(.2), effect.getY(), effect.getParticleZ(.2),
				random.nextGaussian() * .05, .01f, random.nextGaussian() * .05
			);
		}
	}

	public static void airdash_effect(LivingEntity local, LivingEntity effect) {
		World world = local.getWorld();
		world.playSound(local, effect.getBlockPos(), Constants.AIRDASH_SOUND, SoundCategory.PLAYERS, .4f,  1.2f);

	}
}

package com.griffins.fours_movement;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class MovementEffects {
	private final static Random random = new Random();
	private static HashMap<UUID, Integer> airdashParticlesAlive = new HashMap<UUID, Integer>(){};

	public static void airjump_effect(LivingEntity local, LivingEntity effect) {
		World world = local.getWorld();
		world.playSound(local, effect.getBlockPos(), Constants.AIRJUMP_SOUND, SoundCategory.PLAYERS, 0.3f, 3);

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
		world.playSound(local, effect.getBlockPos(), Constants.LUNGE_SOUND, SoundCategory.PLAYERS, .3f, 3f);

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
		world.playSound(local, effect.getBlockPos(), Constants.AIRDASH_SOUND, SoundCategory.PLAYERS, .2f,  1.2f);
		airdashParticlesAlive.put(effect.getUuid(), 0);
	}

	public static void airdash_particle_effect(World world) {
		airdashParticlesAlive.forEach((key, val) -> {
			PlayerEntity player = world.getPlayerByUuid(key);
			for (int i = 0; i < 3; i++) {
				world.addParticle(
					Constants.AIRDASH_PARTICLE,
					player.getX(), player.getBodyY(.5), player.getZ(),
					random.nextGaussian() * .04, random.nextGaussian() * .08, random.nextGaussian() * .04);
			}
			airdashParticlesAlive.put(key, airdashParticlesAlive.get(key) + 1);
		});
		airdashParticlesAlive.values().removeIf(v -> v >= Constants.AIRDASH_PARTICLE_LIFESPAN);
	}
}

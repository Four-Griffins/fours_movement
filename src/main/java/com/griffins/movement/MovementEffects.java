package com.griffins.movement;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Random;

public class MovementEffects {
	private final static Random random = new Random();

	public static void djump_effect(PlayerEntity local, PlayerEntity effect) {
		World world = local.getWorld();
		world.playSound(local, effect.getBlockPos(), SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 0.6f, 3);

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
		world.playSound(local, effect.getBlockPos(), SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.AMBIENT, .6f, 3f);

		for(int i = 0; i < 5; i++) {
			world.addParticle(
				ParticleTypes.CLOUD,
				effect.getParticleX(.2), effect.getY(), effect.getParticleZ(.2),
				random.nextGaussian() * .05, .01f, random.nextGaussian() * .05
			);
		}
	}
}

package com.griffins.fours_movement.mixin;

import com.griffins.fours_movement.MovementEffects;
import com.griffins.fours_movement.Constants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {
	private int timeSinceSneak = 0;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void tickMovement(CallbackInfo info) {
		if (isSneaking()) { timeSinceSneak = 0; } else { timeSinceSneak++; }
		MovementEffects.airdash_particle_effect(getWorld());
	}

	@Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"))
	private void addLessExhaustion(PlayerEntity target, float exhaustion) {
		target.addExhaustion(exhaustion * .2f);
	}

	@Inject(method = "jump", at = @At("TAIL"))
	private void lunge(CallbackInfo info) {
		if (timeSinceSneak > 8) return;
		if (isSneaking()) return;
		Vec3d velocity = getVelocity();
		setVelocity(velocity.x, velocity.y * 1.4F, velocity.z);

		MovementEffects.lunge_effect(this, this);

		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeUuid(getUuid());
		ClientPlayNetworking.send(Constants.LUNGE, buf);
	}
}

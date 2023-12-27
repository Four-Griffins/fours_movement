package com.griffins.movement.mixin;

import com.griffins.movement.MovementEffects;
import com.griffins.movement.MovementIDs;
import com.griffins.movement.MovementMod;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerMovementMixin extends LivingEntity {
	private int timeSinceSneak = 0;

	protected PlayerMovementMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void checkSneak(CallbackInfo info) {
		if (isSneaking()) { timeSinceSneak = 0; } else { timeSinceSneak++; }
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
		ClientPlayNetworking.send(MovementIDs.LUNGE, buf);
	}
}

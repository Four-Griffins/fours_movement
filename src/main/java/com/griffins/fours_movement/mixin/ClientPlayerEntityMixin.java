package com.griffins.fours_movement.mixin;

import com.griffins.fours_movement.MovementEffects;
import com.griffins.fours_movement.Constants;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin {
	private int djumps = 0;
	private int djumpcooldown = 2;
	private boolean prevjump = false;

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void enhancedMovement(CallbackInfo info) {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
		if (player.isOnGround()) {
			djumpcooldown = 2;
			djumps = 1 + EnchantmentHelper.getEquipmentLevel(Constants.TJUMP_ENCHANTMENT, player);
		}

		if (djumpcooldown > 0) {
			djumpcooldown--;
			prevjump = player.input.jumping;
			return;
		}

		ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
		boolean canjump = player.input.jumping
			&& !prevjump
			&& djumps > 0
			&& !(chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack))
			&& !player.isClimbing()
			&& !player.isTouchingWater()
			&& !player.isSwimming()
			&& !player.getAbilities().flying
			&& !player.hasVehicle()
			&& !player.isFallFlying()
			&& !player.hasStatusEffect(StatusEffects.LEVITATION);

		if (canjump) {
			if (EnchantmentHelper.getEquipmentLevel(Constants.AIRDASH_ENCHANTMENT, player) == 1) {
				Vec3d direction = player.getRotationVecClient();
				if (player.input.pressingForward) {
					direction = direction.add(0, .25, 0);

					MovementEffects.airdash_effect(player, player);
					PacketByteBuf buf = PacketByteBufs.create();
					buf.writeUuid(player.getUuid());
					ClientPlayNetworking.send(Constants.AIRDASH, buf);
				} else {
					direction = direction.add(0, 1, 0).multiply(.4);
					if (direction.y < 0) direction.add(0, -direction.y + 0.5, 0);

					MovementEffects.airjump_effect(player, player);
					PacketByteBuf buf = PacketByteBufs.create();
					buf.writeUuid(player.getUuid());
					ClientPlayNetworking.send(Constants.AIRJUMP, buf);
				}
				player.setVelocity(direction);


				djumps--;
				djumpcooldown = 8;
			} else {
				player.jump();
				MovementEffects.airjump_effect(player, player);

				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeUuid(player.getUuid());
				ClientPlayNetworking.send(Constants.AIRJUMP, buf);

				djumps--;
				djumpcooldown = 5;
			}
		}

		prevjump = player.input.jumping;
	}
}

package com.griffins.movement.mixin;

import com.griffins.movement.MovementEffects;
import com.griffins.movement.MovementIDs;
import com.griffins.movement.MovementMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipe_book.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.stat.StatHandler;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerMovementMixin {
	private int djumps = 0;
	private int djumpcooldown = 2;
	private boolean prevjump = false;

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void enhancedMovement(CallbackInfo info) {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
		if (player.isOnGround()) {
			djumpcooldown = 2;
			djumps = 1 + EnchantmentHelper.getEquipmentLevel(MovementIDs.TJUMP_ENCHANT, player);
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
			&& !player.hasVehicle()
			&& !player.isSwimming()
			&& !player.isFallFlying()
			&& !player.hasStatusEffect(StatusEffects.LEVITATION);

		if (canjump) {
			player.jump();
			MovementEffects.djump_effect(player, player);

			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeUuid(player.getUuid());
			ClientPlayNetworking.send(MovementIDs.DJUMP, buf);

			djumps--;
			djumpcooldown = 5;
		}

		prevjump = player.input.jumping;
	}
}

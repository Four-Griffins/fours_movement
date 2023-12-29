package com.griffins.fours_movement;

import net.minecraft.entity.player.PlayerEntity;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.UUID;

public class MovementClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientPlayNetworking.registerGlobalReceiver(Constants.AIRJUMP,
			(client, handler, buf, responseSender) -> {
				UUID targetUuid = buf.readUuid();
				client.execute(() -> {
					PlayerEntity target = client.player.getWorld().getPlayerByUuid(targetUuid);
					MovementEffects.airjump_effect(client.player, target);
				});
			});

		ClientPlayNetworking.registerGlobalReceiver(Constants.AIRDASH,
			(client, handler, buf, responseSender) -> {
				UUID targetUuid = buf.readUuid();
				client.execute(() -> {
					PlayerEntity target = client.player.getWorld().getPlayerByUuid(targetUuid);
					MovementEffects.airdash_effect(client.player, target);
				});
			});

		ClientPlayNetworking.registerGlobalReceiver(Constants.LUNGE,
			(client, handler, buf, responseSender) -> {
				UUID targetUuid = buf.readUuid();
				client.execute(() -> {
					PlayerEntity target = client.player.getWorld().getPlayerByUuid(targetUuid);
					MovementEffects.lunge_effect(client.player, target);
				});
			});
	}
}

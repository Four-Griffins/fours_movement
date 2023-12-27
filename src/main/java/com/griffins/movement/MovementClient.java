package com.griffins.movement;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.UUID;

public class MovementClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientPlayNetworking.registerGlobalReceiver(MovementIDs.DJUMP,
			(client, handler, buf, responseSender) -> {
				MovementMod.LOGGER.info("double jump received");
				UUID effectPlayerUuid = buf.readUuid();
				client.execute(() -> {
					PlayerEntity effectPlayer = client.player.getWorld().getPlayerByUuid(effectPlayerUuid);
					MovementEffects.djump_effect(client.player, effectPlayer);
				});
			});

		ClientPlayNetworking.registerGlobalReceiver(MovementIDs.LUNGE,
			(client, handler, buf, responseSender) -> {
				UUID effectPlayerUuid = buf.readUuid();
				client.execute(() -> {
					MovementMod.LOGGER.info("lunge received");
					PlayerEntity effectPlayer = client.player.getWorld().getPlayerByUuid(effectPlayerUuid);
					MovementEffects.lunge_effect(client.player, effectPlayer);
				});
			});
	}
}

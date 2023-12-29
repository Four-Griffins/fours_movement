package com.griffins.fours_movement;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovementMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("4's Movement");

	@Override
	public void onInitialize(ModContainer mod) {
		ServerPlayNetworking.registerGlobalReceiver(Constants.AIRJUMP,
			(server, player, handler, buf, responseSender) -> {
				PacketByteBuf data = PacketByteBufs.create();
				data.writeUuid(buf.readUuid());

				server.execute(() -> {
					PlayerLookup.tracking(player).forEach(p -> {
						ServerPlayNetworking.send(p, Constants.AIRJUMP, data);
					});
				});
			});

		ServerPlayNetworking.registerGlobalReceiver(Constants.AIRDASH,
			(server, player, handler, buf, responseSender) -> {
				PacketByteBuf data = PacketByteBufs.create();
				data.writeUuid(buf.readUuid());

				server.execute(() -> {
					PlayerLookup.tracking(player).forEach(p -> {
						ServerPlayNetworking.send(p, Constants.AIRDASH, data);
					});
				});
			});

		ServerPlayNetworking.registerGlobalReceiver(Constants.LUNGE,
			(server, player, handler, buf, responseSender) -> {
				PacketByteBuf data = PacketByteBufs.create();
				data.writeUuid(buf.readUuid());

				server.execute(() -> {
					PlayerLookup.tracking(player).forEach(p -> {
						ServerPlayNetworking.send(p, Constants.LUNGE, data);
					});
				});
			});

		Registry.register(Registries.ENCHANTMENT, Constants.TJUMP, Constants.TJUMP_ENCHANTMENT);
		Registry.register(Registries.ENCHANTMENT, Constants.AIRDASH, Constants.AIRDASH_ENCHANTMENT);

		Registry.register(Registries.SOUND_EVENT, Constants.AIRDASH, Constants.AIRDASH_SOUND);
		Registry.register(Registries.SOUND_EVENT, Constants.AIRJUMP, Constants.AIRJUMP_SOUND);
	}
}

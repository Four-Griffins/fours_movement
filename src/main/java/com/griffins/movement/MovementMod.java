package com.griffins.movement;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
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
		ServerPlayNetworking.registerGlobalReceiver(MovementIDs.DJUMP,
			(server, player, handler, buf, responseSender) -> {
				PacketByteBuf data = PacketByteBufs.create();
				data.writeUuid(buf.readUuid());

				server.execute(() -> {
					PlayerLookup.tracking(player).forEach(p -> {
						ServerPlayNetworking.send(p, MovementIDs.DJUMP, data);
					});
				});
			});

		ServerPlayNetworking.registerGlobalReceiver(MovementIDs.LUNGE,
			(server, player, handler, buf, responseSender) -> {
				PacketByteBuf data = PacketByteBufs.create();
				data.writeUuid(buf.readUuid());

				server.execute(() -> {
					PlayerLookup.tracking(player).forEach(p -> {
						ServerPlayNetworking.send(p, MovementIDs.LUNGE, data);
					});
				});
			});

		Registry.register(Registries.ENCHANTMENT, MovementIDs.TJUMP, MovementIDs.TJUMP_ENCHANT);
	}
}

package dev.kyro.arcticguilds.events;

import dev.kyro.arcticguilds.controllers.objects.Guild;
import org.bukkit.entity.Player;

public class GuildCreateEvent extends GuildEvent {
	private Player player;

	public GuildCreateEvent(Player player, Guild guild) {
		super(guild);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}

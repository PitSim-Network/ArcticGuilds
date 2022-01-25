package dev.kyro.arcticguilds.events;

import dev.kyro.arcticguilds.controllers.objects.Guild;
import org.bukkit.entity.Player;

public class GuildWithdrawalEvent extends GuildEvent {
	private Player player;
	private int amount;

	public GuildWithdrawalEvent(Player player, Guild guild, int amount) {
		super(guild);
		this.player = player;
		this.amount = amount;
	}

	public Player getPlayer() {
		return player;
	}

	public int getAmount() {
		return amount;
	}
}
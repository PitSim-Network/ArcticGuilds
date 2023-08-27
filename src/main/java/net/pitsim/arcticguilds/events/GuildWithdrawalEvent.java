package net.pitsim.arcticguilds.events;

import net.pitsim.arcticguilds.controllers.objects.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class GuildWithdrawalEvent extends GuildEvent implements Cancellable {
	private boolean cancelled;
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

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}

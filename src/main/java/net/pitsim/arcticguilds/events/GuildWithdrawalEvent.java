package net.pitsim.arcticguilds.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuildWithdrawalEvent extends Event implements Cancellable {
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean isCancelled;
	private Player player;
	private int amount;
	private boolean isSoftCanceled;

	public GuildWithdrawalEvent(Player player, int amount) {
		this.isCancelled = false;
		this.player = player;
		this.amount = amount;
		this.isSoftCanceled = false;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	public Player getPlayer() {
		return player;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isSoftCanceled() {
		return isSoftCanceled;
	}

	public void setSoftCanceled(boolean softCanceled) {
		this.isSoftCanceled = softCanceled;
	}


}

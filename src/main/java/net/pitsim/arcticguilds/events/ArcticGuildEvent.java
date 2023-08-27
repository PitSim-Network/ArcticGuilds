package net.pitsim.arcticguilds.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArcticGuildEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}

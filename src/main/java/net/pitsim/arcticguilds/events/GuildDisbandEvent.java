package net.pitsim.arcticguilds.events;

import net.pitsim.arcticguilds.controllers.objects.Guild;

public class GuildDisbandEvent extends GuildEvent {
	public GuildDisbandEvent(Guild guild) {
		super(guild);
	}
}

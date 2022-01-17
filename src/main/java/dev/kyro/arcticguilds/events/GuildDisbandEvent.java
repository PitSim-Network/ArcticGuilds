package dev.kyro.arcticguilds.events;

import dev.kyro.arcticguilds.controllers.objects.Guild;

public class GuildDisbandEvent extends GuildEvent {
	public GuildDisbandEvent(Guild guild) {
		super(guild);
	}
}

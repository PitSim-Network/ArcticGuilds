package dev.kyro.arcticguilds.events;

import dev.kyro.arcticguilds.controllers.objects.Guild;

public class GuildEvent extends ArcticGuildEvent {
	private Guild guild;

	public GuildEvent(Guild guild) {
		this.guild = guild;
	}

	public Guild getGuild() {
		return guild;
	}
}

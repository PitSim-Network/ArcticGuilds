package dev.kyro.arcticguilds.controllers;

import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.events.GuildReputationEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class ReputationManager implements Listener {

	static {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Guild guild : GuildManager.guildList) {
					if(guild.queuedReputation == 0) continue;

					GuildReputationEvent event = new GuildReputationEvent(guild, guild.queuedReputation);
					Bukkit.getPluginManager().callEvent(event);

					guild.addReputation(event.getTotalReputation());
				}
			}
		}.runTaskTimer(ArcticGuilds.INSTANCE, 20 * 30, 20 * 60);
	}
}

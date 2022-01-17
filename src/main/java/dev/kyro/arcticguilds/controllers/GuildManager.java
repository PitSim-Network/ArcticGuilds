package dev.kyro.arcticguilds.controllers;

import dev.kyro.arcticapi.data.AData;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuildManager implements Listener {
	public static List<Guild> guildList = new ArrayList<>();
	public static AData guildFile;

	static {
		guildFile = new AData("guilds", "", false);

		for(String key : guildFile.getConfiguration().getKeys(false)) {
			ConfigurationSection guildData = guildFile.getConfiguration().getConfigurationSection(key);
			Guild guild = new Guild(guildData);
			guildList.add(guild);
		}
	}

	public static Guild getGuild(UUID uuid) {
		for(Guild guild : guildList) if(guild.uuid == uuid) return guild;
		return null;
	}
}

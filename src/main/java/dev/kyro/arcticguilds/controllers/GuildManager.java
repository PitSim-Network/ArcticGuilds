package dev.kyro.arcticguilds.controllers;

import dev.kyro.arcticapi.data.AData;
import dev.kyro.arcticapi.data.APlayer;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GuildManager implements Listener {
	public static List<Guild> guildList = new ArrayList<>();
	public static List<GuildMember> guildMemberList = new ArrayList<>();
	public static AData guildFile;

	static {
		guildFile = new AData("guilds", "", false);

		for(Map.Entry<UUID, APlayer> entry : APlayerData.getAllData().entrySet()) {
			UUID uuid = entry.getKey();
			APlayer aPlayer = entry.getValue();

			GuildMember guildMember = new GuildMember(uuid, aPlayer.playerData);
			guildMemberList.add(guildMember);
		}

		for(String key : guildFile.getConfiguration().getKeys(false)) {
			ConfigurationSection guildData = guildFile.getConfiguration().getConfigurationSection(key);
			Guild guild = new Guild(key, guildData);
			guildList.add(guild);
		}
	}

	public static Guild getGuild(UUID guildUUID) {
		for(Guild guild : guildList) if(guild.uuid == guildUUID) return guild;
		return null;
	}

	public static Guild getGuild(Player player) {
		for(Guild guild : guildList) {
			for(Map.Entry<GuildMember, GuildMemberInfo> entry : guild.members.entrySet()) {
				if(!entry.getKey().playerUUID.equals(player.getUniqueId())) continue;
				return guild;
			}
		}
		return null;
	}

	public static GuildMember getMember(UUID playerUUID) {
		for(GuildMember guildMember : guildMemberList) {
			if(!guildMember.playerUUID.equals(playerUUID)) continue;
			return guildMember;
		}
		return new GuildMember(playerUUID);
	}
}

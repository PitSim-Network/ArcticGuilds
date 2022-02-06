package dev.kyro.arcticguilds.controllers;

import dev.kyro.arcticapi.data.AData;
import dev.kyro.arcticapi.data.APlayer;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import dev.kyro.arcticguilds.events.GuildReputationEvent;
import dev.kyro.arcticguilds.inventories.BuffPanel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GuildManager implements Listener {
	public static List<Guild> guildList = new ArrayList<>();
	public static List<GuildMember> guildMemberList = new ArrayList<>();
	public static AData guildFile;
	private static List<Guild> topGuilds = new ArrayList<>();

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
			new Guild(key, guildData);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
//				TODO: Call this code on buff/upgrade change
				for(Guild guild : guildList) {
					guild.diminish();
					for(Map.Entry<GuildMember, GuildMemberInfo> entry : guild.members.entrySet()) {
						UUID playerUUID = entry.getKey().playerUUID;
						for(Player player : Bukkit.getOnlinePlayers()) {
							if(!player.getUniqueId().equals(playerUUID)) continue;
							if(player.getOpenInventory().getTopInventory().getHolder().getClass() != BuffPanel.class) continue;
							BuffPanel buffPanel = (BuffPanel) player.getOpenInventory().getTopInventory().getHolder();
							buffPanel.setInventory();
						}
					}
				}
			}
		}.runTaskTimer(ArcticGuilds.INSTANCE, 17280, 17280);
//		}.runTaskTimer(ArcticGuilds.INSTANCE, 100, 100);

		new BukkitRunnable() {
			@Override
			public void run() {
				for(Guild guild : GuildManager.guildList) {
					if(guild.queuedReputation == 0) continue;

					GuildReputationEvent event = new GuildReputationEvent(guild, guild.queuedReputation);
					Bukkit.getPluginManager().callEvent(event);

					guild.queuedReputation = 0;
					guild.addReputationDirect(event.getTotalReputation());
				}
			}
		}.runTaskTimer(ArcticGuilds.INSTANCE, 0, 20 * 10);
	}

	public static Guild getGuild(UUID guildUUID) {
		for(Guild guild : guildList) if(guild.uuid.equals(guildUUID)) return guild;
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

	private static void sortGuilds() {
		topGuilds.clear();
		input:
		for(Guild guild : guildList) {
			for(int i = 0; i < topGuilds.size(); i++) {
				Guild topGuild = topGuilds.get(i);
				if(topGuild.reputation >= guild.reputation) continue;
				topGuilds.add(i, guild);
				continue input;
			}
			topGuilds.add(guild);
		}
	}

	public static List<Guild> getTopGuilds() {
		sortGuilds();
		return topGuilds;
	}

	public static int getRank(Guild guild) {
		for(int i = 0; i < guildList.size(); i++) {
			Guild testGuild = guildList.get(i);
			if(testGuild == guild) return i;
		}
		return -1;
	}
}

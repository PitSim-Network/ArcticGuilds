package dev.kyro.arcticguilds.controllers.objects;

import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.enums.GuildRank;
import dev.kyro.arcticguilds.events.GuildCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Guild {
	public UUID uuid;
	public String name;
	public String tag;

	public UUID ownerUUID;
	public Map<GuildMember, GuildMemberInfo> members = new HashMap<>();

	public Guild(Player player, String name) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.ownerUUID = player.getUniqueId();

		GuildMember guildMember = GuildManager.getMember(player.getUniqueId());
		guildMember.guildUUID = uuid;
		members.put(guildMember, new GuildMemberInfo(GuildRank.OWNER));

		GuildManager.guildList.add(this);

		save();
		GuildCreateEvent event = new GuildCreateEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
	}

	public Guild(String key, ConfigurationSection guildData) {
		this.uuid = UUID.fromString(key);
		this.name = guildData.getString("name");
		this.ownerUUID = UUID.fromString(guildData.getString("owner"));

		this.tag = guildData.getString("tag");

		ConfigurationSection allMemberData = guildData.getConfigurationSection("members");
		if(allMemberData == null) allMemberData = guildData.createSection("members");
		for(String uuidString : allMemberData.getKeys(false)) {
			ConfigurationSection memberData = guildData.getConfigurationSection("members." + uuidString);
			UUID playerUUID = UUID.fromString(uuidString);

			GuildMember guildMember = GuildManager.getMember(playerUUID);
			members.put(guildMember, new GuildMemberInfo(memberData));
		}

		GuildManager.guildList.add(this);
	}

	public void save() {
		FileConfiguration fullData = GuildManager.guildFile.getConfiguration();
		ConfigurationSection guildData = fullData.getConfigurationSection(uuid.toString());
		if(guildData == null) guildData = fullData.createSection(uuid.toString());
		save(guildData);
		GuildManager.guildFile.saveDataFile();
	}

	public void save(ConfigurationSection guildData) {
		guildData.set("name", name);
		guildData.set("owner", ownerUUID.toString());

		guildData.set("tag", tag);

		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			String key = "members." + entry.getKey().playerUUID;
			ConfigurationSection memberData = guildData.getConfigurationSection(key);
			if(memberData == null) memberData = guildData.createSection(key);
			entry.getKey().save();
			entry.getValue().save(memberData);
		}
	}

	public GuildMemberInfo getMemberInfo(Player player) {
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			if(!entry.getKey().playerUUID.equals(player.getUniqueId())) continue;
			return entry.getValue();
		}
		return null;
	}

	public GuildMember getMember(Player player) {
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			if(!entry.getKey().playerUUID.equals(player.getUniqueId())) continue;
			return entry.getKey();
		}
		return null;
	}

	public List<GuildMember> getMembersOfRank(GuildRank rank) {
		List<GuildMember> guildMembers = new ArrayList<>();
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			if(entry.getValue().rank != rank) continue;
			guildMembers.add(entry.getKey());
		}
		return guildMembers;
	}

	public void disband() {
		GuildManager.guildList.remove(this);
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) entry.getKey().leave();

		FileConfiguration fullData = GuildManager.guildFile.getConfiguration();
		fullData.set(uuid.toString(), null);
		GuildManager.guildFile.saveDataFile();
	}
}

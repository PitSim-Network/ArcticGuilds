package dev.kyro.arcticguilds.controllers.objects;

import dev.kyro.arcticguilds.enums.GuildRank;
import dev.kyro.arcticguilds.events.GuildCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guild {
	public UUID uuid;
	public String name;
	public String tag;

	public List<GuildMember> members;

	public Guild(Player player, String name) {
		this.uuid = uuid;
		this.name = name;

		GuildCreateEvent event = new GuildCreateEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
	}

	public Guild(ConfigurationSection guildData) {
		this.uuid = UUID.fromString(guildData.getString("uuid"));
		this.name = guildData.getString("name");
		this.tag = guildData.getString("tag");

		for(String uuidString : guildData.getConfigurationSection("members").getKeys(false)) {
			ConfigurationSection memberData = guildData.getConfigurationSection("members" + uuidString);
			UUID playerUUID = UUID.fromString(uuidString);

			GuildMember guildMember = new GuildMember(playerUUID, memberData);
			members.add(guildMember);
		}
	}

	public void save() {

	}

	public List<GuildMember> getMembersOfRank(GuildRank rank) {
		List<GuildMember> guildMembers = new ArrayList<>();
		for(GuildMember member : members) if(member.rank == rank) guildMembers.add(member);
		return guildMembers;
	}
}

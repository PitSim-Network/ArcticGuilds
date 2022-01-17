package dev.kyro.arcticguilds.controllers.objects;

import dev.kyro.arcticguilds.enums.GuildRank;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public class GuildMember {
	public UUID uuid;

//	Savable data
	public GuildRank rank;

	public GuildMember(UUID uuid, ConfigurationSection memberData) {
		this.uuid = uuid;

		this.rank = GuildRank.getRank(memberData.getString("rank"));
	}

	public void save(ConfigurationSection memberData) {
	}
}

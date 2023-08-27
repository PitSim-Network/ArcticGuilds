package net.pitsim.arcticguilds.controllers.objects;

import net.pitsim.arcticguilds.enums.GuildRank;
import org.bukkit.configuration.ConfigurationSection;

public class GuildMemberInfo {
	public GuildRank rank;

	public GuildMemberInfo() {
		this(GuildRank.INITIAL_RANK);
	}

	public GuildMemberInfo(GuildRank rank) {
		this.rank = rank;
	}

	public GuildMemberInfo(ConfigurationSection memberData) {
		this.rank = GuildRank.getRank(memberData.getString("rank"));
	}

	public void save(ConfigurationSection memberData) {
		memberData.set("rank", rank.refName);
	}
}

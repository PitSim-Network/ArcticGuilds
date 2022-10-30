package dev.kyro.arcticguilds;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuildLeaderboardData {

	public static final List<GuildLeaderboardData> guildData = new ArrayList<>();

	public final String name;
	public int reputation;
	public final ChatColor color;

	public GuildLeaderboardData(List<String> stringData, List<Integer> intData) {

		UUID uuid = UUID.fromString(stringData.get(0));
		this.name = stringData.get(1);
		this.reputation = intData.get(0);
		this.color = ChatColor.valueOf(stringData.get(2));

		guildData.add(this);
	}

	public void addReputation(int reputation) {

	}

	public void update() {

	}

	public ChatColor getColor() {
		return color;
	}

	public static GuildLeaderboardData getGuildData(int index) {
		if(index >= guildData.size()) return null;
		return guildData.get(index);
	}


}

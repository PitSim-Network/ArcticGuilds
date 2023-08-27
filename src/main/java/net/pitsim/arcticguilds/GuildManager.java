package net.pitsim.arcticguilds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class GuildManager {
	protected static final List<Guild> guildList = new ArrayList<>();
	protected static final Map<UUID, GuildMember> memberList = new HashMap<>();

	public static Guild getAndUpdateGuild(UUID uuid, String tag, ChatColor color, String name, Map<GuildBuff, Integer> buffLevels) {
		for(Guild guild : guildList) {
			if(guild.uuid.equals(uuid)) {
				guild.tag = tag;
				guild.color = color;
				guild.name = name;
				guild.buffLevels = buffLevels;
				return guild;
			}
		}

		Guild guild = new Guild(uuid, tag, color, name, buffLevels);
		guildList.add(guild);
		return guild;
	}

	public static Guild getGuild(UUID guildUUID) {
		for(Guild guild : guildList) {
			if(guild.uuid.equals(guildUUID)) {
				return guild;
			}
		}
		return null;
	}

	public static Guild getGuild(Player player) {
		GuildMember member = memberList.get(player.getUniqueId());
		if(member == null) return null;
		return member.getGuild();
	}
}

package net.pitsim.arcticguilds;

import org.bukkit.ChatColor;

import java.util.*;

public class GuildMember {

	public final UUID playerUUID;
	private final Guild guild;

	public GuildMember(UUID player, List<String> stringData, List<Integer> intData) {
		this.playerUUID = player;

		UUID guildUUID = UUID.fromString(stringData.get(0));
		String tag = stringData.get(1);
		ChatColor color = ChatColor.valueOf(stringData.get(2));
		String name = stringData.get(3);

		Map<GuildBuff, Integer> buffLevels = new HashMap<>();

		for(int i = 0; i < BuffManager.buffList.size(); i++) {
			GuildBuff buff = BuffManager.buffList.get(i);
			buffLevels.put(buff, intData.get(i));
		}

		this.guild = GuildManager.getAndUpdateGuild(guildUUID, tag, color, name, buffLevels);

		GuildManager.memberList.put(player, this);
	}

	public Guild getGuild() {
		return guild;
	}
}

package dev.kyro.arcticguilds;

import dev.kyro.arcticguilds.misc.PluginMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class GuildData {

	private static final Map<UUID, GuildData> guildData = new HashMap<>();

	private final Map<GuildBuff, Integer> buffLevels = new HashMap<>();
	public final UUID playerUUID;
	private final UUID guildUUID;
	private final String tag;
	private final ChatColor color;

	public GuildData(UUID player, List<String> stringData, List<Integer> intData) {
		this.playerUUID = player;
		this.guildUUID = UUID.fromString(stringData.get(0));
		this.tag = stringData.get(1);
		this.color = ChatColor.valueOf(stringData.get(2));

		for(int i = 0; i < BuffManager.buffList.size(); i++) {
			GuildBuff buff = BuffManager.buffList.get(i);
			buffLevels.put(buff, intData.get(i));
		}

		guildData.put(player, this);
	}

	public void addReputation(int reputation) {

		PluginMessage message = new PluginMessage().writeString("ADD REPUTATION");
		message.writeString(guildUUID.toString());
		message.writeInt(reputation);

		message.send();
	}

	public void update() {

	}

	public UUID getGuildUUID() {
		return guildUUID;
	}

	public String getTag() {
		return tag;
	}

	public ChatColor getColor() {
		return color;
	}

	public int getBuffLevel(GuildBuff buff) {
		return buffLevels.get(buff);
	}

	public static GuildData getGuildData(Player player) {
		if(!guildData.containsKey(player.getUniqueId())) return null;
		return guildData.get(player.getUniqueId());
	}

	public static GuildData getGuildData(UUID uuid) {
		if(!guildData.containsKey(uuid)) return null;
		return guildData.get(uuid);
	}


}

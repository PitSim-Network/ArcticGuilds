package net.pitsim.arcticguilds;

import net.pitsim.arcticguilds.misc.PluginMessage;
import org.bukkit.ChatColor;

import java.util.Map;
import java.util.UUID;

public class Guild {
	public final UUID uuid;
	public String tag;
	public ChatColor color;
	public String name;
	public Map<GuildBuff, Integer> buffLevels;

	public Guild(UUID uuid, String tag, ChatColor color, String name, Map<GuildBuff, Integer> buffLevels) {
		this.uuid = uuid;
		this.tag = tag;
		this.color = color;
		this.name = name;
		this.buffLevels = buffLevels;
	}

	public void update() {

	}

	public UUID getGuildUUID() {
		return uuid;
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

	public void addReputation(int reputation) {

		PluginMessage message = new PluginMessage().writeString("ADD REPUTATION");
		message.writeString(uuid.toString());
		message.writeInt(reputation);

		message.send();
	}
}

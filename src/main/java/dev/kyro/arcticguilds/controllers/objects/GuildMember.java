package dev.kyro.arcticguilds.controllers.objects;

import dev.kyro.arcticapi.data.APlayer;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticguilds.controllers.GuildManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class GuildMember {
	public UUID playerUUID;

//	Savable data
	public UUID guildUUID;

	public GuildMember(UUID playerUUID) {
		this.playerUUID = playerUUID;

		save();

		GuildManager.guildMemberList.add(this);
	}

	public GuildMember(UUID playerUUID, FileConfiguration playerData) {
		this.playerUUID = playerUUID;

		if(playerData.contains("guild")) this.guildUUID = UUID.fromString(playerData.getString("guild"));

		GuildManager.guildMemberList.add(this);
	}

	public void leave() {
		guildUUID = null;
		save();
	}

//	This writes to the player's data, not the guild's
	public void save() {
		APlayer aPlayer = APlayerData.getPlayerData(playerUUID);
		FileConfiguration playerData = aPlayer.playerData;

		if(guildUUID == null) playerData.set("guild", null); else playerData.set("guild", guildUUID.toString());

		aPlayer.save();
	}
}

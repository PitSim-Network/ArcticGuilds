package dev.kyro.arcticguilds;

import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticguilds.commands.guildcommands.*;
import dev.kyro.arcticguilds.controllers.ChatManager;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ArcticGuilds extends JavaPlugin {
	public static ArcticGuilds INSTANCE;

	@Override
	public void onEnable() {
		INSTANCE = this;

		loadConfig();
		ArcticAPI.configInit(this, "prefix", "error-prefix");
		APlayerData.init();

		registerCommands();
		registerListeners();
	}

	@Override
	public void onDisable() {
	}

	private void registerCommands() {
		GuildCommand guildCommand = new GuildCommand("guild");
		new InfoCommand(guildCommand, "info");
		new CreateCommand(guildCommand, "create");
		new DisbandCommand(guildCommand, "disband");
		new KickCommand(guildCommand, "kick");
		new InviteCommand(guildCommand, "invite");
		new UnInviteCommand(guildCommand, "uninvite");
		new JoinCommand(guildCommand, "join");
		new ChatCommand(guildCommand, "chat");
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new ChatManager(), this);
		getServer().getPluginManager().registerEvents(new GuildManager(), this);
		getServer().getPluginManager().registerEvents(new PlayerManager(), this);
	}

	private void loadConfig() {

		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}

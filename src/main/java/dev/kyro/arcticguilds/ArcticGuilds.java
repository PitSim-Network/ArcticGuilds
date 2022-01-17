package dev.kyro.arcticguilds;

import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticguilds.commands.guildcommands.CreateCommand;
import dev.kyro.arcticguilds.commands.guildcommands.GuildCommand;
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

		registerCommands();
		registerListeners();
	}

	@Override
	public void onDisable() {
	}

	private void registerCommands() {
		GuildCommand guildCommand = new GuildCommand("guild");
		guildCommand.registerCommand(new CreateCommand("create"));
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

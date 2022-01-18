package dev.kyro.arcticguilds;

import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.commands.guildcommands.*;
import dev.kyro.arcticguilds.commands.guildcommands.bank.BalanceCommand;
import dev.kyro.arcticguilds.commands.guildcommands.bank.DepositCommand;
import dev.kyro.arcticguilds.commands.guildcommands.bank.WithdrawalCommand;
import dev.kyro.arcticguilds.controllers.ChatManager;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public class ArcticGuilds extends JavaPlugin {
	public static ArcticGuilds INSTANCE;
	public static Economy VAULT = null;

	public static DecimalFormat decimalFormat = new DecimalFormat("#,##0");

	@Override
	public void onEnable() {
		INSTANCE = this;

		loadConfig();
		ArcticAPI.configInit(this, "prefix", "error-prefix");
		APlayerData.init();

		if(!setupEconomy()) {
			AOutput.log(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

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
		new ChatCommand(guildCommand, "chat");
		new UpgradesCommand(guildCommand, "upgrades");

		new JoinCommand(guildCommand, "join");
		new InviteCommand(guildCommand, "invite");
		new UnInviteCommand(guildCommand, "uninvite");
		new KickCommand(guildCommand, "kick");

		new BalanceCommand(guildCommand, "bal");
		new DepositCommand(guildCommand, "deposit");
		new WithdrawalCommand(guildCommand, "withdrawal");
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

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		VAULT = rsp.getProvider();
		return VAULT != null;
	}
}

package dev.kyro.arcticguilds;

import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.commands.admin.GuildAdminCommand;
import dev.kyro.arcticguilds.commands.admin.ReputationCommand;
import dev.kyro.arcticguilds.commands.guildcommands.*;
import dev.kyro.arcticguilds.controllers.*;
import dev.kyro.arcticguilds.guildbuffs.*;
import dev.kyro.arcticguilds.guildupgrades.BankLimit;
import dev.kyro.arcticguilds.guildupgrades.GuildSize;
import dev.kyro.arcticguilds.guildupgrades.ReputationIncrease;
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
		registerBuffs();
		registerUpgrades();
		registerListeners();
	}

	@Override
	public void onDisable() {
	}

	private void registerCommands() {
		GuildCommand guildCommand = new GuildCommand("guild");
		new HelpCommand(guildCommand, "help");
		new InfoCommand(guildCommand, "info");
		new CreateCommand(guildCommand, "create");
		new TransferCommand(guildCommand, "transfer");
		new DisbandCommand(guildCommand, "disband");
		new ChatCommand(guildCommand, "chat");
		new MenuCommand(guildCommand, "menu");
		new TagCommand(guildCommand, "tag");

		new InviteCommand(guildCommand, "invite");
		new JoinCommand(guildCommand, "join");
		new PromoteCommand(guildCommand, "promote");
		new DemoteCommand(guildCommand, "demote");
		new LeaveCommand(guildCommand, "leave");
		new KickCommand(guildCommand, "kick");

		new BalanceCommand(guildCommand, "bal");
		new DepositCommand(guildCommand, "deposit");
		new WithdrawalCommand(guildCommand, "withdrawal");

		GuildAdminCommand adminCommand = new GuildAdminCommand("gadmin");
		new ReputationCommand(adminCommand, "rep");
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new ChatManager(), this);
		getServer().getPluginManager().registerEvents(new BuffManager(), this);
		getServer().getPluginManager().registerEvents(new UpgradeManager(), this);
		getServer().getPluginManager().registerEvents(new GuildManager(), this);
		getServer().getPluginManager().registerEvents(new PlayerManager(), this);
		getServer().getPluginManager().registerEvents(new PermissionManager(), this);
	}

	private void registerBuffs() {
		BuffManager.registerBuff(new DamageBuff());
		BuffManager.registerBuff(new DefenceBuff());
		BuffManager.registerBuff(new XPBuff());
		BuffManager.registerBuff(new GoldBuff());
//		BuffManager.registerBuff(new DispersionBuff());
		BuffManager.registerBuff(new RenownBuff());
	}

	private void registerUpgrades() {
		UpgradeManager.registerUpgrade(new GuildSize());
		UpgradeManager.registerUpgrade(new BankLimit());
		UpgradeManager.registerUpgrade(new ReputationIncrease());
//		UpgradeManager.registerUpgrade(new GuildBuffs());
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

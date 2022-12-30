package dev.kyro.arcticguilds;

import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.guildbuffs.*;
import dev.kyro.arcticguilds.misc.PluginMessageManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import septogeddon.pluginquery.PluginQuery;
import septogeddon.pluginquery.api.QueryMessenger;

import java.text.DecimalFormat;

public class ArcticGuilds extends JavaPlugin {
	public static ArcticGuilds INSTANCE;

	public static DecimalFormat decimalFormat = new DecimalFormat("#,##0");

	@Override
	public void onEnable() {
		INSTANCE = this;

		loadConfig();
		ArcticAPI.configInit(this, "prefix", "error-prefix");
		APlayerData.init();

		getServer().getPluginManager().registerEvents(new ProxyMessaging(), this);

		BuffManager.registerBuff(new DamageBuff());
		BuffManager.registerBuff(new DefenceBuff());
		BuffManager.registerBuff(new XPBuff());
		BuffManager.registerBuff(new GoldBuff());
		BuffManager.registerBuff(new RenownBuff());

		QueryMessenger messenger = PluginQuery.getMessenger();
		messenger.getEventBus().registerListener(new PluginMessageManager());
	}

	@Override
	public void onDisable() {
	}


	private void loadConfig() {

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}

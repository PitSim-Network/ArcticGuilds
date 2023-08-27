package net.pitsim.arcticguilds;

import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.data.APlayerData;
import dev.kyro.arcticguilds.guildbuffs.*;
import net.pitsim.arcticguilds.misc.PluginMessageManager;
import net.pitsim.arcticguilds.guildbuffs.*;
import org.bukkit.plugin.java.JavaPlugin;
import septogeddon.pluginquery.PluginQuery;
import septogeddon.pluginquery.api.QueryMessenger;

public class ArcticGuilds extends JavaPlugin {
	public static ArcticGuilds INSTANCE;

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

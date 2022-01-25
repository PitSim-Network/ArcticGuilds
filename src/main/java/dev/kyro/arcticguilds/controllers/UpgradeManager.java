package dev.kyro.arcticguilds.controllers;

import dev.kyro.arcticguilds.controllers.objects.GuildUpgrade;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class UpgradeManager implements Listener {
	public static List<GuildUpgrade> upgradeList = new ArrayList<>();

	public static void registerUpgrade(GuildUpgrade upgrade) {
		upgradeList.add(upgrade);
	}

	public static GuildUpgrade getUpgrade(String refName) {
		for(GuildUpgrade upgrade : upgradeList) if(upgrade.refName.equalsIgnoreCase(refName)) return upgrade;
		return null;
	}
}

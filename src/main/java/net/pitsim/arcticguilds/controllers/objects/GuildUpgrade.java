package net.pitsim.arcticguilds.controllers.objects;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.arcticguilds.ArcticGuilds;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GuildUpgrade implements Listener {
	public String displayName;
	public String refName;
	public int maxLevel;

	public static List<Integer> slots = new ArrayList<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16));
	public int slot;

	public GuildUpgrade(String displayName, String refName, int maxLevel) {
		this.displayName = displayName;
		this.refName = refName;
		this.maxLevel = maxLevel;

		slot = slots.remove(0);

		Bukkit.getPluginManager().registerEvents(this, ArcticGuilds.INSTANCE);
	}

	public abstract int getCost(int level);
	public abstract ItemStack getBaseStack(Guild guild, int level);

	public ItemStack getDisplayStack(Guild guild, int level) {
		ItemStack itemStack = getBaseStack(guild, level);
		itemStack.setAmount(level);
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> lore = itemMeta.getLore();

		ALoreBuilder preLore = new ALoreBuilder();
		for(String line : preLore.getLore()) lore.add(0, line);

		ALoreBuilder postLore = new ALoreBuilder();
		postLore.addLore("");
		if(level == maxLevel) {
			if(maxLevel == 1) {
				postLore.addLore("&aUnlocked!");
			} else {
				postLore.addLore("&aMax tier unlocked!");
			}
		} else if(level == 0) {
			postLore.addLore("&7Cost: &6" + ArcticGuilds.decimalFormat.format(getCost(level + 1)) + "g");
			postLore.addLore("&7Bank Funds: &6" + ArcticGuilds.decimalFormat.format(guild.getBalance()) + "g");
			postLore.addLore("", "&eClick to purchase!");
		} else {
			postLore.addLore("&7Cost: &6" + ArcticGuilds.decimalFormat.format(getCost(level + 1)) + "g");
			postLore.addLore("&7Bank Funds: &6" + ArcticGuilds.decimalFormat.format(guild.getBalance()) + "g");
			postLore.addLore("", "&eClick to upgrade!");
		}
		lore.addAll(postLore.getLore());

		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}

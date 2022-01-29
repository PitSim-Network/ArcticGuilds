package dev.kyro.arcticguilds.guildupgrades;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.misc.AUtil;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildUpgrade;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DeathTP extends GuildUpgrade {
	public DeathTP() {
		super("&9Death TP", "tp", 1);
	}

	@Override
	public int getCost(int level) {
		return 10_000_000;
	}

	@Override
	public ItemStack getBaseStack(Guild guild, int level) {
		ALoreBuilder lore = new ALoreBuilder();
		if(level != 0) {
			lore.addLore("&7Current: &6" + ArcticGuilds.decimalFormat.format(guild.getMaxBank()) + "g bank size", "&7Tier: &a" + AUtil.toRoman(level), "");
		}
		lore.addLore("&7Each tier:", "&6x10 &7bank size");

		ItemStack itemStack = new AItemStackBuilder(Material.CHEST)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}
}

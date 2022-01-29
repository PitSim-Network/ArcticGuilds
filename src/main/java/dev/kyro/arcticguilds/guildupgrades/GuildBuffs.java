package dev.kyro.arcticguilds.guildupgrades;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildUpgrade;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuildBuffs extends GuildUpgrade {
	public GuildBuffs() {
		super("&bGuild Buffs", "buffs", 1);
	}

	@Override
	public int getCost(int level) {
		return 1_000_000;
	}

	@Override
	public ItemStack getBaseStack(Guild guild, int level) {
		ALoreBuilder lore = new ALoreBuilder();
		lore.addLore("&7Unlock &bguild upgrades &7(found in /guild menu)");

		ItemStack itemStack = new AItemStackBuilder(Material.CHEST)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}
}

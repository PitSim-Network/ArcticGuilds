package dev.kyro.arcticguilds.guildupgrades;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.misc.AUtil;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildUpgrade;
import dev.kyro.arcticguilds.events.GuildReputationEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class ReputationIncrease extends GuildUpgrade {
	public ReputationIncrease() {
		super("&fIncreased Reputation", "reputation", 5);
	}

	@EventHandler
	public void onReputation(GuildReputationEvent event) {
		Guild guild = event.getGuild();
		int reputationLevel = guild.getLevel(this);
		if(reputationLevel == 0) return;
		event.addMultiplier(1 + reputationLevel / 5.0);
	}

	@Override
	public int getCost(int level) {
		switch(level) {
			case 1:
				return 1_000_000;
			case 2:
				return 5_000_000;
			case 3:
				return 10_000_000;
			case 4:
				return 15_000_000;
			case 5:
				return 20_000_000;
		}
		return -1;
	}

	@Override
	public ItemStack getBaseStack(Guild guild, int level) {
		ALoreBuilder lore = new ALoreBuilder();
		if(level != 0) {
			lore.addLore("&7Current: &f+" + (level * 20) + "% reputation", "&7Tier: &a" + AUtil.toRoman(level), "");
		}
		lore.addLore("&7Each tier:", "&f+20% &7reputation");

		ItemStack itemStack = new AItemStackBuilder(Material.NETHER_STAR)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}
}

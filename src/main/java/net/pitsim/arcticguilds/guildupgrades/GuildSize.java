package net.pitsim.arcticguilds.guildupgrades;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.arcticguilds.controllers.objects.Guild;
import net.pitsim.arcticguilds.controllers.objects.GuildUpgrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class GuildSize extends GuildUpgrade {
	public GuildSize() {
		super("&aIncreased Guild Size", "size", 5);
	}

	@Override
	public int getCost(int level) {
		switch(level) {
			case 1:
				return 1_000_000;
			case 2:
				return 10_000_000;
			case 3:
				return 25_000_000;
			case 4:
				return 50_000_000;
			case 5:
				return 100_000_000;
		}
		return -1;
	}

	@Override
	public ItemStack getBaseStack(Guild guild, int level) {
		ALoreBuilder lore = new ALoreBuilder();
		if(level != 0) {
			lore.addLore("&7Current: &a+" + level + " member slots", "&7Tier: &a" + AUtil.toRoman(level), "");
		}
		lore.addLore("&7Each tier:", "&a+1 &7member slot");

		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		OfflinePlayer guildOwner = Bukkit.getOfflinePlayer(guild.ownerUUID);
		skullMeta.setOwner(guildOwner.getName());
		itemStack.setItemMeta(skullMeta);
		new AItemStackBuilder(itemStack)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}
}

package dev.kyro.arcticguilds.guildbuffs;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.misc.AUtil;
import dev.kyro.arcticguilds.controllers.BuffManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildBuff;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RenownBuff extends GuildBuff {
	public RenownBuff() {
		super("&eRenown Buff", "renown", ChatColor.YELLOW);
	}

	@Override
	public ItemStack getDisplayItem(Guild guild, int level) {
		ALoreBuilder lore = new ALoreBuilder();
		if(level != 0) {
			lore.addLore("&7Tier: &a" + AUtil.toRoman(level), "");
			Map<SubBuff, Double> buffMap = BuffManager.getAllBuffs(guild, level).get(this);
			for(Map.Entry<SubBuff, Double> entry : buffMap.entrySet()) {
				lore.addLore(chatColor + entry.getKey().getDisplayString(entry.getValue()));
			}
		}

		ItemStack itemStack = new AItemStackBuilder(Material.BEACON)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}

	@Override
	public void addBuffs() {
		SubBuff renownSub = new SubBuff("&e+%amount%% &7more passive renown");

		addSubBuff(1, renownSub, 12.5);
		addSubBuff(2, renownSub, 12.5);
		addSubBuff(3, renownSub, 12.5);
		addSubBuff(4, renownSub, 12.5);
		addSubBuff(5, renownSub, 12.5);
		addSubBuff(6, renownSub, 12.5);
		addSubBuff(7, renownSub, 12.5);
		addSubBuff(8, renownSub, 12.5);
	}
}

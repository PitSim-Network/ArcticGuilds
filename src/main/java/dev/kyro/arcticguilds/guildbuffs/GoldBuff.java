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

public class GoldBuff extends GuildBuff {
	public GoldBuff() {
		super("&6Gold Buff", "gold", ChatColor.GOLD);
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

		ItemStack itemStack = new AItemStackBuilder(Material.GOLD_INGOT)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}

	@Override
	public void addBuffs() {
		SubBuff goldSub = new SubBuff("&6+%amount%% gold &7from kills");

		addSubBuff(1, goldSub, 1);
		addSubBuff(2, goldSub, 1);
		addSubBuff(3, goldSub, 1);
		addSubBuff(4, goldSub, 1);
		addSubBuff(5, goldSub, 1);
		addSubBuff(6, goldSub, 1);
		addSubBuff(7, goldSub, 1);
		addSubBuff(8, goldSub, 1);
	}
}
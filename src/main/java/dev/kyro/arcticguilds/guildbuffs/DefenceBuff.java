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

public class DefenceBuff extends GuildBuff {
	public DefenceBuff() {
		super("&9Defence Buff", "defence", ChatColor.BLUE);
	}

	@Override
	public ItemStack getDisplayItem(Guild guild, int level) {
		ALoreBuilder lore = new ALoreBuilder();
		if(level != 0) {
			lore.addLore("&7Tier: &a" + AUtil.toRoman(level), "");
			Map<SubBuff, Double> buffMap = BuffManager.getAllBuffs(level).get(this);
			for(Map.Entry<SubBuff, Double> entry : buffMap.entrySet()) {
				lore.addLore(chatColor + entry.getKey().getDisplayString(entry.getValue()));
			}
		}

		ItemStack itemStack = new AItemStackBuilder(Material.DIAMOND_CHESTPLATE)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}

	@Override
	public void addBuffs() {
		SubBuff defenceSub = new SubBuff("defence", "&9-%amount%% &7damage");
		SubBuff trueDefenceSub = new SubBuff("truedefence", "&b-%amount%\u2764 true damage");

		addSubBuff(1, defenceSub, 1);
		addSubBuff(2, defenceSub, 1);
		addSubBuff(3, defenceSub, 1);
		addSubBuff(4, defenceSub, 1);
		addSubBuff(4, trueDefenceSub, 0.2);
		addSubBuff(5, defenceSub, 1);
		addSubBuff(6, defenceSub, 1);
		addSubBuff(7, defenceSub, 1);
		addSubBuff(8, defenceSub, 1);
		addSubBuff(8, trueDefenceSub, 0.2);
	}
}

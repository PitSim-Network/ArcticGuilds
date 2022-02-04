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

public class XPBuff extends GuildBuff {
	public XPBuff() {
		super("&bXP Buff", "xp",
				new ALoreBuilder("&7Increased xp from kills").getLore(), ChatColor.AQUA);
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

		ItemStack itemStack = new AItemStackBuilder(Material.INK_SACK, 1, 12)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}

	@Override
	public void addBuffs() {
		SubBuff xpSub = new SubBuff("xp", "&b+%amount%% XP &7from kills");
		SubBuff maxXPSub = new SubBuff("maxxp", "&b+%amount%% max XP &7from kills");

		addSubBuff(1, xpSub, 1);
		addSubBuff(1, maxXPSub, 1);
		addSubBuff(2, xpSub, 1);
		addSubBuff(2, maxXPSub, 1);
		addSubBuff(3, xpSub, 1);
		addSubBuff(3, maxXPSub, 1);
		addSubBuff(4, xpSub, 1);
		addSubBuff(4, maxXPSub, 1);
		addSubBuff(5, xpSub, 1);
		addSubBuff(5, maxXPSub, 1);
		addSubBuff(6, xpSub, 1);
		addSubBuff(6, maxXPSub, 1);
		addSubBuff(7, xpSub, 1);
		addSubBuff(7, maxXPSub, 1);
		addSubBuff(8, xpSub, 1);
		addSubBuff(8, maxXPSub, 1);
	}
}

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

public class DamageBuff extends GuildBuff {
	public DamageBuff() {
		super("&cDamage Buff", "damage", ChatColor.RED);
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

		ItemStack itemStack = new AItemStackBuilder(Material.DIAMOND_SWORD)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}

	@Override
	public void addBuffs() {
		SubBuff damageSub = new SubBuff("&c+%amount%% &7damage");
		SubBuff trueDamageSub = new SubBuff("&4+%amount%\u2764 true damage");

		addSubBuff(1, damageSub, 1);
		addSubBuff(2, damageSub, 1);
		addSubBuff(3, damageSub, 1);
		addSubBuff(4, damageSub, 1);
		addSubBuff(4, trueDamageSub, 0.2);
		addSubBuff(5, damageSub, 1);
		addSubBuff(6, damageSub, 1);
		addSubBuff(7, damageSub, 1);
		addSubBuff(8, damageSub, 1);
		addSubBuff(8, trueDamageSub, 0.2);
	}
}

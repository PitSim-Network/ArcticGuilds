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

public class DispersionBuff extends GuildBuff {
	public DispersionBuff() {
		super("&dDispersion Buff", "renown", ChatColor.LIGHT_PURPLE);
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

		ItemStack itemStack = new AItemStackBuilder(Material.WEB)
				.setName(displayName)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}

	@Override
	public void addBuffs() {
		SubBuff dispersionSub = new SubBuff("&d+%amount%% &7disperse of attacks in middle");

		addSubBuff(1, dispersionSub, 5);
		addSubBuff(2, dispersionSub, 5);
		addSubBuff(3, dispersionSub, 5);
		addSubBuff(4, dispersionSub, 5);
		addSubBuff(5, dispersionSub, 5);
		addSubBuff(6, dispersionSub, 5);
		addSubBuff(7, dispersionSub, 5);
		addSubBuff(8, dispersionSub, 5);
	}
}

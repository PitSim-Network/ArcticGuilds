package dev.kyro.arcticguilds.guildbuffs;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticguilds.GuildBuff;
import net.md_5.bungee.api.ChatColor;

public class DamageBuff extends GuildBuff {
	public DamageBuff() {
		super("&cDamage Buff", "damage",
				new ALoreBuilder("&7Increased damage vs other guild members").getLore(), ChatColor.RED);
		firstLevelCost = 2;
	}

	@Override
	public void addBuffs() {
		SubBuff damageSub = new SubBuff("damage", "&c+%amount%% &7damage vs other guilds");
//		SubBuff trueDamageSub = new SubBuff("truedamage", "&4+%amount%\u2764 true damage");

		addSubBuff(1, damageSub, 1);
		addSubBuff(2, damageSub, 1);
		addSubBuff(3, damageSub, 1);
		addSubBuff(4, damageSub, 1);
		addSubBuff(5, damageSub, 1);
		addSubBuff(6, damageSub, 1);
		addSubBuff(7, damageSub, 1);
		addSubBuff(8, damageSub, 1);
	}
}

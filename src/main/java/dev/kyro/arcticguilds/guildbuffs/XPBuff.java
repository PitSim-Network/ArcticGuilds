package dev.kyro.arcticguilds.guildbuffs;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticguilds.GuildBuff;
import net.md_5.bungee.api.ChatColor;

public class XPBuff extends GuildBuff {
	public XPBuff() {
		super("&bXP Buff", "xp",
				new ALoreBuilder("&7Increased xp from kills").getLore(), ChatColor.AQUA);
		firstLevelCost = 3;
	}

	@Override
	public void addBuffs() {
		SubBuff xpSub = new SubBuff("xp", "&b+%amount%% XP &7from kills");
		SubBuff maxXPSub = new SubBuff("maxxp", "&b+%amount%% max XP &7from kills");

		addSubBuff(1, xpSub, 5);
		addSubBuff(2, maxXPSub, 5);
		addSubBuff(3, xpSub, 5);
		addSubBuff(4, maxXPSub, 5);
		addSubBuff(5, xpSub, 5);
		addSubBuff(6, maxXPSub, 5);
		addSubBuff(7, xpSub, 5);
		addSubBuff(8, maxXPSub, 5);
	}
}

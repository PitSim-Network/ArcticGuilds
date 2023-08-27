package net.pitsim.arcticguilds.guildbuffs;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.arcticguilds.GuildBuff;
import net.md_5.bungee.api.ChatColor;

public class DefenceBuff extends GuildBuff {
	public DefenceBuff() {
		super("&9Defence Buff", "defence",
				new ALoreBuilder("&7Decreased damage from other guild members").getLore(), ChatColor.BLUE);
		firstLevelCost = 2;
	}

	@Override
	public void addBuffs() {
		SubBuff defenceSub = new SubBuff("defence", "&9-%amount%% &7damage from other guilds");
//		SubBuff trueDefenceSub = new SubBuff("truedefence", "&b-%amount%\u2764 true damage");

		addSubBuff(1, defenceSub, 1);
		addSubBuff(2, defenceSub, 1);
		addSubBuff(3, defenceSub, 1);
		addSubBuff(4, defenceSub, 1);
		addSubBuff(5, defenceSub, 1);
		addSubBuff(6, defenceSub, 1);
		addSubBuff(7, defenceSub, 1);
		addSubBuff(8, defenceSub, 1);
	}
}

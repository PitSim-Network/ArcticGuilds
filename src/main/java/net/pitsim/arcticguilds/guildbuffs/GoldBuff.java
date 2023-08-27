package net.pitsim.arcticguilds.guildbuffs;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import net.pitsim.arcticguilds.GuildBuff;
import net.md_5.bungee.api.ChatColor;

public class GoldBuff extends GuildBuff {
	public GoldBuff() {
		super("&6Gold Buff", "gold",
				new ALoreBuilder("&7Increased gold from kills").getLore(), ChatColor.GOLD);
		firstLevelCost = 3;
	}

	@Override
	public void addBuffs() {
		SubBuff goldSub = new SubBuff("gold", "&6+%amount%% gold &7from kills");

		addSubBuff(1, goldSub, 2);
		addSubBuff(2, goldSub, 2);
		addSubBuff(3, goldSub, 2);
		addSubBuff(4, goldSub, 4);
		addSubBuff(5, goldSub, 2);
		addSubBuff(6, goldSub, 2);
		addSubBuff(7, goldSub, 2);
		addSubBuff(8, goldSub, 4);
	}
}

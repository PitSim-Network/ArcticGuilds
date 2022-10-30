package dev.kyro.arcticguilds.guildbuffs;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticguilds.GuildBuff;
import net.md_5.bungee.api.ChatColor;

public class RenownBuff extends GuildBuff {
	public RenownBuff() {
		super("&eRenown Buff", "renown",
				new ALoreBuilder("&7Increased passive renown").getLore(), ChatColor.YELLOW);
	}

	@Override
	public void addBuffs() {
		SubBuff renownSub = new SubBuff("renown", "&e+%amount%% &7more passive renown");

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

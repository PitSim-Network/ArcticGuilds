package net.pitsim.arcticguilds.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import org.bukkit.entity.Player;

public class HelpGUI extends AGUI {

	public HelpGUI(Player player) {
		super(player);

		setHomePanel(new HelpPanel(this));
	}
}

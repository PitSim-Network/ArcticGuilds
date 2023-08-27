package net.pitsim.arcticguilds;

import dev.kyro.arcticapi.gui.AGUI;
import org.bukkit.entity.Player;

import java.util.List;

public class PreparedGUI extends AGUI {
	public PreparedPanel panel;

	public PreparedGUI(Player player, String name, int rows, List<String> items) {
		super(player);

		panel = new PreparedPanel(this, name, rows, items);
		setHomePanel(panel);

	}


}

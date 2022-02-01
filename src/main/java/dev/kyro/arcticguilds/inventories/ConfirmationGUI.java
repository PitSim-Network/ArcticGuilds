package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.gui.AGUI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ConfirmationGUI extends AGUI {

	public ConfirmationGUI(Player player, BukkitRunnable disband, ALoreBuilder yesLore, ALoreBuilder noLore) {
		super(player);
		setHomePanel(new ConfirmationPanel(this, disband, yesLore, noLore));
	}
}

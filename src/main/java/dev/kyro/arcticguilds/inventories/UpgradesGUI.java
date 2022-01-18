package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import org.bukkit.entity.Player;

public class UpgradesGUI extends AGUI {
	public Guild guild;

	public UpgradesGUI(Player player, Guild guild) {
		super(player);
		this.guild = guild;

		setHomePanel(new UpgradesPanel(this));
	}
}

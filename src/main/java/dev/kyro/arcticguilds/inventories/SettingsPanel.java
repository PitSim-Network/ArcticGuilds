package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticguilds.ArcticGuilds;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SettingsPanel extends AGUIPanel {
	public MenuGUI menuGUI;

	public SettingsPanel(AGUI gui) {
		super(gui);
		this.menuGUI = (MenuGUI) gui;
	}

	@Override
	public String getName() {
		return ChatColor.GRAY + ((MenuGUI) gui).guild.name + " Guild Settings";
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	public void onClick(InventoryClickEvent event) { }

	@Override
	public void onOpen(InventoryOpenEvent event) { }

	@Override
	public void onClose(InventoryCloseEvent event) {
		new BukkitRunnable() {
			@Override
			public void run() {
				openPreviousGUI();
			}
		}.runTaskLater(ArcticGuilds.INSTANCE, 1L);
	}
}

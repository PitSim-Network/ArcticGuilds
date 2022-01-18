package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class UpgradesPanel extends AGUIPanel {
	public UpgradesGUI upgradesGUI;

	public UpgradesPanel(AGUI gui) {
		super(gui);
		this.upgradesGUI = (UpgradesGUI) gui;
	}

	@Override
	public String getName() {
		return ((UpgradesGUI) gui).guild.name + " Upgrades";
	}

	@Override
	public int getRows() {
		return 0;
	}

	@Override
	public void onClick(InventoryClickEvent inventoryClickEvent) {

	}

	@Override
	public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

	}

	@Override
	public void onClose(InventoryCloseEvent inventoryCloseEvent) {

	}
}

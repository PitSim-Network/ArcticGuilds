package dev.kyro.arcticguilds;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticguilds.misc.DummyItemStack;
import dev.kyro.arcticguilds.misc.PluginMessage;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.List;

public class PreparedPanel extends AGUIPanel {

	public String name;
	public int rows;

	public PreparedPanel(AGUI gui, String name, int rows, List<String> items) {
		super(gui, true);

		this.name = name;
		this.rows = rows;

		buildInventory();

		for(int i = 0; i < items.size() - 1; i++) {
			DummyItemStack dummyItemStack = DummyItemStack.fromString(items.get(i));
			getInventory().setItem(i, dummyItemStack.toItemStack());
		}
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public void onClick(InventoryClickEvent inventoryClickEvent) {
		PluginMessage message = new PluginMessage();
		message.writeString("INVENTORY CLICK");
		message.writeString(inventoryClickEvent.getWhoClicked().getUniqueId().toString());
		message.writeString(inventoryClickEvent.getInventory().getName());
		message.writeInt(inventoryClickEvent.getSlot());
		message.writeString(DummyItemStack.fromItemStack(inventoryClickEvent.getCurrentItem()).toString());

		message.send();
	}

	@Override
	public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

	}

	@Override
	public void onClose(InventoryCloseEvent inventoryCloseEvent) {

		PluginMessage message = new PluginMessage();
		message.writeString("INVENTORY CLOSE");
		message.writeString(inventoryCloseEvent.getPlayer().getUniqueId().toString());
		message.writeString(inventoryCloseEvent.getInventory().getName());
		message.send();
	}
}

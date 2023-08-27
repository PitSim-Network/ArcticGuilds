package net.pitsim.arcticguilds.inventories;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import net.pitsim.arcticguilds.ArcticGuilds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ConfirmationPanel extends AGUIPanel {
	public AGUI menuGUI;
	public BukkitRunnable callback;

	public ConfirmationPanel(AGUI gui, BukkitRunnable callback, ALoreBuilder yesLore, ALoreBuilder noLore) {
		super(gui);
		this.menuGUI = gui;
		this.callback = callback;

		ItemStack yes = new AItemStackBuilder(Material.STAINED_CLAY, 1, 5)
				.setName("&a&lCONFIRM")
				.setLore(yesLore)
				.getItemStack();
		getInventory().setItem(11, yes);

		ItemStack no = new AItemStackBuilder(Material.STAINED_CLAY, 1, 14)
				.setName("&c&lDECLINE")
				.setLore(noLore)
				.getItemStack();
		getInventory().setItem(15, no);
	}

	@Override
	public String getName() {
		return ChatColor.GRAY + "Confirmation GUI";
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		if(event.getClickedInventory().getHolder() != this) return;
		int slot = event.getSlot();
		if(slot == 11) {
			callback.run();
			player.closeInventory();
		} else if(slot == 15) {
			player.closeInventory();
		}
	}

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

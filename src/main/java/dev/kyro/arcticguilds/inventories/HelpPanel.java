package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class HelpPanel extends AGUIPanel {
	public HelpGUI helpGUI;

	public HelpPanel(AGUI gui) {
		super(gui);
		this.helpGUI = (HelpGUI) gui;

		inventoryBuilder.createBorder(Material.STAINED_GLASS_PANE, 7);

		ItemStack general = new AItemStackBuilder(Material.PAPER)
				.setName("&f&lWhat are Guilds?")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(10, general);

		ItemStack reputation = new AItemStackBuilder(Material.DIAMOND)
				.setName("&b&lReputation System")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(11, reputation);

		ItemStack upgrades = new AItemStackBuilder(Material.ANVIL)
				.setName("&2&lUpgrades")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(12, upgrades);

		ItemStack rewards = new AItemStackBuilder(Material.FEATHER)
				.setName("&e&lRewards")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(13, rewards);

		ItemStack combat = new AItemStackBuilder(Material.DIAMOND_SWORD)
				.setName("&9&lCombat")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(14, combat);

		ItemStack joinLeave = new AItemStackBuilder(Material.HOPPER)
				.setName("&7&lJoining and Leaving")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(15, joinLeave);
	}

	@Override
	public String getName() {
		return ChatColor.GRAY + "Guild Help";
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
	public void onClose(InventoryCloseEvent event) { }
}

package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticguilds.misc.Constants;
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
						"&7Guilds allow players to group up",
						"&7to fight, streak, and hang out",
						"",
						"&7Being a part of a guild comes with"
				))
				.getItemStack();
		getInventory().setItem(10, general);

		ItemStack reputation = new AItemStackBuilder(Material.DIAMOND)
				.setName("&b&lReputation System")
				.setLore(new ALoreBuilder(
						"&7Guild reputation measures the strength",
						"&7of a guild and can be earned from a",
						"&7variety of tasks",
						"",
						"&7Each 1,000 reputation gives a reputation point",
						"&7which can be used to activate specific buffs",
						"",
						"&7Reputation Sources:",
						"&7 * Being active",
						"&7 * Killing other guilds",
						"&7 * Making other guilds lose feathers",
						"&7 * Streaking (WIP--NOT IMPLEMENTED)"
				))
				.getItemStack();
		getInventory().setItem(11, reputation);

		ItemStack upgrades = new AItemStackBuilder(Material.ANVIL)
				.setName("&2&lUpgrades")
				.setLore(new ALoreBuilder(
						"&7Unlike reputation buffs, upgrades are permanent.",
						"&7Upgrades can be found in the upgrade GUI and improve",
						"&7your guild in many ways",
						"",
						"&7To purchase an upgrade, have a " + Constants.UPGRADES_PERMISSION.displayName + " purchase a upgrade",
						"&7and ensure that there is enough money in the guild bank"
				))
				.getItemStack();
		getInventory().setItem(12, upgrades);

		ItemStack rewards = new AItemStackBuilder(Material.FEATHER)
				.setName("&e&lRewards")
				.setLore(new ALoreBuilder(
						"&7Rewards will most likely be given out",
						"&7in the form of feathers to the best performing",
						"&7guilds (Feature WIP)"
				))
				.getItemStack();
		getInventory().setItem(13, rewards);

		ItemStack joinLeave = new AItemStackBuilder(Material.HOPPER)
				.setName("&7&lJoining and Leaving")
				.setLore(new ALoreBuilder(
						"&7Joining, leaving, creating, and disbanding are",
						"&7all actions that have a long cooldown. This",
						"&7ensures that players don't guild hop",
						"",
						"Reference the /guild command",
						"&7to see commands for these actions"
				))
				.getItemStack();
		getInventory().setItem(14, joinLeave);
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

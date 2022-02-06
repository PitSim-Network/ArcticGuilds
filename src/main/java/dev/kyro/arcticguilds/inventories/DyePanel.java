package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.misc.Misc;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class DyePanel extends AGUIPanel {
	public MenuGUI menuGUI;

	public DyePanel(AGUI gui) {
		super(gui);
		this.menuGUI = (MenuGUI) gui;

		for(int i = 0; i < 16; i++) {
			ItemStack itemStack = new ItemStack(Material.BANNER);
			BannerMeta bannerMeta = (BannerMeta) itemStack.getItemMeta();
			bannerMeta.setBaseColor(DyeColor.values()[i]);
			itemStack.setItemMeta(bannerMeta);

			getInventory().setItem(i, itemStack);
		}
	}

	@Override
	public String getName() {
		return ChatColor.GRAY + ((MenuGUI) gui).guild.name + " Banner Color";
	}

	@Override
	public int getRows() {
		return 2;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		if(event.getClickedInventory().getHolder() != this) return;
		ItemStack clickedItem = event.getCurrentItem();
		if(Misc.isAirOrNull(clickedItem) || clickedItem.getType() != Material.BANNER) return;
		BannerMeta bannerMeta = (BannerMeta) clickedItem.getItemMeta();
		menuGUI.guild.bannerColor = bannerMeta.getBaseColor().getDyeData();
		menuGUI.guild.save();
		menuGUI.menuPanel.setInventory();
		player.closeInventory();
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

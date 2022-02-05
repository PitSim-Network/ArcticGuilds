package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.data.AConfig;
import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.PermissionManager;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import dev.kyro.arcticguilds.misc.ColorConverter;
import dev.kyro.arcticguilds.misc.Constants;
import dev.kyro.arcticguilds.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public class MenuPanel extends AGUIPanel {
	public MenuGUI menuGUI;

	public MenuPanel(AGUI gui) {
		super(gui);
		this.menuGUI = (MenuGUI) gui;

		inventoryBuilder.createBorder(Material.STAINED_GLASS_PANE, 7)
				.setSlots(Material.STAINED_GLASS_PANE, 7, 11);

		ItemStack buffs = new AItemStackBuilder(Material.BEACON)
				.setName("&b&lGuild Buffs")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(12, buffs);

		ItemStack upgrades = new AItemStackBuilder(Material.ANVIL)
				.setName("&2&lGuild Upgrades")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(13, upgrades);

		ItemStack shop = new AItemStackBuilder(Material.DOUBLE_PLANT)
				.setName("&e&lGuild Shop")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(14, shop);

		ItemStack settings = new AItemStackBuilder(Material.REDSTONE_COMPARATOR)
				.setName("&f&lGuild Settings")
				.setLore(new ALoreBuilder(

				))
				.getItemStack();
		getInventory().setItem(15, settings);

		setInventory();
	}

	@Override
	public String getName() {
		return ChatColor.GRAY + ((MenuGUI) gui).guild.name + " Guild Menu";
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		if(event.getClickedInventory().getHolder() != this) return;
		int slot = event.getSlot();
		if(slot == 10) {
			Map.Entry<GuildMember, GuildMemberInfo> entry = menuGUI.guild.getMember(player);
			if(!PermissionManager.isAdmin(player)) {
				if(!entry.getValue().rank.isAtLeast(Constants.BANNER_COLOR)) {
					AOutput.error(player, "You must be at least " + Constants.BANNER_COLOR.displayName + " to do this");
					return;
				}
			}
			openPanel(menuGUI.dyePanel);
		} else if(slot == 12) {
			openPanel(menuGUI.buffPanel);
		} else if(slot == 13) {
			openPanel(menuGUI.upgradePanel);
		} else if(slot == 14) {
//			openPanel(menuGUI.shopPanel);
			Sounds.NO.play(player);
			AOutput.error(player, "This feature is still being developed");
		} else if(slot == 15) {
//			openPanel(menuGUI.settingsPanel);
			Sounds.NO.play(player);
			AOutput.error(player, "This feature is still being developed");
		}
	}

	@Override
	public void onOpen(InventoryOpenEvent event) { }

	@Override
	public void onClose(InventoryCloseEvent event) { }

	public void setInventory() {
		DyeColor dyeColor = null;
		for(DyeColor value : DyeColor.values()) {
			if(value.getDyeData() != menuGUI.guild.bannerColor) continue;
			dyeColor = value;
		}
		assert dyeColor != null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone(AConfig.getString("timezone")));

		ItemStack stats = new ItemStack(Material.BANNER);
		BannerMeta bannerMeta = (BannerMeta) stats.getItemMeta();
		bannerMeta.setBaseColor(dyeColor);
		stats.setItemMeta(bannerMeta);
		ChatColor chatColor = ColorConverter.getChatColor(dyeColor);
		new AItemStackBuilder(stats)
				.setName(chatColor + "&l" + menuGUI.guild.name + " Information")
				.setLore(new ALoreBuilder(
						"&7Date Created: " + chatColor + dateFormat.format(menuGUI.guild.dateCreated),
						"&7Owner: " + chatColor + Bukkit.getOfflinePlayer(menuGUI.guild.ownerUUID).getName(),
						"&7Members: " + chatColor + menuGUI.guild.members.size(),
						"&7Guild Rank: " + menuGUI.guild.getFormattedRank(),
						"&7Reputation: " + chatColor + ArcticGuilds.decimalFormat.format(menuGUI.guild.reputation),
						"&7Balance: &6" + menuGUI.guild.getFormattedBalance() + "g",
						"",
						"&7Click to change dye color"
				));
		getInventory().setItem(10, stats);
	}
}

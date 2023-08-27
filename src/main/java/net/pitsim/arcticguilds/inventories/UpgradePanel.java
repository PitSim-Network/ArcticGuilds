package net.pitsim.arcticguilds.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import net.pitsim.arcticguilds.ArcticGuilds;
import net.pitsim.arcticguilds.controllers.UpgradeManager;
import net.pitsim.arcticguilds.controllers.objects.GuildUpgrade;
import net.pitsim.arcticguilds.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class UpgradePanel extends AGUIPanel {
	public MenuGUI menuGUI;

	public UpgradePanel(AGUI gui) {
		super(gui);
		this.menuGUI = (MenuGUI) gui;

		inventoryBuilder.createBorder(Material.STAINED_GLASS_PANE, 7);

		for(int i = 0; i < UpgradeManager.upgradeList.size(); i++) {
			GuildUpgrade upgrade = UpgradeManager.upgradeList.get(i);
			int level = menuGUI.guild.getLevel(upgrade);
			getInventory().setItem(upgrade.slot, upgrade.getDisplayStack(menuGUI.guild, level));
		}
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
	public void onClick(InventoryClickEvent event) {
		if(event.getClickedInventory().getHolder() != this) return;
		int slot = event.getSlot();
		for(GuildUpgrade upgrade : UpgradeManager.upgradeList) {
			if(upgrade.slot != slot) continue;

			int level = menuGUI.guild.getLevel(upgrade);
			if(level >= upgrade.maxLevel) {
				AOutput.error(player, "That upgrade is max level");
				return;
			}

			long balance = menuGUI.guild.getBalance();
			int cost = upgrade.getCost(level + 1);
			if(balance < cost) {
				AOutput.error(player, "Insufficient bank funds");
				return;
			}

			menuGUI.guild.withdraw(cost);
			menuGUI.guild.upgradeLevels.put(upgrade, level + 1);
			menuGUI.guild.save();

			getInventory().setItem(upgrade.slot, upgrade.getDisplayStack(menuGUI.guild, level + 1));
			player.updateInventory();

			Sounds.UPGRADE.play(player);
			menuGUI.guild.broadcast("&a&lGUILD! &7Upgraded " + upgrade.displayName + " &7to level &a" + AUtil.toRoman(level + 1));
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

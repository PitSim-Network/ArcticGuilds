package dev.kyro.arcticguilds.inventories;

import dev.kyro.arcticapi.builders.AItemStackBuilder;
import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.gui.AGUI;
import dev.kyro.arcticapi.gui.AGUIPanel;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticapi.misc.AUtil;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.BuffManager;
import dev.kyro.arcticguilds.controllers.objects.GuildBuff;
import dev.kyro.arcticguilds.misc.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BuffPanel extends AGUIPanel {
	public MenuGUI menuGUI;

	public BuffPanel(AGUI gui) {
		super(gui);
		this.menuGUI = (MenuGUI) gui;

		setInventory();
	}

	@Override
	public String getName() {
		return ChatColor.GRAY + ((MenuGUI) gui).guild.name + " Guild Buffs";
	}

	@Override
	public int getRows() {
		return 6;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		if(event.getClickedInventory().getHolder() != this) return;
		int slot = event.getSlot();
		int row = slot / 9;
		int level = slot % 9;
		boolean isMain = slot % 9 == 0;
		GuildBuff buff = BuffManager.getBuff(row);
		if(buff == null) return;
		if(isMain) {
			menuGUI.guild.buffLevels.put(buff, 0);
			menuGUI.guild.save();
			menuGUI.guild.broadcast("&a&lGUILD &7Reset " + buff.displayName + " &7levels");
			setInventory();
			Sounds.RESET.play(player);
		} else {
			int buffLevel = menuGUI.guild.getLevel(buff);
			int cost = buffLevel + buff.firstLevelCost;
			if(level - 1 == buffLevel) {
				if(menuGUI.guild.getTotalBuffCost() + cost > menuGUI.guild.getRepPoints()) {
					AOutput.error(player, "You do not have enough reputation points");
					return;
				}
				menuGUI.guild.buffLevels.put(buff, buffLevel + 1);
				menuGUI.guild.save();
				menuGUI.guild.broadcast("&a&lGUILD! &7Increased " + buff.displayName + " &7to level &a" + AUtil.toRoman(buffLevel + 1));
				setInventory();
				Sounds.UPGRADE.play(player);
			}
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

	public void setInventory() {
		for(int i = 0; i < BuffManager.buffList.size(); i++) {
			GuildBuff buff = BuffManager.buffList.get(i);
			int startingSlot = i * 9;
			int buffLevel = menuGUI.guild.getLevel(buff);

			Map<GuildBuff.SubBuff, Double> subBuffs = new LinkedHashMap<>();
			for(int level = 1; level < 9; level++) {
				int slot = startingSlot + level;
				UnlockStatus status = UnlockStatus.LOCKED;
				if(level <= buffLevel) status = UnlockStatus.UNLOCKED;
				if(level - 1 == buffLevel) status = UnlockStatus.UNLOCKING;

				for(Map.Entry<GuildBuff.SubBuff, Double> entry : buff.subBuffMap.get(level).entrySet())
					subBuffs.put(entry.getKey(), subBuffs.getOrDefault(entry.getKey(), 0.0) + entry.getValue());

				ItemStack itemStack = getDisplayLevel(buff, level, status, subBuffs);
				getInventory().setItem(slot, itemStack);
			}
			ItemStack itemStack = buff.getDisplayItem(menuGUI.guild, buffLevel);
			List<String> lore = itemStack.getItemMeta().getLore();
			if(lore == null) lore = new ArrayList<>();
			ALoreBuilder loreBuilder = new ALoreBuilder(lore);
			for(String line : buff.description) loreBuilder.getLore().add(0, line);
			loreBuilder.addLore("", "&eClick to reset!");
			new AItemStackBuilder(itemStack).setLore(loreBuilder);
			getInventory().setItem(startingSlot, itemStack);
		}
		updateInventory();
	}

	public ItemStack getDisplayLevel(GuildBuff buff, int level, UnlockStatus status, Map<GuildBuff.SubBuff, Double> subBuffs) {
		ALoreBuilder lore = new ALoreBuilder();
		if(status == UnlockStatus.UNLOCKED) {

		} else if(status == UnlockStatus.UNLOCKING) {

		} else {

		}
		lore.addLore("&7Total Effects");
		for(Map.Entry<GuildBuff.SubBuff, Double> entry : subBuffs.entrySet()) {
			lore.addLore(status.color + entry.getKey().getDisplayString(entry.getValue()));
		}
		if(status == UnlockStatus.UNLOCKED) {
		} else if(status == UnlockStatus.UNLOCKING) {
			lore.addLore("", "&7Reputation Cost: &e" + (level + buff.firstLevelCost - 1),
					"&7Points Allocated: &e" + ArcticGuilds.decimalFormat.format(menuGUI.guild.getTotalBuffCost())
							+ "&7/&e" + ArcticGuilds.decimalFormat.format(menuGUI.guild.getRepPoints()), "", "&eClick to unlock!");
		} else {
			lore.addLore("", "&cUnlock prior levels first");
		}

		ItemStack itemStack = new AItemStackBuilder(status.material, level, status.data)
				.setName("&7Level " + level + " - " + status.color + status.name)
				.setLore(lore)
				.getItemStack();
		return itemStack;
	}

	private enum UnlockStatus {
		UNLOCKED("Unlocked", Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN),
		UNLOCKING("Click to Unlock", Material.STAINED_GLASS_PANE, (short) 4, ChatColor.YELLOW),
		LOCKED("Locked", Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED);

		public String name;
		public Material material;
		public short data;
		public ChatColor color;

		UnlockStatus(String name, Material material, short data, ChatColor color) {
			this.name = name;
			this.material = material;
			this.data = data;
			this.color = color;
		}
	}
}

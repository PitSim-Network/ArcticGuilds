package dev.kyro.arcticguilds.misc;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class DummyItemStack {

	private String material;
	private int amount;
	private String displayName;
	private List<String> lore;
	private short data;
	private List<String> modifiers = new ArrayList<>();

	public DummyItemStack(String material) {
		this.material = material;
		this.amount = 1;
		this.data = -1;
	}

	public DummyItemStack(String material, int amount, short data) {
		this.material = material;
		this.amount = amount;
		this.data = data;
	}

	public static DummyItemStack fromString(String string) {
		String[] split = string.split("\\|");

		DummyItemStack stack = new DummyItemStack(split[0]);
		if(split.length == 1) return stack;

		stack.setAmount(Integer.parseInt(split[1]));
		stack.setData(Short.parseShort(split[2]));

		if(split.length == 3) return stack;
		stack.setDisplayName(split[3]);

		if(split.length == 4) return stack;
		String modifiers = split[4];
		for(String s : modifiers.split("<")) {
			stack.addModifier(s);
		}

		if(split.length == 5) return stack;
		List<String> loreList = new ArrayList<>();
		String lore = split[5];
		Collections.addAll(loreList, lore.split("<"));
		stack.setLore(loreList);

		return stack;
	}

	public String toString() {
		StringBuilder modifiers = new StringBuilder();

		for(int i = 0; i < this.modifiers.size(); i++) {
			modifiers.append(this.modifiers.get(i));
			if(i != this.modifiers.size() - 1) {
				modifiers.append("<");
			}
		}

		StringBuilder lore = new StringBuilder();

		if(this.lore != null) {
			for(int i = 0; i < this.lore.size(); i++) {
				lore.append(this.lore.get(i));
				if(i != this.lore.size() - 1) {
					lore.append("<");
				}
			}
		}


		return material + "|" + amount + "|" + data + "|" + displayName + "|" + modifiers + "|" + lore;
	}

	public ItemStack toItemStack() {
		ItemStack itemStack;

		if(material.equals("null")) return new ItemStack(Material.AIR);

		if(data == -1) {
			itemStack = new ItemStack(Material.getMaterial(material));
		} else {
			itemStack = new ItemStack(Material.getMaterial(material), amount, data);
		}

		itemStack.setAmount(1);
		ItemMeta meta = itemStack.getItemMeta();
		if(displayName != null) meta.setDisplayName(displayName);
		if(lore != null && lore.size() > 0) meta.setLore(lore);

		for(String modifier : modifiers) {
			if(modifier.equals("ENCHANT_GLINT")) {
				meta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true);
			}
			if(modifier.equals("HIDE_ENCHANTS")) {
				meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
			}
			if(modifier.equals("HIDE_ATTRIBUTES")) {
				meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
			}
			if(modifier.equals("UNBREAKABLE")) {
				meta.spigot().setUnbreakable(true);
			}
			if(modifier.equals("HIDE_UNBREAKABLE")) {
				meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_UNBREAKABLE);
			}
			if(modifier.startsWith("BANNER")) {
				String[] split = modifier.split(":");
				org.bukkit.inventory.meta.BannerMeta bannerMeta = (org.bukkit.inventory.meta.BannerMeta) meta;
				bannerMeta.setBaseColor(org.bukkit.DyeColor.getByDyeData(Byte.parseByte(split[1])));
			}
			if(modifier.startsWith("SKULL")) {
				String[] split = modifier.split(":");
				OfflinePlayer player = org.bukkit.Bukkit.getOfflinePlayer(UUID.fromString(split[1]));
				org.bukkit.inventory.meta.SkullMeta skullMeta = (org.bukkit.inventory.meta.SkullMeta) meta;
				skullMeta.setOwner(player.getName());
			}
		}

		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public static DummyItemStack fromItemStack(ItemStack itemStack) {
		DummyItemStack dummyItemStack = new DummyItemStack(itemStack.getType().name(), itemStack.getAmount(), itemStack.getDurability());
		ItemMeta meta = itemStack.getItemMeta();
		if(meta.hasDisplayName()) dummyItemStack.setDisplayName(meta.getDisplayName());
		if(meta.hasLore()) dummyItemStack.setLore(meta.getLore());
		if(meta.hasEnchants()) dummyItemStack.addModifier("ENCHANT_GLINT");
		if(meta.spigot().isUnbreakable()) dummyItemStack.addModifier("UNBREAKABLE");
		if(meta instanceof org.bukkit.inventory.meta.BannerMeta) {
			org.bukkit.inventory.meta.BannerMeta bannerMeta = (org.bukkit.inventory.meta.BannerMeta) meta;
			dummyItemStack.addModifier("BANNER:" + bannerMeta.getBaseColor().getDyeData());
		}

		return dummyItemStack;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setData(short data) {
		this.data = data;
	}

	public String getMaterial() {
		return material;
	}

	public int getAmount() {
		return amount;
	}

	public String getDisplayName() {
		return displayName;
	}

	public List<String> getLore() {
		return lore;
	}

	public short getData() {
		return data;
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public void addModifier(String modifier) {
		modifiers.add(modifier);
	}



}

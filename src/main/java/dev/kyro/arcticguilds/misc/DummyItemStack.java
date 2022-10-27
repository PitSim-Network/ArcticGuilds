package dev.kyro.arcticguilds.misc;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyItemStack {

	private String material;
	private int amount;
	private String displayName;
	private List<String> lore;
	private short data;
	private final List<String> modifiers = new ArrayList<>();

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
		stack.setAmount(Integer.parseInt(split[1]));
		stack.setData(Short.parseShort(split[2]));
		stack.setDisplayName(split[3]);
		int modifierLength = Integer.parseInt(split[4]);

		List<String> lore = new ArrayList<>(Arrays.asList(split));
		int i;
		for(i = 0; i < modifierLength; i++) {
			stack.addModifier(lore.get(5 + i));
		}
		lore.subList(0, i + 4).clear();

		stack.setLore(lore);

		return stack;
	}

	public ItemStack toItemStack() {
		return null;
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

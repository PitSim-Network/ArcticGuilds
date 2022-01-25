package dev.kyro.arcticguilds.controllers.objects;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class GuildBuff {
	public String displayName;
	public String refName;
	public ChatColor chatColor;

	public List<SubBuff> subBuffList = new ArrayList<>();
	public Map<Integer, Map<SubBuff, Double>> subBuffMap = new LinkedHashMap<>();
	public int firstLevelCost = 1;

	public GuildBuff(String displayName, String refName, ChatColor chatColor) {
		this.displayName = displayName;
		this.refName = refName;
		this.chatColor = chatColor;
		addBuffs();
	}

	public abstract ItemStack getDisplayItem(Guild guild, int level);
	public abstract void addBuffs();

	public void addSubBuff(int level, SubBuff subBuff, double amount) {
		subBuffMap.putIfAbsent(level, new LinkedHashMap<>());
		subBuffMap.get(level).put(subBuff, amount);
	}

	public static class SubBuff {
//		Use %amount% to designate where the amount should go
		private final String displayString;

		public SubBuff(String displayString) {
			this.displayString = displayString;
		}

		public String getDisplayString(double amount) {
			DecimalFormat decimalFormat = new DecimalFormat("0.#");
			return displayString.replaceAll("%amount%", decimalFormat.format(amount));
		}
	}

	public Map<SubBuff, Double> getBuffs(int level) {
		Map<SubBuff, Double> buffMap = new LinkedHashMap<>();
		for(SubBuff subBuff : subBuffList) buffMap.put(subBuff, 0.0);
		int count = 0;
		for(Map.Entry<SubBuff, Double> entry : buffMap.entrySet()) {
			if(count++ == level) break;
			double current = buffMap.get(entry.getKey());
			buffMap.put(entry.getKey(), current + entry.getValue());
		}
		return buffMap;
	}

	public int getTotalCost() {
		int total = 0;
		for(int i = 0; i < 8; i++) total += i + firstLevelCost;
		return total;
	}

	public int getCostForLevel(int level) {
		int total = 0;
		for(int i = 0; i < level; i++) total += i + firstLevelCost;
		return total;
	}
}

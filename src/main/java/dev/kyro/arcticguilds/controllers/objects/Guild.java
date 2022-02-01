package dev.kyro.arcticguilds.controllers.objects;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.BuffManager;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.UpgradeManager;
import dev.kyro.arcticguilds.enums.GuildRank;
import dev.kyro.arcticguilds.events.GuildCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Guild {
	public List<UUID> activeInvites = new ArrayList<>();
	public int queuedReputation = 0;

//	Savable
	public UUID uuid;
	public Date dateCreated;
	public String name;
	public UUID ownerUUID;

	private long balance = 0;
	public int reputation = 0;
	public String tag;
	public int bannerColor = 15;
	public Map<GuildBuff, Integer> buffLevels = new LinkedHashMap<>();
	public Map<GuildUpgrade, Integer> upgradeLevels = new LinkedHashMap<>();
	public Map<GuildMember, GuildMemberInfo> members = new HashMap<>();

	public Guild(Player player, String name) {
		this.uuid = UUID.randomUUID();
		this.dateCreated = new Date();
		this.name = name;
		this.ownerUUID = player.getUniqueId();

		for(GuildBuff buff : BuffManager.buffList) this.buffLevels.put(buff, 0);
		for(GuildUpgrade upgrade : UpgradeManager.upgradeList) this.upgradeLevels.put(upgrade, 0);

		GuildMember guildMember = GuildManager.getMember(player.getUniqueId());
		guildMember.setGuildUUID(uuid);
		members.put(guildMember, new GuildMemberInfo(GuildRank.OWNER));

		GuildManager.guildList.add(this);
		save();
		GuildCreateEvent event = new GuildCreateEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
	}

	public Guild(String key, ConfigurationSection guildData) {
		this.uuid = UUID.fromString(key);
		this.dateCreated = new Date(guildData.getLong("created"));
		this.name = guildData.getString("name");
		this.ownerUUID = UUID.fromString(guildData.getString("owner"));

		this.balance = guildData.getLong("balance");
		this.reputation = guildData.getInt("reputation");
		this.tag = guildData.getString("tag");
		this.bannerColor = guildData.getInt("banner");
		for(GuildBuff buff : BuffManager.buffList) this.buffLevels.put(buff, guildData.getInt("buffs." + buff.refName));
		for(GuildUpgrade upgrade : UpgradeManager.upgradeList) this.upgradeLevels.put(upgrade, guildData.getInt("upgrades." + upgrade.refName));

		ConfigurationSection allMemberData = guildData.getConfigurationSection("members");
		if(allMemberData == null) allMemberData = guildData.createSection("members");
		for(String uuidString : allMemberData.getKeys(false)) {
			ConfigurationSection memberData = guildData.getConfigurationSection("members." + uuidString);
			UUID playerUUID = UUID.fromString(uuidString);

			GuildMember guildMember = GuildManager.getMember(playerUUID);
			members.put(guildMember, new GuildMemberInfo(memberData));
		}

		GuildManager.guildList.add(this);
	}

	public void save() {
		FileConfiguration fullData = GuildManager.guildFile.getConfiguration();
		ConfigurationSection guildData = fullData.getConfigurationSection(uuid.toString());
		if(guildData == null) guildData = fullData.createSection(uuid.toString());
		save(guildData);
		GuildManager.guildFile.saveDataFile();
	}

	public void save(ConfigurationSection guildData) {
		guildData.set("created", dateCreated.getTime());
		guildData.set("name", name);
		guildData.set("owner", ownerUUID.toString());

		guildData.set("balance", balance);
		guildData.set("reputation", reputation);
		guildData.set("tag", tag);
		guildData.set("banner", bannerColor);
		for(GuildBuff buff : BuffManager.buffList) guildData.set("buffs." + buff.refName, buffLevels.get(buff));
		for(GuildUpgrade upgrade : UpgradeManager.upgradeList) guildData.set("upgrades." + upgrade.refName, upgradeLevels.get(upgrade));

		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			String key = "members." + entry.getKey().playerUUID;
			ConfigurationSection memberData = guildData.getConfigurationSection(key);
			if(memberData == null) memberData = guildData.createSection(key);
			entry.getKey().save();
			entry.getValue().save(memberData);
		}
	}

	public Map.Entry<GuildMember, GuildMemberInfo> getMember(Player player) {
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			if(!entry.getKey().playerUUID.equals(player.getUniqueId())) continue;
			return entry;
		}
		return null;
	}

	public List<GuildMember> getMembersOfRank(GuildRank rank) {
		List<GuildMember> guildMembers = new ArrayList<>();
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			if(entry.getValue().rank != rank) continue;
			guildMembers.add(entry.getKey());
		}
		return guildMembers;
	}

	public void broadcast(String message) {
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) {
			Player chatPlayer = Bukkit.getPlayer(entry.getKey().playerUUID);
			if(chatPlayer == null) continue;
			AOutput.color(chatPlayer, message);
		}
	}

	public void chat(Player player, String message) {
		Map.Entry<GuildMember, GuildMemberInfo> info = getMember(player);
		message = ChatColor.stripColor(message);
		message = "&8[&aGuild&8] &a" + info.getValue().rank.prefix + player.getName() + " &8>> &a" + message;
		broadcast(message);
	}

	public void addMember(Player player) {
		GuildMember guildMember = GuildManager.getMember(player.getUniqueId());
		guildMember.setGuildUUID(uuid);
		members.put(guildMember, new GuildMemberInfo());
	}

	public String getFormattedBalance() {
		return ArcticGuilds.decimalFormat.format(balance);
	}

	public long getBalance() {
		return balance;
	}

	public void deposit(int amount) {
		balance += amount;
		assert balance >= 0;
		save();
	}

	public void withdraw(int amount) {
		balance -= amount;
		assert balance >= 0;
		save();
	}

	public void diminish() {
		double reduction = reputation;
		reduction = Math.pow(reduction, 1.4);
		reduction *= 0.000_05;

		reputation = (int) Math.max(reputation - reduction, 0);

		while(getTotalBuffCost() > getRepPoints()) {
			List<GuildBuff> applicableBuffs = new ArrayList<>();
			for(Map.Entry<GuildBuff, Integer> entry : buffLevels.entrySet()) if(entry.getValue() != 0) applicableBuffs.add(entry.getKey());
			Collections.shuffle(applicableBuffs);

			GuildBuff buff = applicableBuffs.remove(0);
			int currentLvl = buffLevels.get(buff);
			buffLevels.put(buff, --currentLvl);

			broadcast("&a&lGUILD! &7Not enough reputation! Reduced " + buff.displayName + " &7by 1");
		}
		save();
	}

	public int getRepPoints() {
		return reputation / 1000;
	}

	public void addReputationDirect(int amount) {
		int previousPoints = getRepPoints();
		reputation += amount;
		if(previousPoints < getRepPoints()) {
			broadcast("&a&lGUILD! &7+1 Reputation point");
		}
		save();
	}

	public void addReputation(int amount) {
		queuedReputation += amount;
	}

	public int getTotalBuffCost() {
		int total = 0;
		for(Map.Entry<GuildBuff, Integer> entry : buffLevels.entrySet()) total += entry.getKey().getCostForLevel(entry.getValue());
		return total;
	}

	public void disband() {
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : new HashMap<>(members).entrySet()) entry.getKey().leave();
		GuildManager.guildList.remove(this);

		FileConfiguration fullData = GuildManager.guildFile.getConfiguration();
		fullData.set(uuid.toString(), null);
		GuildManager.guildFile.saveDataFile();
	}

	public int getLevel(GuildBuff buff) {
		return buffLevels.get(buff);
	}

	public int getLevel(GuildUpgrade upgrade) {
		return upgradeLevels.get(upgrade);
	}

	public int getMaxMembers() {
		int size = 3;
		int sizeIncrease = getLevel(UpgradeManager.getUpgrade("size"));
		size += sizeIncrease;
		return size;
	}

	public int getMaxBank() {
		int max = 100_000;
		int multiplyTimes = getLevel(UpgradeManager.getUpgrade("bank"));
		for(int i = 0; i < multiplyTimes; i++) max *= 10;
		return max;
	}

	public String getFormattedRank() {
		int rank = GuildManager.getRank(this) + 1;
		if(rank == 1) return "&e#1";
		if(rank == 2) return "&f#2";
		if(rank == 3) return "&6#3";
		return "&7" + rank;
	}
}

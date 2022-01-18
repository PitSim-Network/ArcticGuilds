package dev.kyro.arcticguilds.controllers.objects;

import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.GuildManager;
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

//	Savable
	public UUID uuid;
	public String name;
	public UUID ownerUUID;

	private long balance = 0;
	public int reputation = 0;
	public String tag;

	public Map<GuildMember, GuildMemberInfo> members = new HashMap<>();

	public Guild(Player player, String name) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.ownerUUID = player.getUniqueId();

		GuildMember guildMember = GuildManager.getMember(player.getUniqueId());
		guildMember.guildUUID = uuid;
		members.put(guildMember, new GuildMemberInfo(GuildRank.OWNER));

		GuildManager.guildList.add(this);

		save();
		GuildCreateEvent event = new GuildCreateEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
	}

	public Guild(String key, ConfigurationSection guildData) {
		this.uuid = UUID.fromString(key);
		this.name = guildData.getString("name");
		this.ownerUUID = UUID.fromString(guildData.getString("owner"));

		this.balance = guildData.getLong("balance");
		this.reputation = guildData.getInt("reputation");
		this.tag = guildData.getString("tag");

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
		guildData.set("name", name);
		guildData.set("owner", ownerUUID.toString());

		guildData.set("balance", balance);
		guildData.set("reputation", reputation);
		guildData.set("tag", tag);

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
		guildMember.guildUUID = uuid;
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
	}

	public void disband() {
		GuildManager.guildList.remove(this);
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : members.entrySet()) entry.getKey().leave();

		FileConfiguration fullData = GuildManager.guildFile.getConfiguration();
		fullData.set(uuid.toString(), null);
		GuildManager.guildFile.saveDataFile();
	}
}

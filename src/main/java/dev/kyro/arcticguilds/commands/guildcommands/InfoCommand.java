package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.builders.AMessageBuilder;
import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.data.AConfig;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class InfoCommand extends ACommand {
	public InfoCommand(AMultiCommand base, String executor) {
		super(base, executor);
	}

	@Override
	public void execute(CommandSender sender, Command command, String alias, List<String> args) {
		if(!(sender instanceof Player)) return;
		Player player = (Player) sender;

		Guild guild = null;
		if(!args.isEmpty()) {
			String guildName = args.get(0);
			for(Guild testGuild : GuildManager.guildList) {
				if(!testGuild.name.equalsIgnoreCase(guildName)) continue;
				guild = testGuild;
				break;
			}
			if(guild == null) {
				AOutput.error(player, "That guild does not exist");
				return;
			}
		}
		if(guild == null) {
			guild = GuildManager.getGuild(player);
			if(guild == null) {
				AOutput.error(player, "You are not in a guild");
				return;
			}
		}

		List<Map.Entry<GuildMember, GuildMemberInfo>> sortedPlayers = new ArrayList<>();
		List<Map.Entry<GuildMember, GuildMemberInfo>> clone = new ArrayList<>();
		clone.addAll(guild.members.entrySet());
		main:
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : guild.members.entrySet()) {
			if(sortedPlayers.isEmpty()) {
				sortedPlayers.add(entry);
				continue;
			}
			for(int i = 0; i < clone.size(); i++) {
				Map.Entry<GuildMember, GuildMemberInfo> toCheckMember = clone.get(i);
				if(i >= sortedPlayers.size()) {
					sortedPlayers.add(entry);
					continue main;
				} else if(entry.getValue().rank.isAtLeast(toCheckMember.getValue().rank) && entry.getValue().rank != toCheckMember.getValue().rank) {
					sortedPlayers.add(i, entry);
					continue main;
				}
			}
		}
		List<UUID> onlinePlayers = new ArrayList<>();
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : sortedPlayers) {
//			onlinePlayers.add(entry.getKey().playerUUID);
//			if(true) continue;
			Player onlinePlayer = Bukkit.getPlayer(entry.getKey().playerUUID);
			if(onlinePlayer != null) onlinePlayers.add(entry.getKey().playerUUID);
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone(AConfig.getString("timezone")));
		AMessageBuilder messageBuilder = new AMessageBuilder()
				.addLine("&a&lGUILD " + guild.getColor() + guild.name)
				.addLine(guild.getColor() + " * &7Date Created: " + guild.getColor() + dateFormat.format(guild.dateCreated))
				.addLine(guild.getColor() + " * &7Guild Rank: " + guild.getColor() + guild.getFormattedRank())
				.addLine(guild.getColor() + " * &7Guild Reputation: " + guild.getColor() + guild.reputation)
				.addLine(guild.getColor() + " * &7Reputation Points: " + guild.getColor() + guild.getTotalBuffCost() +
						"&7/" + guild.getColor() + guild.getRepPoints())
				.addLine(guild.getColor() + " * &7Bank Balanace: &6" + guild.getFormattedBalance() + "g&7/&6" + ArcticGuilds.decimalFormat.format(guild.getMaxBank()))
				.addLine(guild.getColor() + " * &7Owner: " + guild.getColor() + Bukkit.getOfflinePlayer(guild.ownerUUID).getName())
				.addLine(guild.getColor() + " * &7Members: &7(" + guild.getColor() + guild.members.size() + "&7/" + guild.getColor() + guild.getMaxMembers() + "&7)")
				.addLine(guild.getColor() + " * &7Online Members: &7(" + guild.getColor() + onlinePlayers.size() + "&7/" + guild.getColor() + guild.members.size() + "&7)");
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : sortedPlayers) {
			if(!onlinePlayers.contains(entry.getKey().playerUUID)) continue;
			OfflinePlayer guildPlayer = Bukkit.getOfflinePlayer(entry.getKey().playerUUID);
			messageBuilder.addLine(guild.getColor() + "    - &a" + entry.getValue().rank.prefix + guildPlayer.getName());
		}
		messageBuilder.addLine(guild.getColor() + " * &7Offline Members: &7(" + guild.getColor() +
				(guild.members.size() - onlinePlayers.size()) + "&7/" + guild.getColor() + guild.members.size() + "&7)");
		for(Map.Entry<GuildMember, GuildMemberInfo> entry : sortedPlayers) {
			if(onlinePlayers.contains(entry.getKey().playerUUID)) continue;
			OfflinePlayer guildPlayer = Bukkit.getOfflinePlayer(entry.getKey().playerUUID);
			messageBuilder.addLine(guild.getColor() + "    - &c" + entry.getValue().rank.prefix + guildPlayer.getName());
		}

		messageBuilder.colorize().send(player);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		List<String> tabComplete = new ArrayList<>();
		for(Guild guild : GuildManager.guildList) {
			tabComplete.add(guild.name);
		}
		return tabComplete;
	}
}

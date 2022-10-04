package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PermissionManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import dev.kyro.arcticguilds.enums.GuildRank;
import dev.kyro.arcticguilds.inventories.ConfirmationGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransferCommand extends ACommand {
	public TransferCommand(AMultiCommand base, String executor) {
		super(base, executor);
	}

	@Override
	public void execute(CommandSender sender, Command command, String alias, List<String> args) {
		if(!(sender instanceof Player)) return;
		Player player = (Player) sender;

		Guild guild = GuildManager.getGuild(player);
		if(guild == null) {
			AOutput.error(player, "You are not in a guild");
			return;
		}

		if(!PermissionManager.isAdmin(player)) {
			if(!guild.ownerUUID.equals(player.getUniqueId())) {
				AOutput.error(player, "you are not the owner of your guild");
				return;
			}
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /transfer <player>");
			return;
		}

		Map.Entry<GuildMember, GuildMemberInfo> entry = guild.getMember(player);
		Map.Entry<GuildMember, GuildMemberInfo> targetEntry = null;
		OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
		if(target == null) {
			AOutput.error(player, "That player does not exist");
			return;
		}
		for(Map.Entry<GuildMember, GuildMemberInfo> memberEntry : guild.members.entrySet()) {
			if(!memberEntry.getKey().playerUUID.equals(target.getUniqueId())) continue;
			targetEntry = memberEntry;
			break;
		}
		if(targetEntry == null) {
			AOutput.error(player, "That player is not in your guild");
			return;
		}

		if(!PermissionManager.isAdmin(player)) {
			if(target.getUniqueId().equals(player.getUniqueId())) {
				AOutput.error(player, "You cannot promote yourself");
				return;
			}
		}

		BukkitRunnable transfer = new BukkitRunnable() {
			@Override
			public void run() {
				Map.Entry<GuildMember, GuildMemberInfo> entry = guild.getMember(player);
				Map.Entry<GuildMember, GuildMemberInfo> targetEntry = guild.getMember(target.getUniqueId());

				if(entry == null || targetEntry == null || entry.getValue().rank != GuildRank.OWNER) {
					AOutput.error(player, "Something went wrong. Please try again");
					return;
				}

				entry.getValue().rank = GuildRank.CO_OWNER;
				targetEntry.getValue().rank = GuildRank.OWNER;

				guild.ownerUUID = target.getUniqueId();
				guild.save();
				guild.broadcast("&a&lGUILD! &7Guild ownership has been transferred to " + target.getName());
			}
		};
		ALoreBuilder yesLore = new ALoreBuilder("&7Clicking here will transfer", "&7your guild to " + target.getName());
		ALoreBuilder noLore = new ALoreBuilder("&7Click to cancel");
		new ConfirmationGUI(player, transfer, yesLore, noLore).open();
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		List<String> tabComplete = new ArrayList<>();
		Guild guild = GuildManager.getGuild(player);
		if(guild == null) return null;
		Map.Entry<GuildMember, GuildMemberInfo> entry = guild.getMember(player);
		for(Map.Entry<GuildMember, GuildMemberInfo> memberEntry : guild.members.entrySet()) {
			if(!entry.getValue().rank.isAtLeast(memberEntry.getValue().rank) || entry.getValue().rank == memberEntry.getValue().rank) continue;
			if(entry.getKey().playerUUID.equals(player.getUniqueId())) continue;
			OfflinePlayer guildPlayer = Bukkit.getOfflinePlayer(memberEntry.getKey().playerUUID);
			tabComplete.add(guildPlayer.getName());
		}
		return tabComplete;
	}
}

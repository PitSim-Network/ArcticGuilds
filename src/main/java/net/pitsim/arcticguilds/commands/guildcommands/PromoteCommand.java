package net.pitsim.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.arcticguilds.controllers.GuildManager;
import net.pitsim.arcticguilds.controllers.PermissionManager;
import net.pitsim.arcticguilds.controllers.objects.Guild;
import net.pitsim.arcticguilds.controllers.objects.GuildMember;
import net.pitsim.arcticguilds.controllers.objects.GuildMemberInfo;
import net.pitsim.arcticguilds.enums.GuildRank;
import net.pitsim.arcticguilds.misc.Constants;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromoteCommand extends ACommand {
	public PromoteCommand(AMultiCommand base, String executor) {
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

		Map.Entry<GuildMember, GuildMemberInfo> entry = guild.getMember(player);
		if(!PermissionManager.isAdmin(player)) {
			if(!entry.getValue().rank.isAtLeast(Constants.PROMOTE_PERMISSION)) {
				AOutput.error(player, "You must be at least " + Constants.PROMOTE_PERMISSION.displayName + " to do this");
				return;
			}
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /promote <player>");
			return;
		}

		Map.Entry<GuildMember, GuildMemberInfo> guildTarget = null;
		OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(0));
		if(target == null) {
			AOutput.error(player, "That player does not exist");
			return;
		}
		for(Map.Entry<GuildMember, GuildMemberInfo> memberEntry : guild.members.entrySet()) {
			if(!memberEntry.getKey().playerUUID.equals(target.getUniqueId())) continue;
			guildTarget = memberEntry;
			break;
		}
		if(guildTarget == null) {
			AOutput.error(player, "That player is not in your guild");
			return;
		}

		if(guildTarget.getValue().rank.isAtLeast(GuildRank.CO_OWNER)) {
			AOutput.error(player, "That player cannot be promoted any higher");
			return;
		}

		if(!PermissionManager.isAdmin(player)) {
			if(guildTarget.getValue().rank.getPriority() + 1 >= entry.getValue().rank.getPriority()) {
				AOutput.error(player, "You cannot promote someone of an equal or higher rank");
				return;
			}
		}

		if(!PermissionManager.isAdmin(player)) {
			if(target.getUniqueId().equals(player.getUniqueId())) {
				AOutput.error(player, "You cannot promote yourself");
				return;
			}
		}

		guildTarget.getValue().rank = guildTarget.getValue().rank.getRelative(1);
		guild.save();
		guild.broadcast("&a&lGUILD! &7" + target.getName() + " has been promoted to " + guildTarget.getValue().rank.displayName);
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

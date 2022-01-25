package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PermissionManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import dev.kyro.arcticguilds.misc.Constants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InviteCommand extends ACommand {
	public static List<UUID> cooldownList = new ArrayList<>();

	public InviteCommand(AMultiCommand base, String executor) {
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

		Map.Entry<GuildMember, GuildMemberInfo> info = guild.getMember(player);
		if(!info.getValue().rank.isAtLeast(Constants.INVITE_PERMISSION)) {
			AOutput.error(player, "You must be at least " + Constants.INVITE_PERMISSION.displayName + " to do this");
			return;
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /invite <player>");
			return;
		}

		Player target = null;
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if(!onlinePlayer.getName().equalsIgnoreCase(args.get(0))) continue;
			target = onlinePlayer;
			break;
		}
		if(target == null) {
			AOutput.error(player, "Could not find that player");
			return;
		}

		for(Map.Entry<GuildMember, GuildMemberInfo> entry : guild.members.entrySet()) {
			if(!entry.getKey().playerUUID.equals(target.getUniqueId())) continue;
			AOutput.error(player, "That player is already in your group");
			return;
		}

		if(guild.activeInvites.contains(target.getUniqueId())) {
			AOutput.error(player, "You have already sent an invite to that player");
			return;
		}

		if(!PermissionManager.isAdmin(player)) {
			if(guild.members.size() >= guild.getMaxMembers()) {
				AOutput.error(player, "Your guild at its maximum size");
				return;
			}

			if(cooldownList.contains(target.getUniqueId())) {
				AOutput.error(player, "Please wait before trying to invite this player again");
				return;
			}
			cooldownList.add(target.getUniqueId());
			Player finalTarget = target;
			new BukkitRunnable() {
				@Override
				public void run() {
					cooldownList.remove(finalTarget.getUniqueId());
				}
			}.runTaskLater(ArcticGuilds.INSTANCE, 20 * 60);
		}

		guild.activeInvites.add(target.getUniqueId());

		guild.broadcast("&a&lGUILD! &7" + target.getName() + " has been invited to the guild by " + player.getName());
		AOutput.send(target, "&a&lGUILD! &7You have been invited to join " + guild.name + " by " + player.getName());
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}
package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import dev.kyro.arcticguilds.misc.Constants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class UnInviteCommand extends ACommand {
	public UnInviteCommand(AMultiCommand base, String executor) {
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
		if(!entry.getValue().rank.isAtLeast(Constants.INVITE_PERMISSION)) {
			AOutput.error(player, "You must be at least " + Constants.INVITE_PERMISSION.displayName + " to do this");
			return;
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /uninvite <player>");
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

		if(!guild.activeInvites.contains(target.getUniqueId())) {
			AOutput.error(player, "There is no active invite to that player");
			return;
		}

		guild.activeInvites.remove(target.getUniqueId());

		AOutput.send(target, "&a&lGUILD! &7Your invite to the guild " + guild.name + " has been revoked");
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

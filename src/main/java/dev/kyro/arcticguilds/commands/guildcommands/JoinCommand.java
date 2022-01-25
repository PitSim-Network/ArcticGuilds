package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PermissionManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinCommand extends ACommand {
	public JoinCommand(AMultiCommand base, String executor) {
		super(base, executor);
	}

	@Override
	public void execute(CommandSender sender, Command command, String alias, List<String> args) {
		if(!(sender instanceof Player)) return;
		Player player = (Player) sender;

		Guild preGuild = GuildManager.getGuild(player);
		if(preGuild != null) {
			AOutput.error(player, "You are already in a guild");
			return;
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /join <guild>");
			return;
		}

		Guild guild = null;
		for(Guild testGuild : GuildManager.guildList) {
			if(!testGuild.name.equalsIgnoreCase(args.get(0))) continue;
			guild = testGuild;
			break;
		}
		if(guild == null) {
			AOutput.error(player, "Could not find a guild with that name");
			return;
		}

		if(!PermissionManager.isAdmin(player)) {
			if(!guild.activeInvites.contains(player.getUniqueId())) {
				AOutput.error(player, "You do not have an invite to that guild");
				return;
			}
			guild.activeInvites.remove(player.getUniqueId());

			if(guild.members.size() >= guild.getMaxMembers()) {
				AOutput.error(player, "That guild has reached its maximum amount of members");
				return;
			}
		}

		guild.broadcast("&a&lGUILD! &7" + player.getName() + " has joined the guild");
		guild.addMember(player);
		AOutput.send(player, "&a&lGUILD! &7You have joined the guild " + guild.name);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

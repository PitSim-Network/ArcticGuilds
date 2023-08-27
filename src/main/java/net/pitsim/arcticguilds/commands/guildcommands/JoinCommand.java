package net.pitsim.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.arcticguilds.controllers.GuildManager;
import net.pitsim.arcticguilds.controllers.PermissionManager;
import net.pitsim.arcticguilds.controllers.objects.Guild;
import net.pitsim.arcticguilds.controllers.objects.GuildMember;
import net.pitsim.arcticguilds.controllers.objects.GuildMemberInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		if(!PermissionManager.isAdmin(player)) {
			GuildMember guildMember = GuildManager.getMember(player.getUniqueId());
			if(guildMember.wasModifiedRecently()) {
				AOutput.error(player, "You have changed guilds too recently. Please wait " + guildMember.getModifiedTimeRemaining());
				return;
			}
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
			guild:
			for(Guild testGuild : GuildManager.guildList) {
				for(Map.Entry<GuildMember, GuildMemberInfo> entry : testGuild.members.entrySet()) {
					OfflinePlayer guildPlayer = Bukkit.getOfflinePlayer(entry.getKey().playerUUID);
					if(!guildPlayer.getName().equalsIgnoreCase(args.get(0))) continue;
					guild = testGuild;
					break guild;
				}
			}
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
		List<String> tabComplete = new ArrayList<>();
		for(Guild guild : GuildManager.guildList) {
			if(!guild.activeInvites.contains(player.getUniqueId())) continue;
			tabComplete.add(guild.name);
		}
		return tabComplete;
	}
}

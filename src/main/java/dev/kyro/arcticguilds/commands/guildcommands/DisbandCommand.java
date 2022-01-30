package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PermissionManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class DisbandCommand extends ACommand {
	public DisbandCommand(AMultiCommand base, String executor) {
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

			Map.Entry<GuildMember, GuildMemberInfo> entry = guild.getMember(player);
			if(entry.getKey().wasModifiedRecently()) {
				AOutput.error(player, "You have changed guilds too recently. Please wait " + entry.getKey().getModifiedTimeRemaining());
				return;
			}
		}

		guild.disband();
		AOutput.send(player, "You have disbanded the guild " + guild.name);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import dev.kyro.arcticguilds.misc.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KickCommand extends ACommand {
	public KickCommand(AMultiCommand base, String executor) {
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

		if(guild.ownerUUID.equals(player.getUniqueId())) {
			AOutput.error(player, "You cannot leave your own guild");
			return;
		}

		GuildMemberInfo memberInfo = guild.getMemberInfo(player);
		if(!memberInfo.rank.isAtLeast(Constants.KICK_PERMISSION)) {
			AOutput.error(player, "You must be at least " + Constants.KICK_PERMISSION.displayName + " to be able to do this");
			return;
		}

		guild.getMember(player).leave();
		AOutput.send(player, "You have left the guild: " + guild.name);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

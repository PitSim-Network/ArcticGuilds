package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateCommand extends ACommand {
	public CreateCommand(AMultiCommand base, String executor) {
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
			AOutput.error(player, "Usage: /guild create <name>");
			return;
		}
		String name = args.get(0);

		Guild guild = new Guild(player, name);
		AOutput.send(player, "You have created a guild with the name: " + guild.name);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

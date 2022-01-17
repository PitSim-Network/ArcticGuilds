package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.AMultiCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GuildCommand extends AMultiCommand {
	public GuildCommand(String executor) {
		super(executor);
	}

	@Override
	public void execute(CommandSender sender, Command command, String alias, List<String> args) {

	}
}

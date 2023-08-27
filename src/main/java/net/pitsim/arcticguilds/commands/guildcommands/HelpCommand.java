package net.pitsim.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import net.pitsim.arcticguilds.inventories.HelpGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand extends ACommand {
	public HelpCommand(AMultiCommand base, String executor) {
		super(base, executor);
	}

	@Override
	public void execute(CommandSender sender, Command command, String alias, List<String> args) {
		if(!(sender instanceof Player)) return;
		Player player = (Player) sender;

		new HelpGUI(player).open();
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

package net.pitsim.arcticguilds.commands.admin;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.arcticguilds.controllers.GuildManager;
import net.pitsim.arcticguilds.controllers.objects.Guild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReputationCommand extends ACommand {
	public ReputationCommand(AMultiCommand base, String executor) {
		super(base, executor);
	}

	@Override
	public void execute(CommandSender sender, Command command, String alias, List<String> args) {
		if(!(sender instanceof Player)) return;
		Player player = (Player) sender;

		if(args.size() < 2) {
			AOutput.error(player, "Usage: /rep <guild> <amount>");
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

		int amount;
		try {
			amount = Integer.parseInt(args.get(1));
		} catch(Exception ignored) {
			AOutput.error(player, "Invalid amount");
			return;
		}

		guild.addReputationDirect(amount);
		AOutput.send(player, "&a&lGUILD! &7Gave the guild " + guild.name + " " + amount + " reputation");
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		List<String> tabComplete = new ArrayList<>();
		for(Guild guild : GuildManager.guildList) {
			tabComplete.add(guild.name);
		}
		return tabComplete;
	}
}

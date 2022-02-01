package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PermissionManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Pattern;

public class CreateCommand extends ACommand {
	public static int GUILD_CREATION_COST = 1_000_000;

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

		if(!PermissionManager.isAdmin(player)) {
			GuildMember guildMember = GuildManager.getMember(player.getUniqueId());
			if(guildMember.wasModifiedRecently()) {
				AOutput.error(player, "You have changed guilds too recently. Please wait " + guildMember.getModifiedTimeRemaining());
				return;
			}
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /guild create <name>");
			return;
		}

		if(ArcticGuilds.VAULT.getBalance(player) < GUILD_CREATION_COST) {
			AOutput.error(player, "You do not have enough money to do that");
			return;
		}

		String name = args.get(0);
		if(name.length() > 16) {
			AOutput.error(player, "Your guild's name cannot be longer than 16 characters");
			return;
		}
		Pattern pattern = Pattern.compile("[^a-z0-9_#]", Pattern.CASE_INSENSITIVE);
		if(pattern.matcher(name).find()) {
			AOutput.error(player, "Names can only contain numbers, letters, underscores, and octothorpes");
			return;
		}

		for(Guild guild : GuildManager.guildList) {
			if(!guild.name.equalsIgnoreCase(name)) continue;
			AOutput.error(player, "A guild with that name already exists");
			return;
		}

		Guild guild = new Guild(player, name);
		ArcticGuilds.VAULT.withdrawPlayer(player, GUILD_CREATION_COST);
		AOutput.send(player, "You have created a guild with the name: " + guild.name);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

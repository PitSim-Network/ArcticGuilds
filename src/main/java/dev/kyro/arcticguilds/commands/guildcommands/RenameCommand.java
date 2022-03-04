package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.builders.ALoreBuilder;
import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.PermissionManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import dev.kyro.arcticguilds.inventories.ConfirmationGUI;
import dev.kyro.arcticguilds.misc.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RenameCommand extends ACommand {
	public static int GUILD_RENAME_COST = 100_000;

	public RenameCommand(AMultiCommand base, String executor) {
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
		if(!PermissionManager.isAdmin(player)) {
			if(!entry.getValue().rank.isAtLeast(Constants.RENAME_PERMISSION)) {
				AOutput.error(player, "You must be at least " + Constants.RENAME_PERMISSION.displayName + " to do this");
				return;
			}
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /guild rename <name>");
			return;
		}

		if(ArcticGuilds.VAULT.getBalance(player) < GUILD_RENAME_COST) {
			AOutput.error(player, "&7You need to have &6g" + ArcticGuilds.decimalFormat.format(GUILD_RENAME_COST) + "g &7to do that");
			return;
		}

		String name = args.get(0);
		if(name.length() > 16) {
			AOutput.error(player, "Your guild's name cannot be longer than 16 characters");
			return;
		}
		Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
		if(pattern.matcher(name).find()) {
			AOutput.error(player, "Names can only contain numbers and letters");
			return;
		}

		for(Guild testGuild : GuildManager.guildList) {
			if(testGuild == guild || !testGuild.name.equalsIgnoreCase(name)) continue;
			AOutput.error(player, "A guild with that name already exists");
			return;
		}

		BukkitRunnable disband = new BukkitRunnable() {
			@Override
			public void run() {
				ArcticGuilds.VAULT.withdrawPlayer(player, GUILD_RENAME_COST);
				guild.broadcast("&a&lGUILD! &7Guild name changed to: " + guild.name);
			}
		};
		ALoreBuilder yesLore = new ALoreBuilder("&7Clicking here will rename", "&7your guild to " + name, "",
				"&7Doing so costs &6" + ArcticGuilds.decimalFormat.format(GUILD_RENAME_COST) + "g");
		ALoreBuilder noLore = new ALoreBuilder("&7Click to cancel");
		new ConfirmationGUI(player, disband, yesLore, noLore).open();
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

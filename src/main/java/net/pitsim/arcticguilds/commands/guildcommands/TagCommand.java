package net.pitsim.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.arcticguilds.controllers.GuildManager;
import net.pitsim.arcticguilds.controllers.PermissionManager;
import net.pitsim.arcticguilds.controllers.objects.Guild;
import net.pitsim.arcticguilds.controllers.objects.GuildMember;
import net.pitsim.arcticguilds.controllers.objects.GuildMemberInfo;
import net.pitsim.arcticguilds.misc.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TagCommand extends ACommand {
	public TagCommand(AMultiCommand base, String executor) {
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
			if(!entry.getValue().rank.isAtLeast(Constants.TAG_PERMISSION)) {
				AOutput.error(player, "You must be at least " + Constants.TAG_PERMISSION.displayName + " to do this");
				return;
			}
		}

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /tag <name>");
			return;
		}

		String tag = args.get(0);
		if(tag.length() > 5) {
			AOutput.error(player, "Your guild's tag cannot be longer than 5 characters");
			return;
		}
		Pattern pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
		if(pattern.matcher(tag).find()) {
			AOutput.error(player, "Tags can only contain letters");
			return;
		}

		for(Guild testGuild : GuildManager.guildList) {
			if(testGuild.tag == null || !testGuild.tag.equalsIgnoreCase(tag) || testGuild == guild) continue;
			AOutput.error(player, "A guild with that tag already exists");
			return;
		}

		guild.tag = tag;
		guild.save();
		guild.broadcast("&aGUILD! &7Your guild's tag is now " + guild.getColor() + "#" + guild.tag);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

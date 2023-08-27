package net.pitsim.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.arcticguilds.controllers.ChatManager;
import net.pitsim.arcticguilds.controllers.GuildManager;
import net.pitsim.arcticguilds.controllers.objects.Guild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatCommand extends ACommand {
	public ChatCommand(AMultiCommand base, String executor) {
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

		if(ChatManager.guildChatPlayer.contains(player.getUniqueId())) {
			ChatManager.guildChatPlayer.remove(player.getUniqueId());
			AOutput.send(player, "&a&lGUILD! &7Guild chat &cdisabled");
		} else {
			ChatManager.guildChatPlayer.add(player.getUniqueId());
			AOutput.send(player, "&a&lGUILD! &7Guild chat &aenabled");
		}
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

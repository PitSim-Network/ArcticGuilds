package dev.kyro.arcticguilds.commands.guildcommands;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.ArcticGuilds;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.events.GuildWithdrawalEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DepositCommand extends ACommand {
	public DepositCommand(AMultiCommand base, String executor) {
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

		if(args.size() < 1) {
			AOutput.error(player, "Usage: /deposit <amount>");
			return;
		}

		int amount;
		try {
			amount = Integer.parseInt(args.get(0));
			if(amount <= 0) throw new IllegalArgumentException();
		} catch(Exception ignored) {
			AOutput.error(player, "Invalid amount");
			return;
		}

		if(amount > ArcticGuilds.VAULT.getBalance(player)) {
			AOutput.error(player, "You do not have enough money to do this");
			return;
		}

		if(guild.getBalance() + amount > guild.getMaxBank()) {
			AOutput.error(player, "Bank is too full");
			return;
		}

		ArcticGuilds.VAULT.withdrawPlayer(player, amount);
		guild.deposit(amount);

		guild.broadcast("&a&lGUILD! &7" + player.getName() + " has deposited &6" + ArcticGuilds.decimalFormat.format(amount) + "g");

		GuildWithdrawalEvent event = new GuildWithdrawalEvent(player, guild, amount);
		Bukkit.getPluginManager().callEvent(event);
	}

	@Override
	public List<String> getTabComplete(Player player, String current, List<String> args) {
		return null;
	}
}

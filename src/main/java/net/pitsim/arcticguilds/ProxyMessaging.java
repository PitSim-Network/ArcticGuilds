package net.pitsim.arcticguilds;

import net.pitsim.arcticguilds.events.GuildWithdrawalEvent;
import net.pitsim.arcticguilds.misc.MessageEvent;
import net.pitsim.arcticguilds.misc.PluginMessage;
import net.pitsim.arcticguilds.misc.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class ProxyMessaging implements Listener {

	@EventHandler
	public void onMessageReceived(MessageEvent event) {
		PluginMessage message = event.getMessage();
		List<String> strings = message.getStrings();
		List<Integer> ints = message.getIntegers();

		if(strings.size() >= 3 && strings.get(0).equals("OPEN INVENTORY")) {

			strings.remove(0);

			UUID uuid = UUID.fromString(strings.get(0));
			strings.remove(0);
			Player player = Bukkit.getPlayer(uuid);
			if(player == null) return;

			String inventoryName = strings.get(0);
			strings.remove(0);
			int rows = ints.get(0);
			ints.remove(0);

			new BukkitRunnable() {
				@Override
				public void run() {
//					player.closeInventory();
					PreparedGUI gui = new PreparedGUI(player, inventoryName, rows, strings);
					gui.open();
				}
			}.runTask(ArcticGuilds.INSTANCE);
		}

		if(strings.size() >= 1 && ints.size() >= 1 && strings.get(0).equals("WITHDRAW")) {
			UUID uuid = UUID.fromString(strings.get(1));
			Player player = Bukkit.getPlayer(uuid);
			if(player == null) return;

			int amount = ints.get(0);

			GuildWithdrawalEvent withdrawalEvent = new GuildWithdrawalEvent(player, amount);
			Bukkit.getPluginManager().callEvent(withdrawalEvent);
		}

		if(strings.size() >= 1 && strings.get(0).equals("GUILD DATA")) {
			strings.remove(0);

			UUID uuid = UUID.fromString(strings.get(0));
			strings.remove(0);

			new GuildMember(uuid, strings, ints);
		}

		if(strings.size() >= 1 && ints.size() >= 3 && strings.get(0).equals("GUILD LEADERBOARD DATA")) {
			strings.remove(0);

			GuildLeaderboardData.guildData.clear();

			for(int i = 0; i < 10; i++) {
				if(strings.size() < 3 || ints.size() < 1) break;

				new GuildLeaderboardData(strings, ints);
				strings.remove(0);
				strings.remove(0);
				strings.remove(0);
				ints.remove(0);
			}
		}

		if(strings.size() >= 3 && strings.get(0).equals("PLAY SOUND")) {
			UUID uuid = UUID.fromString(strings.get(1));
			Player player = Bukkit.getPlayer(uuid);
			if(player == null) return;

			String sound = strings.get(2);

			switch(sound) {
				case "RESET":
					Sounds.RESET.play(player);
					break;
				case "UPGRADE":
					Sounds.UPGRADE.play(player);
					break;

			}
		}
	}
}

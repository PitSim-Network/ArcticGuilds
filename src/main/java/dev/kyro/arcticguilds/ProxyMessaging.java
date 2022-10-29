package dev.kyro.arcticguilds;

import dev.kyro.arcticguilds.misc.MessageEvent;
import dev.kyro.arcticguilds.misc.PluginMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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

			UUID uuid = UUID.fromString(strings.get(1));
			strings.remove(0);
			Player player = Bukkit.getPlayer(uuid);
			if(player == null) return;

			String inventoryName = strings.get(2);
			strings.remove(0);
			int rows = ints.get(0);
			ints.remove(0);

			System.out.println(strings);
		}
	}
}

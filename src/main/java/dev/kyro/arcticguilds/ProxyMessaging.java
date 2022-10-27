package dev.kyro.arcticguilds;

import dev.kyro.arcticguilds.misc.MessageEvent;
import dev.kyro.arcticguilds.misc.PluginMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ProxyMessaging implements Listener {

	@EventHandler
	public void onMessageReceived(MessageEvent event) {
		PluginMessage message = event.getMessage();
	}
}

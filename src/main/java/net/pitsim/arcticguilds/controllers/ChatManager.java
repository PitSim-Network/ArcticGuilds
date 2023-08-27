package net.pitsim.arcticguilds.controllers;

import net.pitsim.arcticguilds.controllers.objects.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatManager implements Listener {
	public static List<UUID> guildChatPlayer = new ArrayList<>();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(!guildChatPlayer.contains(player.getUniqueId())) return;

		Guild guild = GuildManager.getGuild(player);
		if(guild == null) return;

		event.setCancelled(true);
		guild.chat(player, event.getMessage());
	}
}

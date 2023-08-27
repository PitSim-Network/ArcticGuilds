package net.pitsim.arcticguilds.controllers;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PermissionManager implements Listener {

	public static boolean isAdmin(Player player) {
		return player.hasPermission("aguilds.admin");
	}
}

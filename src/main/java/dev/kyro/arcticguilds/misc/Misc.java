package dev.kyro.arcticguilds.misc;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Misc {

	public static void sendActionBar(Player player, String message) {
		PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" +
				ChatColor.translateAlternateColorCodes('&', message) + "\"}"), (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendTitle(Player player, String message, int length) {
		IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
				ChatColor.translateAlternateColorCodes('&', message) + "\"}");

		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle titleLength = new PacketPlayOutTitle(5, length, 5);

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
	}

	public static void sendSubTitle(Player player, String message, int length) {
		IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
				ChatColor.translateAlternateColorCodes('&', message) + "\"}");

		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
		PacketPlayOutTitle subTitleLength = new PacketPlayOutTitle(5, length, 5);

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitleLength);
	}

	public static boolean isAirOrNull(ItemStack itemStack) {

		return itemStack == null || itemStack.getType() == Material.AIR;
	}
}

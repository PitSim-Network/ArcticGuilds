package dev.kyro.arcticguilds.misc;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public class ColorConverter {

	public static ChatColor getChatColor(DyeColor dyeColor) {
		switch(dyeColor) {
			case BLACK:
				return ChatColor.BLACK;
			case RED:
				return ChatColor.RED;
			case GREEN:
				return ChatColor.DARK_GREEN;
			case BROWN:
				return ChatColor.BLACK;
			case BLUE:
				return ChatColor.BLUE;
			case PURPLE:
				return ChatColor.DARK_PURPLE;
			case CYAN:
				return ChatColor.DARK_AQUA;
			case SILVER:
				return ChatColor.GRAY;
			case GRAY:
				return ChatColor.DARK_GRAY;
			case PINK:
				return ChatColor.LIGHT_PURPLE;
			case LIME:
				return ChatColor.GREEN;
			case YELLOW:
				return ChatColor.YELLOW;
			case LIGHT_BLUE:
				return ChatColor.AQUA;
			case MAGENTA:
				return ChatColor.LIGHT_PURPLE;
			case ORANGE:
				return ChatColor.GOLD;
			case WHITE:
				return ChatColor.WHITE;
		}
		return null;
	}
}

package dev.kyro.arcticguilds.commands.admin;

import dev.kyro.arcticapi.commands.ACommand;
import dev.kyro.arcticapi.commands.AMultiCommand;
import dev.kyro.arcticapi.misc.AOutput;
import dev.kyro.arcticguilds.controllers.BuffManager;
import dev.kyro.arcticguilds.controllers.ConnectionManager;
import dev.kyro.arcticguilds.controllers.GuildManager;
import dev.kyro.arcticguilds.controllers.UpgradeManager;
import dev.kyro.arcticguilds.controllers.objects.Guild;
import dev.kyro.arcticguilds.controllers.objects.GuildMember;
import dev.kyro.arcticguilds.controllers.objects.GuildMemberInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.kyro.arcticguilds.controllers.GuildManager.guildFile;

public class TestCommand extends ACommand {
	public TestCommand(AMultiCommand base, String executor) {
		super(base, executor);
	}

	@Override
	public void execute(CommandSender sender, Command command, String alias, List<String> args) {
		if(!(sender instanceof Player)) return;
		Player player = (Player) sender;

		if(!player.isOp()) {
			AOutput.error(player, "Invalid Permissions");
			return;
		}

		for(String key : guildFile.getConfiguration().getKeys(false)) {
			ConfigurationSection guildData = guildFile.getConfiguration().getConfigurationSection(key);
			Guild guild = new Guild(key, guildData);

			String query = "REPLACE INTO GUILD_DATA (UUID, created, name, owner, balance, reputation," +
					" banner, buffs_damage, buffs_defence, buffs_xp, buffs_gold, buffs_renown, upgrades_size, upgrades_bank, " +
					"upgrades_reputation, tag, members) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			try(PreparedStatement statement = ConnectionManager.connection.prepareStatement(query)) {
				statement.setString(1, guild.uuid.toString());
				statement.setLong(2, guild.dateCreated.getTime());
				statement.setString(3, guild.name);
				statement.setString(4, guild.ownerUUID.toString());
				statement.setLong(5, guild.getBalance());
				statement.setInt(6, guild.reputation);
				statement.setInt(7, guild.bannerColor);
				statement.setInt(8, guild.buffLevels.get(BuffManager.getBuff("damage")));
				statement.setInt(9, guild.buffLevels.get(BuffManager.getBuff("defence")));
				statement.setInt(10, guild.buffLevels.get(BuffManager.getBuff("xp")));
				statement.setInt(11, guild.buffLevels.get(BuffManager.getBuff("gold")));
				statement.setInt(12, guild.buffLevels.get(BuffManager.getBuff("renown")));
				statement.setInt(13, guild.upgradeLevels.get(UpgradeManager.getUpgrade("size")));
				statement.setInt(14, guild.upgradeLevels.get(UpgradeManager.getUpgrade("bank")));
				statement.setInt(15, guild.upgradeLevels.get(UpgradeManager.getUpgrade("reputation")));
				statement.setString(16, guild.tag);

				StringBuilder members = new StringBuilder();
				for(Map.Entry<GuildMember, GuildMemberInfo> entry : guild.members.entrySet()) {
					members.append(entry.getKey().playerUUID.toString()).append(":").append(entry.getValue().rank.refName).append(",");
				}
				String finalMembers = members.substring(0, members.length() - 1);

				statement.setString(17, finalMembers);
				statement.executeUpdate();
			} catch(SQLException e) { e.printStackTrace(); }
		}

	}

	@Override
	public List<String> getTabComplete(Player player, String s, List<String> list) {
		return null;
	}
}

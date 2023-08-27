package net.pitsim.arcticguilds.inventories;

import dev.kyro.arcticapi.gui.AGUI;
import net.pitsim.arcticguilds.controllers.objects.Guild;
import org.bukkit.entity.Player;

public class MenuGUI extends AGUI {
	public Guild guild;

	public MenuPanel menuPanel;
	public DyePanel dyePanel;
	public BuffPanel buffPanel;
	public UpgradePanel upgradePanel;
	public ShopPanel shopPanel;
	public SettingsPanel settingsPanel;

	public MenuGUI(Player player, Guild guild) {
		super(player);
		this.guild = guild;

		this.menuPanel = new MenuPanel(this);
		this.dyePanel = new DyePanel(this);
		this.buffPanel = new BuffPanel(this);
		this.upgradePanel = new UpgradePanel(this);
		this.shopPanel = new ShopPanel(this);
		this.settingsPanel = new SettingsPanel(this);

		setHomePanel(menuPanel);
	}
}

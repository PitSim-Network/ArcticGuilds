package dev.kyro.arcticguilds.events;

import dev.kyro.arcticguilds.controllers.objects.Guild;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GuildReputationEvent extends GuildEvent {
	private Player player;
	private int amount;
	private List<Double> multipliers = new ArrayList<>();

	public GuildReputationEvent(Guild guild, @Nullable Player player, int amount) {
		super(guild);
		this.player = player;
		this.amount = amount;
	}

	public Player getPlayer() {
		return player;
	}

	public int getAmount() {
		return amount;
	}

	public void addMultiplier(double multiplier) {
		multipliers.add(multiplier);
	}

	public void clearMultipliers() {
		multipliers.clear();
	}

	public int getTotalReputation() {
		double amount = this.amount;
		for(Double multiplier : multipliers) amount *= multiplier;
		return (int) amount;
	}
}

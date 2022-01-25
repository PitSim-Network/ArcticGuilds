package dev.kyro.arcticguilds.misc;

import dev.kyro.arcticguilds.ArcticGuilds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Sounds {

	//	General
	public static final SoundEffect SUCCESS = new SoundEffect(Sound.NOTE_PLING, 1, 2);
	public static final SoundEffect ERROR = new SoundEffect(Sound.ENDERMAN_TELEPORT, 1, 0.5);
	public static final SoundEffect NO = new SoundEffect(Sound.VILLAGER_NO, 1, 1);
	public static final SoundEffect WARNING_LOUD = new SoundEffect(Sound.NOTE_PLING, 1000, 1);

	//	Game / Misc
	public static final SoundEffect UPGRADE = new SoundEffect(Sound.LEVEL_UP, 1, 1);
	public static final SoundEffect RESET = new SoundEffect(Sound.ANVIL_BREAK, 1, 1);

	/*
	public static final SoundEffect SIMPLE_SOUND = new SoundEffect(Sound.ZOMBIE_WOODBREAK, 1, 1);
	public static final SoundEffect COMPLEX_SOUND = new SoundEffect();
	static {
		COMPLEX_SOUND
				.add(new SoundMoment(0)
						.add(Sound.ORB_PICKUP, 1, 1)
						.add(Sound.EXPLODE, 1, 1))
				.add(new SoundMoment(2)
						.add(Sound.ORB_PICKUP, 1, 1)
						.add(Sound.IRONGOLEM_DEATH, 1, 1));
	}
	*/

	public static class SoundEffect {
		private SoundMoment soundMoment;
		private final List<SoundMoment> soundTimeList = new ArrayList<>();

		public SoundEffect() {
		}

		public SoundEffect(Sound sound, double volume, double pitch) {
			this.soundMoment = new SoundMoment(new SoundMoment.BukkitSound(sound, volume, pitch));
		}

		public SoundEffect(String soundString, double volume, double pitch) {
			this.soundMoment = new SoundMoment(new SoundMoment.BukkitSound(soundString, volume, pitch));
		}

		public SoundEffect add(SoundMoment soundMoment) {
			soundTimeList.add(soundMoment);
			return this;
		}

		public void play(Location location, double radius) {
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if(onlinePlayer.getWorld() != location.getWorld() || onlinePlayer.getLocation().distance(location) > radius) continue;
				play(onlinePlayer);
			}
		}

		public void play(Player player) {
			if(!player.isOnline()) return;
			if(soundMoment != null) {
				soundMoment.play(player);
				return;
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					int count = 0;
					List<SoundMoment> toRemove = new ArrayList<>();
					for(SoundMoment soundMoment : soundTimeList) {
						if(soundMoment.tick == count) {
							toRemove.add(soundMoment);
							soundMoment.play(player);
						}
						count++;
					}
					soundTimeList.removeAll(toRemove);
					if(soundTimeList.isEmpty()) cancel();
				}
			}.runTaskTimer(ArcticGuilds.INSTANCE, 0L, 1L);
		}

		public void play(Location location) {
			if(soundMoment != null) {
				soundMoment.play(location);
				return;
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					int count = 0;
					List<SoundMoment> toRemove = new ArrayList<>();
					for(SoundMoment soundMoment : soundTimeList) {
						if(soundMoment.tick == count) {
							toRemove.add(soundMoment);
							soundMoment.play(location);
						}
						count++;
					}
					soundTimeList.removeAll(toRemove);
					if(soundTimeList.isEmpty()) cancel();
				}
			}.runTaskTimer(ArcticGuilds.INSTANCE, 0L, 1L);
		}
	}

	public static class SoundMoment {
		private final List<BukkitSound> bukkitSounds = new ArrayList<>();
		private int tick;

		//			Time constructor
		public SoundMoment(int tick) {
			this.tick = tick;
		}

		//			Sound with no time
		public SoundMoment(BukkitSound bukkitSound) {
			this.bukkitSounds.add(bukkitSound);
		}

		//			Add sound to time constructed with time constructor
		public SoundMoment add(Sound sound, int volume, double pitch) {
			bukkitSounds.add(new BukkitSound(sound, volume, pitch));
			return this;
		}
		public SoundMoment add(String soundString, int volume, double pitch) {
			bukkitSounds.add(new BukkitSound(soundString, volume, pitch));
			return this;
		}

		public void play(Player player) {
			if(!player.isOnline()) return;
			for(BukkitSound bukkitSound : bukkitSounds) {
				if(bukkitSound.sound != null) {
					player.playSound(player.getLocation(), bukkitSound.sound, bukkitSound.volume, bukkitSound.pitch);
				} else {
					player.playSound(player.getLocation(), bukkitSound.soundString, bukkitSound.volume, bukkitSound.pitch);
				}
			}
		}
		public void play(Location location) {
			for(BukkitSound bukkitSound : bukkitSounds) {
				if(bukkitSound.sound != null) location.getWorld().playSound(location, bukkitSound.sound, bukkitSound.volume, bukkitSound.pitch);
			}
		}

		public static class BukkitSound {
			private Sound sound;
			private String soundString;
			private final float volume;
			private final float pitch;

			private BukkitSound(Sound sound, double volume, double pitch) {
				this.sound = sound;
				this.volume = (float) volume;
				this.pitch = (float) pitch;
			}

			private BukkitSound(String soundString, double volume, double pitch) {
				this.soundString = soundString;
				this.volume = (float) volume;
				this.pitch = (float) pitch;
			}
		}
	}
}

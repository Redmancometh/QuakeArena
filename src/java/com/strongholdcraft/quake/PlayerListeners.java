package com.strongholdcraft.quake;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerListeners implements Listener {
	@EventHandler
	public void startGame(final PlayerLoginEvent event) {
		if (Bukkit.getServer().getOnlinePlayers().size() == 0
				&& !(GameInProgress.getInProgress())) {
			GameInProgress.startGame();
		}
		GameInProgress.addPlayer(event.getPlayer());
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager()
				.getPlugin("QuakeArena"), new Runnable() {
			public void run() {
				ScoreBoardListeners.attachScoreboard();
			}
		}, 3);
	}
}

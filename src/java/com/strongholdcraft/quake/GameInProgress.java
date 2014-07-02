package com.strongholdcraft.quake;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameInProgress {
	private static HashMap<Player, Integer> scores = new HashMap();
	private int totalkills;
	private static Player[] players = new Player[20];
	private static boolean inProgress;

	public static void startGame() {
		inProgress = true;
		Bukkit.broadcastMessage("STARTED");
	}

	public static void stopGame() {
		inProgress = false;
	}

	public static boolean getInProgress() {
		return inProgress;
	}

	public static void addPlayer(Player p) {
		players[Bukkit.getOnlinePlayers().length] = p;
		if (!scores.containsKey(p)) {
			scores.put(p, 0);
		}
	}

	public static int getScore(Player p) {
		return scores.get(p);
	}
}

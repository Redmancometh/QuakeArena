package com.strongholdcraft.quake;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoardListeners {
	private static Scoreboard board;
	private static Score[] score;

	@SuppressWarnings("deprecation")
	public static void createScoreboard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.LIGHT_PURPLE + "Kills");
		Team team = board.registerNewTeam("Red");
		score[0] = objective.getScore(p);
		Player[] online = Bukkit.getServer().getOnlinePlayers();
		for (int x = 0; x < online.length; x++) {
			GameInProgress.getScore(online[x]);
			score[x].setScore(GameInProgress.getScore(online[x]));
		}
		p.setScoreboard(board);
	}

	public static void attachScoreboard() {

	}
}

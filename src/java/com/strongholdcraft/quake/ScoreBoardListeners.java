package com.strongholdcraft.quake;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreBoardListeners 
{
	private static Scoreboard board;
	private static Team team;
	private static Score[] score;
	private static Objective objective;
	public static void createScoreboard(Player p)
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy");   
	    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    objective.setDisplayName(ChatColor.LIGHT_PURPLE+"Kills");
		Team team = board.registerNewTeam("Red");
	    score[0] = objective.getScore(p);
	    Player[] online = Bukkit.getServer().getOnlinePlayers();
	    for(int x = 0; x<online.length; x++)
	    {
	    	GameInProgress.getScore(online[x]);
	    	score[x].setScore(GameInProgress.getScore(online[x]));
	    }
	    p.setScoreboard(board);
	}
	public static void attachScoreboard()
	{
	}


}

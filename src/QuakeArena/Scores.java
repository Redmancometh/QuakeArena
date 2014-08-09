package QuakeArena;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Scores implements Listener
{
    static Plugin pl;
    public Scores(Plugin pl)
    {
	this.pl=pl;
    }
    static ScoreboardManager manager;
    static Scoreboard board;
    static Objective objective;
    static HashMap<Player, Integer>scores = new HashMap<Player, Integer>();
    @EventHandler
    public static void addPlayer(PlayerLoginEvent e)
    {
	if(!scores.containsKey(e.getPlayer()))
	{
	    scores.put(e.getPlayer(), 1);
	    Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() 
	    {
		@Override
		public void run() 
		{
		    updateBoard();
		}
		}, 5);
	}
    }
    public static void updateBoard()
    {
	manager = Bukkit.getScoreboardManager();
	board = manager.getNewScoreboard();
	Objective objective = board.registerNewObjective("test", "dummy");
	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	objective.setDisplayName(ChatColor.GREEN+"Kills");
	for(Player player : Bukkit.getOnlinePlayers()) 
	{
	    Score score = objective.getScore(player.getDisplayName());
	    score.setScore(scores.get(player));
	    player.setScoreboard(board);
	}
    }
    public static int getScore(Player p){return scores.get(p);}
    public static void addScore(Player p)
    {
	scores.put(p, (scores.get(p)+1));
	updateBoard();
	if(scores.get(p)>=25){GameInProgress.stopGame();}
    }
    public static void setScore(Player p, Integer score){scores.put(p, score);}
}

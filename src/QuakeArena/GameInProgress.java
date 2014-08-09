package QuakeArena;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class GameInProgress 
{
    private static HashMap<Player, Integer> scores = new HashMap();
    private int totalkills;
    private static Player[] players = new Player[20];
    private static boolean inProgress;
    public static void startGame(){inProgress=true;Bukkit.broadcastMessage("STARTED");}
    public static void stopGame()
    {
	inProgress=false;Bukkit.broadcastMessage("STOP");
	Bukkit.getServer().shutdown();
    }
    public static boolean getInProgress(){return inProgress;}
}

package QuakeArena;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerListeners implements Listener
{
    static Plugin pl;
    public PlayerListeners(Plugin pl)
    {
	this.pl=pl;
    }
    @EventHandler
    public void startGame(PlayerLoginEvent event)
    {
	if(Bukkit.getServer().getOnlinePlayers().length==0&&!(GameInProgress.getInProgress()))
	{
	    GameInProgress.startGame();
	}
    }
    @EventHandler
    public void playerKilled(PlayerDeathEvent e)
    {
	if(e.getEntity().getKiller() instanceof Egg)
	{
	    Egg egg = (Egg) e.getEntity().getKiller();
	    if(egg.getShooter() instanceof Player)
	    {
		Scores.addScore((Player)egg.getShooter());
		Scores.updateBoard();
	    }
	}
	else
	{
	    Scores.addScore(e.getEntity().getKiller());
	    Scores.updateBoard();
	}
    }


}

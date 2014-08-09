package WeaponListeners;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class ShotGunListener implements Listener
{
    static Plugin pl;
    int x = 0;
    int taskid;
    HashMap<Player, Long> cooldown = new HashMap();
    public ShotGunListener(Plugin pl)
    {
	this.pl=pl;
    }
    @EventHandler
    public void fireShotGun(PlayerInteractEvent e)
    {
	final Random r = new Random();
	final Player p = e.getPlayer();
	if(p.getItemInHand().getType()==Material.WOOD_SPADE&&e.getAction() == Action.RIGHT_CLICK_AIR)
	{
	    if ((!cooldown.containsKey(p)) || (System.currentTimeMillis() - cooldown.get(p) > 800))
	    {
		cooldown.put(p, System.currentTimeMillis());
		for(int x = 0; x<15; x++)
		{
		   final Egg egg = p.throwEgg();
		   Vector v = new Vector(r.nextDouble() - 0.89, r.nextDouble() - 0.65, r.nextDouble() - 0.89); //Add the velocity by a random number		  
		   egg.setVelocity(egg.getVelocity().add(v).normalize());
		   egg.setVelocity(egg.getVelocity().normalize().multiply(2));
		   new BukkitRunnable()
		   {
		       public void run()
		       {
			   egg.remove();
		       }
		   }.runTaskLater(pl, 6);
		}
	    }
	}
    }
}

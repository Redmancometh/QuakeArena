package WeaponListeners;

import java.util.HashMap;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class GrenadeLauncher implements Listener
{
    static Plugin pl;
    static HashMap<Player, Long> cooldown = new HashMap();
    int taskid;

    public GrenadeLauncher(Plugin pl)
    {
	this.pl = pl;
    }

    @EventHandler
    public void fireNade(final PlayerInteractEvent e)
    {
	e.setCancelled(true);
	final Random r = new Random();
	final Player p = e.getPlayer();
	if (p.getItemInHand().getType() == Material.IRON_AXE&&e.getAction() == Action.RIGHT_CLICK_AIR)
	{
	    if ((!cooldown.containsKey(p)) || (System.currentTimeMillis() - cooldown.get(p) > 200))
	    {
		cooldown.put(p, System.currentTimeMillis());
		final Item i = p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.BLAZE_ROD));
		Vector v = p.getLocation().getDirection().multiply(1);
		i.setVelocity(v.add(new Vector(0,.3,0)));
		new BukkitRunnable()
		{
		    @Override
		    public void run()
		    {
			if (i.isOnGround())
			{
    			    i.remove();
    			    i.getWorld().createExplosion(i.getLocation(), 1);
    			    this.cancel();
			}		    
		    }
		}.runTaskTimer(pl, 2, 2);
	    }
	}

    }
}
// Create the task anonymously and schedule to run it once, after 20 ticks


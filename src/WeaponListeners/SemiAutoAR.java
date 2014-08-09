package WeaponListeners;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class SemiAutoAR implements Listener
{
    static Plugin pl;
    int taskid;
    HashMap<Player, Long> cooldown = new HashMap();

    public SemiAutoAR(Plugin pl)
    {
	this.pl = pl;
    }

    @EventHandler
    public void fireMachineGun(PlayerInteractEvent e)
    {
	final Random r = new Random();
	final Player p = e.getPlayer();
	if ((!cooldown.containsKey(p)) || (System.currentTimeMillis() - cooldown.get(p) > 350))
	{
	    if (p.getItemInHand().getType() == Material.WOOD_AXE && e.getAction() == Action.RIGHT_CLICK_AIR)
	    {
		if (p.getItemInHand().getDurability() < 63)
		{
		    p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() + 9));
		    cooldown.put(p, System.currentTimeMillis());
		    new BukkitRunnable()
		    {

			int x = 0;

			@Override
			public void run()
			{
			    p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 90, 1.1F);
			    Vector v = new Vector(r.nextInt(6) / 7, r.nextInt(5) / 10, r.nextInt(6) / 7);
			    Egg a = p.throwEgg();
			    a.setVelocity(a.getVelocity().normalize().multiply(3.8));
			    a.setVelocity(a.getVelocity().add(v));
			    this.cancel();
			}
		    }.runTaskTimer(pl, 0, 60);
		}
		else
		{
		    p.sendMessage(ChatColor.DARK_RED + "Left Click to Reload");
		}
	    }
	    if (p.getItemInHand().getType() == Material.WOOD_AXE && e.getAction() == Action.LEFT_CLICK_AIR)
	    {
		new BukkitRunnable()
		{
		    public void run()
		    {
			p.sendMessage(ChatColor.GOLD + "Reloading...");
			p.getItemInHand().setDurability((short) 0);
		    }
		}.runTaskLater(pl, 10);
	    }
	}

    }
}

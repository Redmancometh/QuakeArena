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

public class BurstARListener implements Listener
{
    static Plugin pl;
    int taskid;
    HashMap<Player, Long> cooldown = new HashMap();
    HashMap<Player, Boolean> reloading = new HashMap();
    public BurstARListener(Plugin pl)
    {
	this.pl=pl;
    }
    @EventHandler
    public void fireMachineGun(PlayerInteractEvent e)
    {
	final Random r = new Random();
	final Player p = e.getPlayer();
	if(p.getItemInHand().getType()==Material.STONE_AXE&&e.getAction() == Action.RIGHT_CLICK_AIR)
	{
	    if(p.getItemInHand().getDurability()<150)
	    {
		if ((!cooldown.containsKey(p)) || (System.currentTimeMillis() - cooldown.get(p) > 800))
		{
		    p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability()+25));
		    cooldown.put(p, System.currentTimeMillis());
		    new BukkitRunnable()
		    {
			int x = 0;
			@Override
			public void run() 
			{
			    p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 40, 4);			
			    Vector v = new Vector(r.nextInt(8)/10, r.nextInt(3)/10, r.nextInt(8)/10);
			    Egg a = p.throwEgg();
			    a.setVelocity(a.getVelocity().multiply(2.8));
			    a.setVelocity(a.getVelocity().add(v));
			    x++;
			    if(x>2){x=0;this.cancel();}
			}
		    }.runTaskTimer(pl, 1, 2);
		}
	    }
	    else
	    {
		p.sendMessage(ChatColor.DARK_RED+"Left Click to Reload");
	    }
	}
	if(p.getItemInHand().getType()==Material.STONE_AXE&&e.getAction() == Action.LEFT_CLICK_AIR)
	{
		reloading.put(p, true);
		new BukkitRunnable()
	    	{
			public void run()
			{
			    p.sendMessage(ChatColor.GOLD+"Reloading...");
			    p.getItemInHand().setDurability((short) 0);
			}
	    	}.runTaskLater(pl, 10);
	 }
    }
}

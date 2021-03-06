package WeaponListeners;

import java.awt.event.ItemEvent;
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
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class SniperListener implements Listener
{
    static Plugin pl;
    int taskid;
    HashMap<Player, Long> cooldown = new HashMap();
    HashMap<Player, Long> zcooldown = new HashMap();
    HashMap<Player, Boolean> zoom = new HashMap();

    public SniperListener(Plugin pl)
    {
	this.pl=pl;
    }
    @EventHandler
    public void fireSniper(final PlayerInteractEvent e)
    {
	e.setCancelled(true);
	final Random r = new Random();
	final Player p = e.getPlayer();
	if(p.getItemInHand().getType()==Material.DIAMOND_AXE)
	{
	    if(e.getAction() == Action.RIGHT_CLICK_AIR)
	    {
		if ((!cooldown.containsKey(p)) || (System.currentTimeMillis() - cooldown.get(p) > 2500))
	    	{
		    p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 90, 1.1f);
		    cooldown.put(p, System.currentTimeMillis());
		    Egg egg = p.throwEgg();
		    egg.setVelocity(p.getLocation().getDirection());
		    egg.setVelocity(egg.getVelocity().normalize().multiply(5));
	    	}
	    }
	    if (p.getItemInHand().getType() == Material.DIAMOND_AXE && e.getAction() == Action.LEFT_CLICK_AIR)
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
    @EventHandler
    public void zoom(PlayerToggleSneakEvent e)
    {
	Player p = e.getPlayer();
	if(e.getPlayer().getItemInHand().getType() == Material.DIAMOND_AXE)
	{
	    if(p.hasPotionEffect(PotionEffectType.SLOW))
	    {
		p.removePotionEffect(PotionEffectType.SLOW);
	    }
	    else
	    {
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 24000,4));
	    }
	}
    }
    @EventHandler
    public void changeItem(PlayerItemHeldEvent e)
    {
	Player p = e.getPlayer();
	if(p.getInventory().getItem(e.getPreviousSlot()).getType() == Material.DIAMOND_AXE)
	{
	    if(p.hasPotionEffect(PotionEffectType.SLOW))
	    {
		p.removePotionEffect(PotionEffectType.SLOW);
	    }
	}
    }
    
}

package WeaponListeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Flamethrower implements Listener
{
    Plugin pl;

    public Flamethrower(Plugin pl)
    {
	this.pl = pl;
    }

    @EventHandler
    public void shootFlames(final PlayerInteractEvent e)
    {
	if (e.getPlayer().getItemInHand().getType() == Material.STONE_PICKAXE)
	{
	    if (e.getAction() == Action.RIGHT_CLICK_AIR)
	    {
		if (e.getPlayer().getItemInHand().getDurability() < 63)
		{
		    e.getPlayer().getItemInHand().setDurability((short) (e.getPlayer().getItemInHand().getDurability() + 9));
		    new BukkitRunnable()
		    {
			public void run()
			{
			    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.FUSE, 10, .5f);
			    final SmallFireball f = e.getPlayer().launchProjectile(SmallFireball.class);
			    this.cancel();
			    new BukkitRunnable()
			    {
				public void run()
				{
				    f.remove();
				}
			    }.runTaskLater(pl, 18);
			}
		    }.runTaskTimer(pl, 2, 4);

		}
		else
		{
			e.getPlayer().sendMessage(ChatColor.GOLD + "Reloading...");
			e.getPlayer().getItemInHand().setDurability((short) 0);
		}
	    }
	    if (e.getPlayer().getItemInHand().getType() == Material.STONE_PICKAXE && e.getAction() == Action.LEFT_CLICK_AIR)
	    {
		new BukkitRunnable()
		{
		    public void run()
		    {
			e.getPlayer().sendMessage(ChatColor.GOLD + "Reloading...");
			e.getPlayer().getItemInHand().setDurability((short) 0);
		    }
		}.runTaskLater(pl, 10);
	    }
	}
    }
}

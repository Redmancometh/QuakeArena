package com.strongholdcraft.quake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class WeaponListeners implements Listener
{
	HashMap<Player, Integer> reloading = new HashMap();
	HashMap<Player, Long> cooldown = new HashMap();
	private BukkitTask task;
	private BukkitTask task2;
	private double difference = .01;
	private Plugin pl;
	public WeaponListeners(Plugin pl)
	{
		this.pl=pl;
	}
	@EventHandler
	public void rightClick(PlayerInteractEvent e)
	{
		final Player p = e.getPlayer();
		if(p.getItemInHand().getType()==Material.REDSTONE_TORCH_ON)
		{
			Bukkit.broadcastMessage("CD CONT "+cooldown.containsKey(p));
			Bukkit.broadcastMessage("Difference: "+difference);
				task = Bukkit.getScheduler().runTaskTimer(pl, new Runnable() 
				{
					int tick=0;
					@Override
					public void run() 
					{
						if(tick>95)
						{
							FallingBlock fb = p.getWorld().spawnFallingBlock(p.getEyeLocation(), Material.LAVA, (byte) 9);		
							Vector v = (p.getEyeLocation().getDirection());
							v.add(new Vector(0,2,0));
							v.angle(p.getEyeLocation().getDirection());
							v.multiply(.5);
					        fb.setVelocity(v);
					        task.cancel();
					        cooldown.put(p, System.currentTimeMillis());
						    task.cancel();
						    tick=0;
						}
						else
						{
							tick+=5;
							Bukkit.broadcastMessage("tick"+tick+" Difference: "+difference);
						}
					}
				}, 0, 5);	
		}
	}
    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) 
    {
    	List<Entity> damagelist = new ArrayList<Entity>();
        if ((event.getEntityType() == EntityType.FALLING_BLOCK)) 
        {
        	FallingBlock fb = (FallingBlock) event.getEntity();
        	event.getBlock().getWorld().createExplosion(fb.getLocation(), 0, false);
        	fb.getLocation().getWorld().playEffect(fb.getLocation(), Effect.EXPLOSION_HUGE, 10);
        	damagelist = fb.getNearbyEntities(5,5,5);
        	for(Iterator<Entity> iter = damagelist.iterator(); iter.hasNext();)
        	{
        		Entity e = iter.next();
        		if(e instanceof Player)
        		{
        			Damageable d = (Damageable)e;
        			d.setHealth(d.getHealth()-((double)9));
        			d.playEffect(EntityEffect.HURT);
        		}
        	}
        	event.setCancelled(true);
        }
    }
}

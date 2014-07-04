package com.strongholdcraft.quake;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WeaponListeners implements Listener {
	HashMap<Player, Integer> reloading = new HashMap<Player, Integer>();
	HashMap<Player, Long> coolDown = new HashMap<Player, Long>();
	private BukkitTask task;
	private double difference = .01;
	private Plugin pl;

	public WeaponListeners(Plugin pl) {
		this.pl = pl;
	}

	@EventHandler
	public void rightClick(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.REDSTONE_TORCH_ON) {
			Bukkit.broadcastMessage("CD CONT " + coolDown.containsKey(p));
			Bukkit.broadcastMessage("Difference: " + difference);
			task = Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {
				int tick = 0;

				@Override
				public void run() {
					if (tick > 95) {
						@SuppressWarnings("deprecation")
						FallingBlock fb = p.getWorld().spawnFallingBlock(
								p.getEyeLocation(), Material.LAVA, (byte) 9);
						Vector v = (p.getEyeLocation().getDirection());
						v.add(new Vector(0, 2, 0));
						v.angle(p.getEyeLocation().getDirection());
						v.multiply(.5);
						fb.setVelocity(v);
						task.cancel();
						coolDown.put(p, System.currentTimeMillis());
						task.cancel();
						tick = 0;
					} else {
						tick += 5;
						Bukkit.broadcastMessage("tick" + tick + " Difference: "
								+ difference);
					}
				}
			}, 0, 5);
		}
	}

	@EventHandler
	public void onBlockFall(EntityChangeBlockEvent event) {
		List<Entity> damageList = new ArrayList<Entity>();
		if ((event.getEntityType() == EntityType.FALLING_BLOCK)) {
			FallingBlock fb = (FallingBlock) event.getEntity();
			event.getBlock().getWorld()
					.createExplosion(fb.getLocation(), 0, false);
			fb.getLocation().getWorld()
					.playEffect(fb.getLocation(), Effect.EXPLOSION_HUGE, 10);
			damageList = fb.getNearbyEntities(5, 5, 5);
			for (Iterator<Entity> iterator = damageList.iterator(); iterator
					.hasNext();) {
				Entity e = iterator.next();
				if (e instanceof Player) {
					Damageable d = (Damageable) e;
					d.setHealth(d.getHealth() - ((double) 9));
					d.playEffect(EntityEffect.HURT);
				}
			}
			event.setCancelled(true);
		}
	}
}

package WeaponListeners;

import java.util.List;

import me.fromgate.playeffect.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class ProjectileListeners implements Listener
{
    @EventHandler
    public void stopEgg(EntityDamageByEntityEvent e)
    {
	if(e.getDamager() instanceof Egg)
	{
	    if(!(e.getEntity() instanceof LivingEntity))
	    {
		e.setCancelled(true);
	    }
	}
    }
    @EventHandler
    public void stopChickens(CreatureSpawnEvent e)
    {
	if(e.getSpawnReason()==SpawnReason.EGG)
	{
	    e.setCancelled(true);
	}
    }
    @EventHandler
    public void onBulletHit(EntityDamageByEntityEvent e)
    {
	if(e.getDamager() instanceof Egg)
	{
	    if(e.getEntity() instanceof LivingEntity)
	    {
		Egg egg = (Egg)e.getDamager();
		Player p = (Player)egg.getShooter();
		if(p.getItemInHand().getType() == Material.WOOD_AXE)
		{
		    e.setDamage(5);
		}
		if(p.getItemInHand().getType() == Material.STONE_AXE)
		{
		    e.setDamage(7);
		}
		if(p.getItemInHand().getType() == Material.WOOD_SPADE)
		{
		    e.setDamage(8);
		}
		if(p.getItemInHand().getType() == Material.DIAMOND_AXE)
		{
		    e.setDamage(15);
		}
		if(p.getItemInHand().getType() == Material.GOLD_SPADE)
		{
		    e.setDamage(3);
		}
		if(p.getItemInHand().getType() == Material.GOLD_AXE)
		{
		    e.setDamage(5);
		}
	    }
	}
    }
    @EventHandler
    public void onBulletImpact(ProjectileHitEvent event)
    {
	Entity e = event.getEntity();
	BlockIterator bi = new BlockIterator(e.getWorld(), e.getLocation().toVector(), e.getVelocity().normalize(), 0, 2);
	Block b = getHitBlock(bi);
	Material m = b.getType();
	if(m==Material.GLASS||m==Material.THIN_GLASS){b.breakNaturally();}
	Bukkit.broadcastMessage("Type: "+b.getType().toString());
    }
    public Block getHitBlock(BlockIterator bi)
    {
	while(bi.hasNext())
	{
	    Block b = bi.next();
	    if(b.getTypeId()!=0){return b;}
	}
	return null;
    }
}

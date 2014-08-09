package WeaponListeners;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

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
}

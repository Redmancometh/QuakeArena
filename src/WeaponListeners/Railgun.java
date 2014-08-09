package WeaponListeners;

import java.util.List;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.VisualEffect;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Railgun implements Listener
{
    Plugin pl;
    public Railgun(Plugin pl)
    {
	this.pl=pl;
    }
    @EventHandler
    public void shootFlames(final PlayerInteractEvent ev)
    {
	final Player p = ev.getPlayer();
	if(ev.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE)
	{
	    if(ev.getAction() == Action.RIGHT_CLICK_AIR)
	    {
		int x = 0;
	        Location loc1 = p.getEyeLocation();
	        @SuppressWarnings("deprecation")
	        Location loc2 = p.getTargetBlock(null, 100).getLocation();
	        if (loc1 == null) return;
	        if (loc2 == null) return;
	        List<Location> line = Util.buildLine(loc1, loc2); // provided by PlayEffect
	        if (line.size()<=2) return;
	        line.remove(0);
	        String param = "loc:"+Util.locationToStrLoc(loc1)+" loc2:"+Util.locationToStrLoc(loc2)+" draw:line color:red type:ball";
	        PlayEffect.play(VisualEffect.FIREWORK, param);
	        for (Location l : line)
	        {
	            for (Entity e : l.getChunk().getEntities())
	            {
	                if (!(e instanceof LivingEntity)) continue;
	                LivingEntity le = (LivingEntity) e;
	                if (le.getLocation().distance(l)<=1.5) 
	                {
	                    Damageable d = (Damageable)le;
	                    le.damage(d.getMaxHealth()*0.55);   
	                }
	            }
	        }
	    }
	}
    }
}

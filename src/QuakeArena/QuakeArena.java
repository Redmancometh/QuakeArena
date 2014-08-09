package QuakeArena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import WeaponListeners.BattleRifle;
import WeaponListeners.BurstARListener;
import WeaponListeners.Flamethrower;
import WeaponListeners.GrenadeLauncher;
import WeaponListeners.LMGAuto;
import WeaponListeners.ProjectileListeners;
import WeaponListeners.Railgun;
import WeaponListeners.SemiAutoAR;
import WeaponListeners.ShotGunListener;
import WeaponListeners.SniperListener;


public class QuakeArena extends JavaPlugin
{
    JavaPlugin pl = this;
    public void onEnable()
    {
	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(new PlayerListeners(this), this);
	pm.registerEvents(new Scores(this), this);
	pm.registerEvents(new ProjectileListeners(), this);
	pm.registerEvents(new ShotGunListener(this), this);
	pm.registerEvents(new SniperListener(this), this);
	pm.registerEvents(new GrenadeLauncher(this), this);
	pm.registerEvents(new BurstARListener(this), this);
	pm.registerEvents(new SemiAutoAR(this), this);
	pm.registerEvents(new Flamethrower(this), this);
	pm.registerEvents(new BattleRifle(this), this);
	pm.registerEvents(new LMGAuto(this), this);
	pm.registerEvents(new Railgun(this), this);
    }
    
}

package com.strongholdcraft.quake;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class QuakeArena extends JavaPlugin
{
	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new PlayerListeners(), this);
	    pm.registerEvents(new WeaponListeners(this), this);
	}
}

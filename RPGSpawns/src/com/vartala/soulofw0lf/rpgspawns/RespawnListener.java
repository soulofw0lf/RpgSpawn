package com.vartala.soulofw0lf.rpgspawns;



import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;



public class RespawnListener implements Listener {
	RpgSpawns Rpgs;
	public RespawnListener(RpgSpawns rpgs){
		this.Rpgs = rpgs;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Player k = event.getPlayer();
		Location loc = k.getLocation();
		World thisworld = loc.getWorld();
		String world = thisworld.getName();
		Double smallest = null;
		Location newspawn = loc;
		if (this.Rpgs.getConfig().contains(k.getName())){
			Player rezzer = Bukkit.getPlayer(this.Rpgs.getConfig().getString(k.getName() + ".rezzer"));
			String pitch = this.Rpgs.getConfig().getString(k.getName() + ".Pitch");
			String yaw = this.Rpgs.getConfig().getString(k.getName() + ".Yaw");
			Float newpitch = Float.parseFloat(pitch);
			Float newyaw = Float.parseFloat(yaw);
			Location newloc = new Location(thisworld,this.Rpgs.getConfig().getDouble(k.getName() + ".X"),this.Rpgs.getConfig().getDouble(k.getName() + ".Y"),this.Rpgs.getConfig().getDouble(k.getName() + ".Z"),newyaw,newpitch);
			event.setRespawnLocation(newloc);
			k.sendMessage(RpgSpawns.rezdby.replaceAll("&p", rezzer.getName()));
			rezzer.sendMessage(RpgSpawns.justrezd.replaceAll("&p",k.getName()));
			this.Rpgs.getConfig().set(k.getName(), null);
			this.Rpgs.saveConfig();
			return;
		}
		if (this.Rpgs.getConfig().getConfigurationSection(world) == null){
			Location mainspawn = thisworld.getSpawnLocation();
			event.setRespawnLocation(mainspawn);
			return;
		}
		final Set<String> keys = this.Rpgs.getConfig().getConfigurationSection(loc.getWorld().getName()).getKeys(false);
		if (keys.size() <= 0) {
			Location mainspawn = thisworld.getSpawnLocation();
			event.setRespawnLocation(mainspawn);
			return;
		}
		for(String key : this.Rpgs.getConfig().getConfigurationSection(world).getKeys(false)){
			if (this.Rpgs.getConfig().getString(world + "." + key + ".Perms") != null){
				String perms = this.Rpgs.getConfig().getString(world + "." + key + ".Perms");
				
				if (k.hasPermission(perms)){
				
					String pitch = this.Rpgs.getConfig().getString(world + "." + key + ".Pitch");
					String yaw = this.Rpgs.getConfig().getString(world + "." + key + ".Yaw");
					Float newpitch = Float.parseFloat(pitch);
					Float newyaw = Float.parseFloat(yaw);
					Location newloc = new Location(thisworld,this.Rpgs.getConfig().getDouble(world + "." + key + ".X"),this.Rpgs.getConfig().getDouble(world + "." + key + ".Y"),this.Rpgs.getConfig().getDouble(world + "." + key + ".Z"),newyaw,newpitch);
					Double dist = loc.distanceSquared(newloc);
					if ((smallest) == null){
						smallest = dist;

					}
					if (smallest >= dist){
						newspawn = newloc;
					}
				}
			}
			else 
			{ if (this.Rpgs.getConfig().getString(world + "." + key + ".Perms") == null){
				
				String pitch = this.Rpgs.getConfig().getString(world + "." + key + ".Pitch");
				String yaw = this.Rpgs.getConfig().getString(world + "." + key + ".Yaw");
				Float newpitch = Float.parseFloat(pitch);
				Float newyaw = Float.parseFloat(yaw);
				Location newloc = new Location(thisworld,this.Rpgs.getConfig().getDouble(world + "." + key + ".X"),this.Rpgs.getConfig().getDouble(world + "." + key + ".Y"),this.Rpgs.getConfig().getDouble(world + "." + key + ".Z"),newyaw,newpitch);
				Double dist = loc.distanceSquared(newloc);
				if ((smallest) == null){
					smallest = dist;

				}
				if (smallest >= dist){
					newspawn = newloc;
				}
			}
			}
		}
		event.setRespawnLocation(newspawn);
		return;
	}

}



package com.vartala.soulofw0lf.rpgspawns;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResHandler implements CommandExecutor {
	RpgSpawns Rpgs;
	public ResHandler(RpgSpawns rpgs){
		this.Rpgs = rpgs;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player) sender;
		Player p = Bukkit.getPlayer(args[0]);
		Location loc = player.getLocation();
		if (RpgSpawns.allowrez == true){
			if (!(player.hasPermission("spawns.res"))){
				player.sendMessage(RpgSpawns.Perms);
				return true;
			}
			if (args.length != 1){
				player.sendMessage(RpgSpawns.RezProper);
				return true;
			}
			if (p == null) {
				sender.sendMessage(RpgSpawns.PlayerNotFound);
				return true;
			}
			if (!(player.getLocation().getWorld().getName().equalsIgnoreCase(p.getLocation().getWorld().getName()))){
				player.sendMessage(RpgSpawns.wrongworld);
				return true;
			}
			double resdist = player.getLocation().distance(p.getLocation());

			if (resdist <= RpgSpawns.MaxDistance){
				Double x = loc.getX();
				Double y = loc.getY();
				Double z = loc.getZ();
				Float pitch = loc.getPitch();
				Float yaw = loc.getYaw();
				if (!(this.Rpgs.getConfig().contains(p.getName()))){
					if (p.isDead()){
						this.Rpgs.getConfig().set(p.getName() + ".X", x);
						this.Rpgs.getConfig().set(p.getName() + ".Y", y);
						this.Rpgs.getConfig().set(p.getName() + ".Z", z);
						this.Rpgs.getConfig().set(p.getName() + ".Pitch", pitch);
						this.Rpgs.getConfig().set(p.getName() + ".Yaw", yaw);
						this.Rpgs.getConfig().set(p.getName() + ".rezzer", player.getName());
						this.Rpgs.saveConfig();
						player.sendMessage(RpgSpawns.RezAttempt.replaceAll("&p", p.getName()));
						return true;
					} else { 
						player.sendMessage(RpgSpawns.NotDead);
						return true;
					}
				} else {
					player.sendMessage(RpgSpawns.AlreadyRezz);
					return true;
				}
			} else {
				player.sendMessage(RpgSpawns.BadDistance.replaceAll("&p", p.getName()));
				return true;
			}
		} else {
			player.sendMessage(RpgSpawns.RezDisabled);
			return true;
		}
		
	}
}

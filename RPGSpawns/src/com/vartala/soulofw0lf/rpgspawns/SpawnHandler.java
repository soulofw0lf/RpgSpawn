package com.vartala.soulofw0lf.rpgspawns;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnHandler implements CommandExecutor {
	RpgSpawns Rpgs;
	public SpawnHandler(RpgSpawns rpgs){
		this.Rpgs = rpgs;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player) sender;
		Location loc = player.getLocation();
		if (args.length <= 0){
			player.sendMessage(RpgSpawns.BadCommand);
			return true;
		}
		if (args[0].equalsIgnoreCase("reload")){
			if (!(player.isOp())){
				player.sendMessage(RpgSpawns.OnlyOp);
				return true;
			} else {
				player.sendMessage(RpgSpawns.ConfigLoaded);
				this.Rpgs.reloadConfig();
				this.Rpgs.loadConfig();
				return true;
			}
		}
		if ((!(args[0].equalsIgnoreCase("list"))) && (!(args[0].equalsIgnoreCase("del"))) && (!(args[0].equalsIgnoreCase("tp"))) && (!(args[0].equalsIgnoreCase("add")))){
			player.sendMessage(RpgSpawns.BadCommand);
			return true;
		}
		if (args[0].equalsIgnoreCase("list")){
			if (!(player.hasPermission("spawns.list"))){
			player.sendMessage(RpgSpawns.NoPerms);
			return true;
			}
			if (!(this.Rpgs.getConfig().contains(loc.getWorld().getName()))){
				player.sendMessage(RpgSpawns.NoSpawns);
				return true;
			}
			final Set<String> keys = this.Rpgs.getConfig().getConfigurationSection(loc.getWorld().getName()).getKeys(false);
			if (keys.size() <= 0) {
				player.sendMessage(RpgSpawns.NoSpawns);
				return true;
			}
			for (String key : this.Rpgs.getConfig().getConfigurationSection(loc.getWorld().getName()).getKeys(false)){
				
				player.sendMessage(key + "\n");
			}
			return true;
		}
		if (args[0].equalsIgnoreCase("tp")){
			if (!(player.hasPermission("spawns.tp"))){
				player.sendMessage(RpgSpawns.NoPerms);
				return true;
				}
			if (args.length != 2){
				player.sendMessage(RpgSpawns.BadCommand);
				return true;
			}
			if (!(this.Rpgs.getConfig().getConfigurationSection(loc.getWorld().getName()).contains(args[1]))){
				player.sendMessage(RpgSpawns.SpawnNotFound.replaceAll("&s", args[1]).replaceAll("&w", loc.getWorld().getName()));
				return true;
			}
			World thisworld = loc.getWorld();
			String world = thisworld.getName();
			String pitch = this.Rpgs.getConfig().getString(world + "." + args[1] + ".Pitch");
			String yaw = this.Rpgs.getConfig().getString(world + "." + args[1] + ".Yaw");
			Float newpitch = Float.parseFloat(pitch);
			Float newyaw = Float.parseFloat(yaw);
			Location newloc = new Location(thisworld,this.Rpgs.getConfig().getDouble(world + "." + args[1] + ".X"),this.Rpgs.getConfig().getDouble(world + "." + args[1] + ".Y"),this.Rpgs.getConfig().getDouble(world + "." + args[1] + ".Z"),newyaw,newpitch);
			player.teleport(newloc);
			return true;
		}
		if (args[0].equalsIgnoreCase("del")){
			if (!(player.hasPermission("spawns.del"))){
				player.sendMessage(RpgSpawns.NoPerms);
				return true;
				}
			if (args.length != 2){
				player.sendMessage(RpgSpawns.BadDeletion);
				return true;
			}
			if (!(this.Rpgs.getConfig().getConfigurationSection(loc.getWorld().getName()).contains(args[1]))){
				player.sendMessage(RpgSpawns.SpawnNotFound.replaceAll("&s", args[1]).replaceAll("&w", loc.getWorld().getName()));
				return true;
			}
			String world = loc.getWorld().getName();
			this.Rpgs.getConfig().getConfigurationSection(world).set(args[1],null);
			this.Rpgs.saveConfig();
			player.sendMessage(RpgSpawns.SpawnRemoved.replaceAll("&s", args[1]).replaceAll("&w", loc.getWorld().getName()));
			return true;
		}
		if (args[0].equalsIgnoreCase("add")){
			if (!(player.hasPermission("spawns.add"))){
				player.sendMessage(RpgSpawns.NoPerms);
				return true;
				}
			String name = args[1];
			if (args.length != 2){
				player.sendMessage(RpgSpawns.SpawnNeedsName);
				return true;
			}
			Double x = loc.getX();
			Double y = loc.getY();
			Double z = loc.getZ();
			Float pitch = loc.getPitch();
			Float yaw = loc.getYaw();
			World world = loc.getWorld();
			String World = world.getName();
			if (!(this.Rpgs.getConfig().contains(World + "." + name))){
			//this.Rpgs.getConfig().createSection(World + name);
			this.Rpgs.getConfig().set(World + "." + name + ".X", x);
			this.Rpgs.getConfig().set(World + "." + name + ".Y", y);
			this.Rpgs.getConfig().set(World + "." + name + ".Z", z);
			this.Rpgs.getConfig().set(World + "." + name + ".Pitch", pitch);
			this.Rpgs.getConfig().set(World + "." + name + ".Yaw", yaw);
			this.Rpgs.saveConfig();
			player.sendMessage(RpgSpawns.SpawnCreate.replaceAll("&s", args[1]).replaceAll("&w", loc.getWorld().getName()));
			return true;
			} else {
				player.sendMessage(RpgSpawns.SpawnExists);
				return true;
			}
		}
		return false;
	}
}

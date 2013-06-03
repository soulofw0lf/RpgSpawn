package com.vartala.soulofw0lf.rpgspawns;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class RpgSpawns extends JavaPlugin implements Listener{
	public static Boolean allowrez = null;
	public static String Perms = null;
	public static String RezProper = null;
	public static String PlayerNotFound = null;
	public static String RezAttempt = null;
	public static String AlreadyRezz = null;
	public static String BadDistance = null;
	public static String NotDead = null;
	public static String RezDisabled = null;
	public static String justrezd = null;
	public static String rezdby = null;
	public static String BadCommand = null;
	public static String NoPerms = null;
	public static String NoSpawns = null;
	public static String SpawnNotFound = null;
	public static String BadDeletion = null;
	public static String SpawnRemoved = null;
	public static String SpawnNeedsName = null;
	public static String SpawnCreate = null;
	public static String SpawnExists = null;
	public static String OnlyOp = null;
	public static String ConfigLoaded = null;
	public static Integer MaxDistance = null;
	public static String wrongworld = null;

	@Override
	public void onEnable(){
		getCommand("spawns").setExecutor(new SpawnHandler(this));
		getCommand("rez").setExecutor(new ResHandler(this));
		getServer().getPluginManager().registerEvents(new RespawnListener(this), this);
		saveDefaultConfig();
		loadConfig();
		getLogger().info("Rpg Spawns V1.5 has been enabled!");
	}
	public void onDisable(){
		getLogger().info("Rpg Spawns V1.5 has been disabled!");
	}
	public void loadConfig(){
		allowrez = getConfig().getBoolean("AllowRez");
		Perms = getConfig().getString("RezTerms.Perms").replaceAll("@", "§");
		wrongworld = getConfig().getString("RezTerms.Wrong World").replaceAll("@", "§");
		RezProper = getConfig().getString("RezTerms.Rez Proper").replaceAll("@", "§");
		PlayerNotFound = getConfig().getString("RezTerms.Player Not Found").replaceAll("@", "§");
		RezAttempt = getConfig().getString("RezTerms.Rez Attempt").replaceAll("@", "§");
		AlreadyRezz = getConfig().getString("RezTerms.Already Rezz").replaceAll("@", "§");
		BadDistance = getConfig().getString("RezTerms.Bad Distance").replaceAll("@", "§"); 
		NotDead = getConfig().getString("RezTerms.Not Dead").replaceAll("@", "§");
		RezDisabled = getConfig().getString("RezTerms.Rez Disabled").replaceAll("@", "§");
		rezdby = getConfig().getString("RezTerms.Ressurected By").replaceAll("@", "§");
		justrezd = getConfig().getString("RezTerms.Just Ressurected").replaceAll("@", "§");
		BadCommand = getConfig().getString("SpawnTerms.Bad Command").replaceAll("@", "§");
		NoPerms = getConfig().getString("SpawnTerms.No Perms").replaceAll("@", "§");
		NoSpawns = getConfig().getString("SpawnTerms.No Spawns").replaceAll("@", "§");
		SpawnNotFound = getConfig().getString("SpawnTerms.Spawn Not Found").replaceAll("@", "§");
		BadDeletion = getConfig().getString("SpawnTerms.Bad Deletion").replaceAll("@", "§");
		SpawnRemoved = getConfig().getString("SpawnTerms.Spawn Removed").replaceAll("@", "§");
		SpawnNeedsName = getConfig().getString("SpawnTerms.Spawn Needs Name").replaceAll("@", "§");
		SpawnCreate = getConfig().getString("SpawnTerms.Spawn Create").replaceAll("@", "§");
		SpawnExists = getConfig().getString("SpawnTerms.Spawn Exists").replaceAll("@", "§");
		OnlyOp = getConfig().getString("SpawnTerms.Only Op").replaceAll("@", "§");
		ConfigLoaded = getConfig().getString("SpawnTerms.Config Loaded").replaceAll("@", "§");
		MaxDistance = getConfig().getInt("Max Distance");
	}
}
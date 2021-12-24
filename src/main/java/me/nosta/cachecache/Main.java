package me.nosta.cachecache;

import me.nosta.cachecache.commands.CommandsCompleter;
import me.nosta.cachecache.commands.GameCommands;
import me.nosta.cachecache.managers.GameManager;
import me.nosta.cachecache.managers.InventoryManager;
import me.nosta.cachecache.managers.RoleManager;
import me.nosta.cachecache.managers.ScoreboardManager;
import me.nosta.cachecache.listeners.ChatListener;
import me.nosta.cachecache.listeners.ConnexionListener;
import me.nosta.cachecache.listeners.DamageListener;
import me.nosta.cachecache.listeners.InventoryListener;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;

	public static Main getInstance() {return instance;}

	public void onEnable() {
		instance = this;

		GameManager.getInstance();
		RoleManager.getInstance();
		InventoryManager.getInstance();
		ScoreboardManager.getInstance();
		
		this.config();
		this.registerListeners();
		this.registerCommands();        
    }
	
	
	public void config() {
		World world = this.getServer().getWorld("world");
		world.setDifficulty(Difficulty.PEACEFUL);
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		world.setGameRule(GameRule.NATURAL_REGENERATION, false);
		world.setGameRule(GameRule.DO_FIRE_TICK, false);
		world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
		world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);

		world.setGameRule(GameRule.DROWNING_DAMAGE, false);
		world.setGameRule(GameRule.FALL_DAMAGE, false);
		world.setGameRule(GameRule.FIRE_DAMAGE, false);
		world.setGameRule(GameRule.FREEZE_DAMAGE, false);
		//world.setTime(18000);
	}
	

	public void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new ConnexionListener(), this);
		this.getServer().getPluginManager().registerEvents(new DamageListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
	}
	
	private void registerCommands() {
		this.getCommand("cc").setExecutor(GameCommands.getInstance());
		this.getCommand("cc").setTabCompleter(CommandsCompleter.getInstance());
	}
}

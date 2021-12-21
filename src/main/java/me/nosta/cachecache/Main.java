package me.nosta.cachecache;

import me.nosta.cachecache.commands.CommandsCompleter;
import me.nosta.cachecache.commands.GameCommands;
import me.nosta.cachecache.game.GameInventories;
import me.nosta.cachecache.game.GameManager;
import me.nosta.cachecache.game.RoleManager;
import me.nosta.cachecache.listeners.ConnexionListener;
import me.nosta.cachecache.listeners.InventoryListener;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main instance;

	public void onEnable() {
		instance = this;

		new GameManager();
		new RoleManager();
		new GameInventories();
		
		this.config();
		this.registerListeners();
		this.registerCommands();        
    }
	
	
	public void config() {
		this.getServer().getWorld("world").setDifficulty(Difficulty.PEACEFUL);
		this.getServer().getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		this.getServer().getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, false);
		this.getServer().getWorld("world").setGameRule(GameRule.DO_FIRE_TICK, false);
		this.getServer().getWorld("world").setGameRule(GameRule.DO_MOB_SPAWNING, false);
		this.getServer().getWorld("world").setTime(18000);
	}
	

	public void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new ConnexionListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
	}
	
	private void registerCommands() {
		this.getCommand("cc").setExecutor(GameCommands.getInstance());
		this.getCommand("cc").setTabCompleter(CommandsCompleter.getInstance());
	}
}

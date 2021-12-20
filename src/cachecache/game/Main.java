package cachecache.game;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

import cachecache.commands.CommandsCompleter;
import cachecache.commands.GameCommands;
import cachecache.listeners.ConnexionListener;
import cachecache.listeners.InventoryListener;



public class Main extends JavaPlugin {
	
	public static Main instance;

	public void onEnable() {
		instance = this;
		
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

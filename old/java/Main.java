package me.nosta.cachecache;

import me.nosta.cachecache.commands.CommandsCompleter;
import me.nosta.cachecache.commands.GameCommands;
import me.nosta.cachecache.commands.RoleCommands;
import me.nosta.cachecache.listeners.*;
import me.nosta.cachecache.managers.*;
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
		RunnableManager.getInstance();
		PowerManager.getInstance();
		QuestManager.getInstance();
		SpawnManager.getInstance();
		DeathManager.getInstance();

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
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	private void registerCommands() {
		this.getCommand("cc").setExecutor(GameCommands.getInstance());
		this.getCommand("role").setExecutor(RoleCommands.getInstance());
		this.getCommand("cc").setTabCompleter(CommandsCompleter.getInstance());
	}
}
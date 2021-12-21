package me.nosta.cachecache.game;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.runnables.StartGameRunnable;
import org.bukkit.Bukkit;

public class GameManager {
	
	private static GameManager instance;
	
	private GameState gameState;

	public static GameManager getInstance() {
		if (instance == null) instance = new GameManager();
		return instance;
	}
	
	public GameManager() {
		this.setState(GameState.WAITING);
	}

	public void startGame() {
		this.setState(GameState.STARTING);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance,new StartGameRunnable(),0,10);
	}

	public void endGame() {
		this.setState(GameState.FINISHED);
	}
	
	public void resetGame() {
		this.setState(GameState.WAITING);
		RoleManager.getInstance().resetRoles();
		Bukkit.getScheduler().cancelTasks(Main.instance);
	}
	
	public GameState getState() {return this.gameState;}
	public void setState(GameState state) {this.gameState = state;}
}

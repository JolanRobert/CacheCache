package me.nosta.cachecache.game;

import me.nosta.cachecache.Main;
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

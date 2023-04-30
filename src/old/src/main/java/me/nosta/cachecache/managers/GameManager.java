package me.nosta.cachecache.managers;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.enums.GameState;
import me.nosta.cachecache.runnables.IngameRunnable;
import me.nosta.cachecache.runnables.PrepareGameRunnable;
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

	public void prepareGame() {
		this.setState(GameState.STARTING);
		new PrepareGameRunnable();
	}

	public void startGame() {
		this.setState(GameState.PLAYING);
		RunnableManager.getInstance().startGameRoleRunnables();
		new IngameRunnable();
	}
	
	public void endGame() {
		Bukkit.getScheduler().cancelTasks(Main.getInstance());
		this.setState(GameState.WAITING);
		RoleManager.getInstance().resetAll();
		ScoreboardManager.getInstance().resetAll();
	}
	
	public GameState getState() {return this.gameState;}
	public void setState(GameState state) {this.gameState = state;}
}

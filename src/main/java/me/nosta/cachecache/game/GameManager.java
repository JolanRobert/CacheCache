package me.nosta.cachecache.game;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.runnables.PrepareGameRunnable;
import me.nosta.cachecache.runnables.TwinRunnable;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.stream.Collectors;

public class GameManager {
	
	private static GameManager instance;
	private GameState gameState;

	private TwinRunnable twinRunnable;

	public static GameManager getInstance() {
		if (instance == null) instance = new GameManager();
		return instance;
	}
	
	public GameManager() {
		this.setState(GameState.WAITING);
	}

	public void prepareGame() {
		this.setState(GameState.STARTING);
		new PrepareGameRunnable().runTaskTimer(Main.instance,0,5);
	}

	public void startGame() {
		this.setState(GameState.PLAYING);

		List<PlayerRole> twins = RoleManager.getInstance().getPlayerRoles().stream().filter(p -> p.getRole() == RoleEnum.JUMEAU).collect(Collectors.toList());
		if (twins.size() == 2) twinRunnable = new TwinRunnable(twins.get(0).getPlayer(),twins.get(1).getPlayer());
	}
	
	public void endGame() {
		Bukkit.getScheduler().cancelTasks(Main.instance);
		this.setState(GameState.WAITING);
		RoleManager.getInstance().resetAll();
		ScoreboardManager.getInstance().resetAll();
	}
	
	public GameState getState() {return this.gameState;}
	public void setState(GameState state) {this.gameState = state;}
}

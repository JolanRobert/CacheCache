package me.nosta.cachecache.game;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.Role;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
	
	private static GameManager instance;
	
	private GameState gameState;
	private List<Role> roles = new ArrayList<>();
	private List<PlayerRole> playerRoles = new ArrayList<PlayerRole>();

	public static GameManager getInstance() {
		if (instance == null) instance = new GameManager();
		return instance;
	}
	
	public GameManager() {
		this.setState(GameState.WAITING);
	}
	
	public void assignRoles() {
		
	}
	
	public void giveRoleInfo() {
		for (PlayerRole pr : playerRoles) {
			pr.getPlayer().sendMessage(pr.getRole().getDescription());
		}
	}
	
	public void resetGame() {
		this.setState(GameState.WAITING);
		for (PlayerRole pr : playerRoles) {pr.resetPlayerRole();}
		Bukkit.getScheduler().cancelTasks(Main.instance);
	}
	
	public GameState getState() {return this.gameState;}
	public void setState(GameState state) {this.gameState = state;}
}

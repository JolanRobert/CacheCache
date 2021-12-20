package fr.cachecache.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.cachecache.PlayerRole;

public class GameManager {
	
	public static GameManager instance;
	
	private GameState gameState;
	
	private List<PlayerRole> playerRoles = new ArrayList<PlayerRole>();

	public void OnEnable() {
		instance = this;
		
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

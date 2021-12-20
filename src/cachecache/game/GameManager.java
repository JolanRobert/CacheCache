package cachecache.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import cachecache.PlayerRole;

public class GameManager {
	
	private static GameManager instance;
	
	private GameState gameState;
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

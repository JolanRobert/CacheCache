package cachecache;

import org.bukkit.entity.Player;

public class PlayerRole {

	private Player player;
	private Role role;
	
	public PlayerRole(Player player, Role role) {
		this.player = player;
		this.role = role;
	}
	
	public void resetPlayerRole() {
		this.role = null;		
	}
	
	public Player getPlayer() {return this.player;}
	public Role getRole() {return this.role;}

}

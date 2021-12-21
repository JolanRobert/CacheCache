package me.nosta.cachecache.elements;

import org.bukkit.entity.Player;

public class PlayerRole {

	private Player player;
	private Role role;

	public PlayerRole(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {return this.player;}
	public Role getRole() {return this.role;}
	public void setRole(Role role) {this.role = role;}

}

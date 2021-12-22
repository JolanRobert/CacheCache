package me.nosta.cachecache.elements;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerRole {

	private Player player;
	private RoleEnum role;

	private PlayerRole twin; //Jumeaux
	private RoleEnum cover; //Espion

	public PlayerRole(Player player) {
		this.player = player;
	}

	public void showRoleInfo() {
		String roleInfo = "";
		roleInfo += ChatColor.GOLD+"[CC] "+ChatColor.BLUE+"Vous êtes ";
		if (role == RoleEnum.JUMEAU) roleInfo += ""+ChatColor.GREEN+"Jumeau avec "+twin.getPlayer().getName()+" !";
		else if (role == RoleEnum.CHASSEUR) roleInfo += ""+ChatColor.RED+"Chasseur !";
		else if (role == RoleEnum.ESPION) roleInfo += ""+ChatColor.RED+"Espion ("+cover.getName()+") !";
		else roleInfo += ""+ChatColor.GREEN+role.getName()+" !";

		if (role == RoleEnum.CHASSEUR || role == RoleEnum.ESPION) roleInfo += ChatColor.BLUE+" Votre objectif est d'empêcher les survivants de s'enfuir de la ville.";
		else roleInfo += ChatColor.BLUE+" Votre objectif est de vous enfuir de la ville.";

		this.player.sendMessage(roleInfo);
	}
	
	public Player getPlayer() {return this.player;}
	public RoleEnum getRole() {return this.role;}
	public void setRole(RoleEnum role) {this.role = role;}

	public void setCover(RoleEnum cover) {this.cover = cover;}
	public void setTwin(PlayerRole twin) {this.twin = twin;}
}

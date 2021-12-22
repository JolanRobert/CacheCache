package me.nosta.cachecache.elements;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerRole {

	private Player player;
	private RoleEnum role;

	private PlayerRole twin; //Jumeaux
	private RoleEnum cover; //Espion
	private PlayerRole admirer; //Ange

	public PlayerRole(Player player) {
		this.player = player;
	}

	public void showRoleInfo() {
		String roleInfo = "";
		roleInfo += ChatColor.GOLD+"[Rôle] "+ChatColor.BLUE+"Vous êtes ";
		if (role == RoleEnum.CHASSEUR || role == RoleEnum.ESPION) roleInfo += ""+ChatColor.RED+role.getName()+" !";
		else roleInfo += ""+ChatColor.GREEN+role.getName()+" !";

		if (role == RoleEnum.CHASSEUR || role == RoleEnum.ESPION) roleInfo += ChatColor.BLUE+" Votre objectif est d'empêcher les survivants de s'enfuir de la ville.";
		else roleInfo += ChatColor.BLUE+" Votre objectif est de vous enfuir de la ville.";

		if (role == RoleEnum.ANGE) roleInfo += ChatColor.GOLD+"\n[Ange] "+ChatColor.BLUE+"Votre adorateur est "+ChatColor.GREEN+admirer.getPlayer().getName()+".";
		else if (role == RoleEnum.JUMEAU) roleInfo += ChatColor.GOLD+"\n[Jumeau] "+ChatColor.BLUE+"Votre Jumeau est "+ChatColor.GREEN+twin.getPlayer().getName()+".";
		else if (role == RoleEnum.ESPION) roleInfo += ChatColor.GOLD+"\n[Espion] "+ChatColor.BLUE+"Votre rôle de couverture est "+ChatColor.RED+cover.getName()+".";

		this.player.sendMessage(roleInfo);
	}
	
	public Player getPlayer() {return this.player;}
	public RoleEnum getRole() {return this.role;}
	public void setRole(RoleEnum role) {this.role = role;}

	public void setCover(RoleEnum cover) {this.cover = cover;}
	public void setTwin(PlayerRole twin) {this.twin = twin;}
	public void setAdmirer(PlayerRole admirer) {this.admirer = admirer;}
}

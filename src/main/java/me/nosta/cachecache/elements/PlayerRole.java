package me.nosta.cachecache.elements;

import me.nosta.cachecache.game.RoleManager;
import me.nosta.cachecache.game.ScoreboardManager;
import me.nosta.cachecache.utilities.ItemEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PlayerRole {

	private final Player player;
	private RoleEnum role;
	private TeamEnum team;

	private PlayerRole twin; //Jumeaux
	private RoleEnum cover; //Espion
	private PlayerRole admirer; //Ange

	private int powerUse; //for role that have limited usages of their power

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
		else if (role == RoleEnum.CHASSEUR) {
			PlayerRole espion = RoleManager.getInstance().getEspion();
			if (espion != null) roleInfo += ChatColor.GOLD+"\n[Chasseur] "+ChatColor.BLUE+"Votre Espion est "+ChatColor.RED+espion.getPlayer().getName()+".";
		}

		this.player.sendMessage(roleInfo);
	}

	public void giveRolePowers() {
		ArrayList<String> lore = new ArrayList<String>();

		RoleEnum myRole;
		if (role == RoleEnum.ESPION) myRole = cover;
		else myRole = role;

		switch (myRole) {
			case CHASSEUR:
				giveKnife();
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,0,false,false));
				break;
			case ASTRONAUTE:
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,2,false,false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,Integer.MAX_VALUE,0,false,false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE,Integer.MAX_VALUE,0,false,false));
				break;
			case CAPITAINE:
				powerUse = 1;
				ItemStack assembly = new ItemStack(Material.NETHER_STAR);
				ItemEditor.setDisplayName(assembly, ChatColor.YELLOW+"Ralliement");
				lore.add(ChatColor.BLUE+"(1 utilisation)");
				lore.add(ChatColor.BLUE+"Téléporte tous les survivants sur votre position");
				ItemEditor.setLore(assembly,lore);
				player.getInventory().addItem(assembly);
				break;
			case JUMEAU:
				/*ItemStack tracker = new ItemStack(Material.COMPASS);
				ItemEditor.setDisplayName(tracker, ChatColor.YELLOW+"Tracker "+twin.getPlayer().getName());
				player.getInventory().addItem(tracker);*/
				break;
			case NINJA:
				powerUse = 3;
				ItemStack camouflage = new ItemStack(Material.NETHER_STAR);
				ItemEditor.setDisplayName(camouflage, ChatColor.YELLOW+"Camouflage");
				lore.add(ChatColor.BLUE+"("+powerUse+" utilisations/60s de cooldown)");
				lore.add(ChatColor.BLUE+"Rend invisible et octroie un bonus de vitesse pendant 10s");
				ItemEditor.setLore(camouflage,lore);
				player.getInventory().addItem(camouflage);
				break;
			case REBELLE:
				powerUse = 3;
				ItemStack dagger = new ItemStack(Material.IRON_SWORD);
				ItemEditor.setUnbreakable(dagger);
				ItemEditor.setDisplayName(dagger, ChatColor.YELLOW+"Dague");
				lore.add(ChatColor.BLUE+"("+powerUse+" utilisations)");
				lore.add(ChatColor.BLUE+"Renvoie un chasseur à son spawn en le frappant");
				ItemEditor.setLore(dagger,lore);
				dagger.addEnchantment(Enchantment.DAMAGE_ALL,1);
				player.getInventory().addItem(dagger);
				break;
			case SNIPER:
				ItemStack bow = new ItemStack(Material.BOW);
				ItemEditor.setUnbreakable(bow);
				ItemEditor.setDisplayName(bow, ChatColor.YELLOW+"Fusil de précision");
				bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK,10);
				player.getInventory().addItem(bow);
				break;
			default:
				break;
		}
	}

	public void giveKnife() {
		ItemStack knife = new ItemStack(Material.GOLDEN_SWORD);
		ItemEditor.setUnbreakable(knife);
		ItemEditor.setDisplayName(knife, ChatColor.YELLOW+"Poignard");
		knife.addEnchantment(Enchantment.DAMAGE_ALL,1);
		player.getInventory().addItem(knife);
	}

	public void clearAll() {
		//player.getInventory().clear();
		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
	}
	
	public Player getPlayer() {return this.player;}
	public RoleEnum getRole() {return this.role;}
	public TeamEnum getTeam() {return team;}

	public void setRole(RoleEnum role) {this.role = role;}
	public void setTeam(TeamEnum team) {
		this.team = team;
		ScoreboardManager.getInstance().joinTeam(team,player);
	}

	public PlayerRole getTwin() {return this.twin;}

	public void setCover(RoleEnum cover) {this.cover = cover;}
	public void setTwin(PlayerRole twin) {this.twin = twin;}
	public void setAdmirer(PlayerRole admirer) {this.admirer = admirer;}


}

package me.nosta.cachecache.elements;

import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.enums.TeamEnum;
import me.nosta.cachecache.managers.RoleManager;
import me.nosta.cachecache.managers.ScoreboardManager;
import me.nosta.cachecache.utilities.ItemEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerRole {

	private final Player player;
	private RoleEnum role;
	private TeamEnum team;

	private PlayerRole twin; //Jumeaux
	private RoleEnum cover; //Espion
	private PlayerRole admirer; //Ange

	private int powerUse; //for role that have limited usages of their power
	private int cooldown; // for role that have cooldowns

	private boolean stun;
	private boolean invincible;
	private boolean respawnable;

	public PlayerRole(Player player) {
		this.player = player;
	}

	public void showRoleInfo() {
		String roleInfo = "";
		roleInfo += ChatColor.GOLD+"[Rôle] "+ChatColor.BLUE+"Vous êtes ";
		if (role == RoleEnum.CHASSEUR || role == RoleEnum.ESPION) roleInfo += ChatColor.RED+role.getName()+" !";
		else roleInfo += ChatColor.GREEN+role.getName()+" !";

		if (role == RoleEnum.CHASSEUR || role == RoleEnum.ESPION) roleInfo += ChatColor.BLUE+" Votre objectif est de tuer tous les Survivants. ";
		else roleInfo += ChatColor.BLUE+" Votre objectif est de vous enfuir de la ville. ";

		roleInfo += role.getDescription();

		if (role == RoleEnum.ESPION) roleInfo += ChatColor.DARK_RED+"\n(Espion) "+ChatColor.BLUE+"Votre rôle de couverture est "+ChatColor.RED+cover.getName()+".";
		if (role == RoleEnum.ANGE || cover == RoleEnum.ANGE && admirer != null) roleInfo += ChatColor.DARK_GREEN+"\n(Ange) "+ChatColor.BLUE+"Votre adorateur est "+ChatColor.GREEN+admirer.getPlayer().getName()+".";
		else if (role == RoleEnum.JUMEAU) roleInfo += ChatColor.DARK_GREEN+"\n(Jumeau) "+ChatColor.BLUE+"Votre Jumeau est "+ChatColor.GREEN+twin.getPlayer().getName()+".";
		else if (role == RoleEnum.CHASSEUR) {
			PlayerRole espion = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.ESPION);
			if (espion != null) roleInfo += ChatColor.DARK_RED+"\n(Chasseur) "+ChatColor.BLUE+"Votre Espion est "+ChatColor.RED+espion.getPlayer().getName()+".";
		}

		this.player.sendMessage(roleInfo);
	}

	public void giveRolePowers() {
		RoleEnum myRole;
		if (role == RoleEnum.ESPION) myRole = cover;
		else myRole = role;

		switch (myRole) {
			case CHASSEUR:
				giveHunterKnife();
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,0,false,false));
				break;
			case ANGE:
				if (admirer != null) {
					powerUse = 1;
					setInvincible(true);
				}
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
				ItemEditor.setLore(assembly,new ArrayList<>(Arrays.asList(ChatColor.BLUE+"(1 utilisation)",ChatColor.BLUE+"Téléporte tous les survivants sur votre position")));
				player.getInventory().addItem(assembly);
				break;
			case NINJA:
				powerUse = 3;
				ItemStack camouflage = new ItemStack(Material.NETHER_STAR);
				ItemEditor.setDisplayName(camouflage, ChatColor.YELLOW+"Camouflage");
				ItemEditor.setLore(camouflage, new ArrayList<>(Arrays.asList(ChatColor.BLUE+"("+powerUse+" utilisations/90s de cooldown)",ChatColor.BLUE+"Rend invisible et octroie un bonus de vitesse pendant 15s")));
				player.getInventory().addItem(camouflage);
				break;
			case PRETRE:
				powerUse = 1;
				break;
			case REBELLE:
				powerUse = 3;
				ItemStack dagger = new ItemStack(Material.IRON_SWORD);
				ItemEditor.setUnbreakable(dagger);
				ItemEditor.setDisplayName(dagger, ChatColor.YELLOW+"Dague");
				ItemEditor.setLore(dagger, new ArrayList<>(Arrays.asList(ChatColor.BLUE+"("+powerUse+" utilisations)",ChatColor.BLUE+"Renvoie un chasseur à son spawn en le frappant")));
				dagger.addEnchantment(Enchantment.DAMAGE_ALL,1);
				ItemEditor.hideEnchants(dagger);
				player.getInventory().addItem(dagger);
				break;
			case SNIPER:
				ItemStack bow = new ItemStack(Material.BOW);
				ItemEditor.setUnbreakable(bow);
				ItemEditor.setDisplayName(bow, ChatColor.YELLOW+"Fusil de précision");
				bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK,10);
				player.getInventory().addItem(bow);
				break;
			case VETERAN:
				powerUse = 2;
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,1,false,false));
			default:
				break;
		}
	}

	public void giveHunterKnife() {
		ItemStack knife = new ItemStack(Material.GOLDEN_SWORD);
		ItemEditor.setUnbreakable(knife);
		ItemEditor.setDisplayName(knife, ChatColor.YELLOW+"Poignard");
		knife.addEnchantment(Enchantment.DAMAGE_ALL,1);
		ItemEditor.hideEnchants(knife);
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
	public RoleEnum getCover() {return this.cover;}
	public PlayerRole getAdmirer() {return this.admirer;}

	public void setCover(RoleEnum cover) {this.cover = cover;}
	public void setTwin(PlayerRole twin) {this.twin = twin;}
	public void setAdmirer(PlayerRole admirer) {this.admirer = admirer;}

	public int getPowerUse() {return this.powerUse;}
	public void losePowerUse() {this.powerUse--;}
	public int getCooldown() {return this.cooldown;}
	public void setCooldown(int cooldown) {this.cooldown = cooldown;}

	public void setInvincible(boolean invincible) {this.invincible = invincible;}
	public boolean isInvincible() {return this.invincible;}
	public void setStun(boolean stun) {this.stun = stun;}
	public boolean isStunned() {return this.stun;}
	public void setRespawnable(boolean respawnable) {this.respawnable = respawnable;}
	public boolean isRespawnable() {return this.respawnable;}

}

package me.nosta.cachecache.runnables;

import me.nosta.cachecache.elements.Role;
import me.nosta.cachecache.game.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;

public class StartGameRunnable extends BukkitRunnable {

	private Random rdm = new Random();

	private float timer = 0;

	@Override
	public void run() {
		if (timer < 6) showTitle();
		else if (timer == 6) selectHunter();
		else if (timer == 8.5f) showRoleInfo();
		else if (timer == 10) this.cancel();
		timer += 0.5f;
	}

	public void showTitle() {
		Player rdmPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[rdm.nextInt(Bukkit.getOnlinePlayers().toArray().length)];
		for (Player p  : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED+rdmPlayer.getName(),null,0,1,0);
			p.playSound(new Location(Bukkit.getWorld("world"),0,0,0), Sound.UI_BUTTON_CLICK,1,1);
		}
	}

	public void selectHunter() {
		Player rdmPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[rdm.nextInt(Bukkit.getOnlinePlayers().toArray().length)];
		for (Player p  : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED+rdmPlayer.getName(),ChatColor.DARK_RED+"sera le chasseur !",0,1,0);
			p.playSound(new Location(Bukkit.getWorld("world"),0,0,0), Sound.ENTITY_ENDER_DRAGON_GROWL,1,1);
		}
		assignRoles(rdmPlayer);
	}

	public void assignRoles(Player hunter) {
		HashMap<Role,Integer> roles = RoleManager.getInstance().getRoles();

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == hunter) RoleManager.getInstance().assignRoles(p,Role.CHASSEUR);
			else {
				Role rdmRole = Role.values()[rdm.nextInt(Role.values().length)]; //Get Rdm Role
				RoleManager.getInstance().assignRoles(p,rdmRole);
				if (roles.get(rdmRole) == 1) roles.remove(rdmRole); // Handle Frere/Civil exception
				else roles.replace(rdmRole,roles.get(rdmRole)-1);
			}
		}
	}

	public void showRoleInfo() {
		RoleManager.getInstance().showRoleInfo();
		for (Player p  : Bukkit.getOnlinePlayers()) {
			p.playSound(new Location(Bukkit.getWorld("world"),0,0,0), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
		}
	}
}

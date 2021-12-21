package me.nosta.cachecache.runnables;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.Role;
import me.nosta.cachecache.game.RoleManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartGameRunnable extends BukkitRunnable {

	private Random rdm = new Random();

	private float timer = 0;

	@Override
	public void run() {
		if (timer < 4) showTitle();
		else if (timer == 6) selectHunter();
		else if (timer == 8) showRoleInfo();
		timer += 0.25f;
	}

	public void showTitle() {
		if (timer < 4 || timer < 6 && timer%0.5f == 0) {
			Player rdmPlayer = RoleManager.getInstance().getPlayerRoles().get(rdm.nextInt(RoleManager.getInstance().getPlayerRoles().size())).getPlayer();
			for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
				pr.getPlayer().sendTitle(ChatColor.RED+rdmPlayer.getName(),null,0,40,0);
				pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK,10000,1);
			}
		}
	}

	public void selectHunter() {
		Player rdmPlayer = RoleManager.getInstance().getPlayerRoles().get(rdm.nextInt(RoleManager.getInstance().getPlayerRoles().size())).getPlayer();
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.getPlayer().sendTitle(ChatColor.RED+rdmPlayer.getName(),ChatColor.DARK_RED+"sera le chasseur !",0,40,20);
			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL,10000,1);
			if (rdmPlayer == pr.getPlayer()) pr.setRole(Role.CHASSEUR);
		}
		assignRoles();
	}

	public void assignRoles() {
		List<Role> roles = new ArrayList<Role>(RoleManager.getInstance().getRoles());
		Role rdmRole;

		boolean frere = false;

		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			if (pr.getRole() != null) continue;
			if (roles.size() > 0) {
				if (!frere) {
					rdmRole = roles.get(rdm.nextInt(roles.size())); //Get Rdm Role
					if (rdmRole == Role.FRERE) {
						if (RoleManager.getInstance().getNbNullPlayerRoles() > 1) {
							pr.setRole(Role.FRERE);
							frere = true;
						}
						else roles.remove(rdmRole);
					}
					else pr.setRole(rdmRole);
				}
				else {
					rdmRole = Role.FRERE;
					frere = false;
				}

				roles.remove(rdmRole);
			}
			else {
				pr.setRole(Role.CIVIL);
			}
		}
	}

	public void showRoleInfo() {
		RoleManager.getInstance().showRoleInfo();
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,10000,1);
		}

		this.cancel();
	}
}

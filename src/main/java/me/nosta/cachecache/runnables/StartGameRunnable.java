package me.nosta.cachecache.runnables;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
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
		if (timer < 6) showTitle();
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
			if (rdmPlayer == pr.getPlayer()) pr.setRole(RoleEnum.CHASSEUR);
		}
		assignRoles();
	}

	public void assignRoles() {
		List<PlayerRole> playerRoles = RoleManager.getInstance().getPlayerRoles();
		List<RoleEnum> roles = new ArrayList<RoleEnum>(RoleManager.getInstance().getRoles());
		RoleEnum rdmRole = null;

		PlayerRole pRole;
		for (int i = 0; i < playerRoles.size(); i++) {
			pRole = playerRoles.get(i);
			if (pRole.getRole() != null) continue;

			if (playerRoles.size()-i == 1) roles.remove(RoleEnum.JUMEAU);

			if (roles.size() == 0) {
				pRole.setRole(RoleEnum.CIVIL);
				continue;
			}
			rdmRole = roles.get(rdm.nextInt(roles.size()));
			pRole.setRole(rdmRole);
			roles.remove(rdmRole);
		}

		//Jumeaux Handling
		PlayerRole twin;
		for (PlayerRole pr : playerRoles) {
			if (pr.getRole() == RoleEnum.JUMEAU) {
				twin = playerRoles.get(rdm.nextInt(playerRoles.size()));
				while (twin.getRole() == RoleEnum.CHASSEUR) twin = playerRoles.get(rdm.nextInt(playerRoles.size())); //make sure to not convert the hunter
				twin.setRole(RoleEnum.JUMEAU);
				twin.setTwin(pr);
				pr.setTwin(twin);
				break;
			}
		}

		//Espion Handling
		RoleEnum cover;
		for (PlayerRole pr : playerRoles) {
			if (pr.getRole() == RoleEnum.ESPION) {
				if (roles.size() == 0) cover = RoleEnum.CIVIL;
				else cover = roles.get(rdm.nextInt(roles.size()));
				pr.setCover(cover);
				break;
			}
		}
	}

	public void showRoleInfo() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.showRoleInfo();
			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,10000,1);
		}
		this.cancel();
	}
}

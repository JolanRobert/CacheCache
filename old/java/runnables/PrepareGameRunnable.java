package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.enums.TeamEnum;
import me.nosta.cachecache.managers.GameManager;
import me.nosta.cachecache.managers.RoleManager;
import me.nosta.cachecache.managers.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PrepareGameRunnable extends BukkitRunnable {

	private final Random rdm = new Random();
	private float timer = 0;

	public PrepareGameRunnable() {
		this.runTaskTimer(Main.getInstance(),0, 5);
	}

	@Override
	public void run() {
		if (timer < 6) showTitle();
		else if (timer == 6) assignRoles();
		else if (timer == 8) revealRoles();

		if (timer >= 8 && timer < 12) countdownSurvivors();
		else if (timer == 13) teleportSurvivors();

		if (timer >= 13 && timer < 22) countdownHunter();
		else if (timer == 23) teleportHunter();
		timer += 0.25f;
	}

	private void showTitle() {
		if (timer <= 2 || timer <= 4 && timer%0.5f == 0 || timer < 6 && timer%1 == 0) {
			Player rdmPlayer = RoleManager.getInstance().getPlayerRoles().get(rdm.nextInt(RoleManager.getInstance().getPlayerRoles().size())).getPlayer();
			for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
				pr.getPlayer().sendTitle(ChatColor.RED+rdmPlayer.getName(),null,0,40,0);
				pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK,Integer.MAX_VALUE,1);
			}
		}
	}

	private void assignRoles() {
		List<PlayerRole> playerRoles = RoleManager.getInstance().getPlayerRoles();
		List<RoleEnum> roles = RoleManager.getInstance().getRoles();

		chooseHunter(playerRoles);
		survivorsRoles(playerRoles,roles);
		jumeauHandling(playerRoles,roles);
		espionHandling(playerRoles,roles);
		angeHandling(playerRoles);
	}

	private void chooseHunter(List<PlayerRole> playerRoles) {
		PlayerRole hunter = playerRoles.get(rdm.nextInt(playerRoles.size()));
		hunter.setRole(RoleEnum.CHASSEUR);
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.getPlayer().sendTitle(ChatColor.RED+hunter.getPlayer().getName(),ChatColor.DARK_RED+"sera le chasseur !",0,40,20);
			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL,Integer.MAX_VALUE,1);
		}
	}

	private void survivorsRoles(List<PlayerRole> playerRoles, List<RoleEnum> roles) {
		RoleEnum rdmRole;
		for (PlayerRole pr : playerRoles) {
			if (roles.size() == 0) {
				pr.setRole(RoleEnum.CIVIL);
				continue;
			}

			rdmRole = roles.get(rdm.nextInt(roles.size()));
			pr.setRole(rdmRole);
			roles.remove(rdmRole);
		}
	}

	private void jumeauHandling(List<PlayerRole> playerRoles, List<RoleEnum> roles) {
		PlayerRole twin = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.JUMEAU);
		if (twin == null) return;

		List<PlayerRole> potentialTwins = playerRoles.stream().filter(p -> p.getRole() != RoleEnum.CHASSEUR && p.getRole() != RoleEnum.JUMEAU).collect(Collectors.toList());
		if (potentialTwins.size() == 0) twin.setRole(RoleEnum.CIVIL);
		else {
			PlayerRole newTwin = potentialTwins.get(rdm.nextInt(potentialTwins.size()));
			roles.add(newTwin.getRole());
			newTwin.setRole(RoleEnum.JUMEAU);
			twin.setTwin(newTwin);
			newTwin.setTwin(twin);
		}
	}

	private void espionHandling(List<PlayerRole> playerRoles, List<RoleEnum> roles) {
		PlayerRole spy = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.ESPION);
		if (spy == null) return;

		if (roles.size() == 0) spy.setCover(RoleEnum.CIVIL);
		else {
			RoleEnum cover = roles.get(rdm.nextInt(roles.size()));
			spy.setCover(cover);
		}
	}

	private void angeHandling(List<PlayerRole> playerRoles) {
		PlayerRole ange = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.ANGE);
		if (ange == null) return;

		List<PlayerRole> potentialAdmirers = playerRoles.stream().filter(p -> p.getRole() != RoleEnum.CHASSEUR
				&& p.getRole() != RoleEnum.ANGE && p.getCover() != RoleEnum.ANGE).collect(Collectors.toList());

		if (potentialAdmirers.size() == 0) ange.setAdmirer(null);
		else ange.setAdmirer(potentialAdmirers.get(rdm.nextInt(potentialAdmirers.size())));
	}

	private void revealRoles() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.showRoleInfo();

			if (pr.getRole() == RoleEnum.CHASSEUR) pr.setTeam(TeamEnum.CHASSEUR);
			else pr.setTeam(TeamEnum.SURVIVANT);

			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,Integer.MAX_VALUE,1);
		}
	}

	private void countdownSurvivors() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.getPlayer().sendMessage(ChatColor.GOLD+"[CC] "+ChatColor.BLUE+"Téléportation des Survivants dans "+(13-timer)+"s");
		}
	}

	private void teleportSurvivors() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			if (pr.getTeam() == TeamEnum.SURVIVANT) SpawnManager.getInstance().teleportPlayer(pr);
		}
	}

	private void countdownHunter() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.getPlayer().sendMessage(ChatColor.GOLD+"[CC] "+ChatColor.BLUE+"Téléportation du Chasseur dans "+(23-timer)+"s");
		}
	}

	private void teleportHunter() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.giveRolePowers();

			if (pr.getRole() == RoleEnum.CHASSEUR) SpawnManager.getInstance().teleportPlayer(pr);

			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_WOLF_GROWL,Integer.MAX_VALUE,1);
		}

		GameManager.getInstance().startGame();
		this.cancel();
	}
}

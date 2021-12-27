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
		else if (timer == 6) selectHunter();
		else if (timer == 8) {
			finalOperations();
			teleportSurvivors();
		}
		else if (timer == 13) teleportHunter();
		timer += 0.25f;
	}

	public void showTitle() {
		if (timer <= 2 || timer <= 4 && timer%0.5f == 0 || timer < 6 && timer%1 == 0) {
			Player rdmPlayer = RoleManager.getInstance().getPlayerRoles().get(rdm.nextInt(RoleManager.getInstance().getPlayerRoles().size())).getPlayer();
			for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
				pr.getPlayer().sendTitle(ChatColor.RED+rdmPlayer.getName(),null,0,40,0);
				pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK,Integer.MAX_VALUE,1);
			}
		}
	}

	public void selectHunter() {
		Player rdmPlayer = RoleManager.getInstance().getPlayerRoles().get(rdm.nextInt(RoleManager.getInstance().getPlayerRoles().size())).getPlayer();
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			pr.getPlayer().sendTitle(ChatColor.RED+rdmPlayer.getName(),ChatColor.DARK_RED+"sera le chasseur !",0,40,20);
			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL,Integer.MAX_VALUE,1);
			if (rdmPlayer == pr.getPlayer()) pr.setRole(RoleEnum.CHASSEUR);
		}
		assignRoles();
	}

	public void assignRoles() {
		//Remove Hunter
		List<PlayerRole> playerRoles = RoleManager.getInstance().getPlayerRoles().stream().filter(pr -> pr.getRole() == null).collect(Collectors.toList());
		List<RoleEnum> roles = new ArrayList<>(RoleManager.getInstance().getRoles());

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

		roles.removeAll(Collections.singleton(RoleEnum.JUMEAU)); //spy can't be twin

		//Twin Handling
		PlayerRole twin = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.JUMEAU);
		if (twin != null) {
			List<PlayerRole> survivors = playerRoles.stream().filter(p -> p.getRole() != RoleEnum.CHASSEUR && p.getRole() != RoleEnum.JUMEAU).collect(Collectors.toList());
			if (survivors.size() == 0) twin.setRole(RoleEnum.CIVIL);
			else {
				PlayerRole newTwin = survivors.get(rdm.nextInt(survivors.size()));
				roles.add(newTwin.getRole());
				newTwin.setRole(RoleEnum.JUMEAU);
				twin.setTwin(newTwin);
				newTwin.setTwin(twin);
			}
		}

		//Spy Handling + Ange Handling
		RoleEnum cover;
		List<PlayerRole> potentialAdmirers = playerRoles.stream().filter(p -> p.getRole() != RoleEnum.CHASSEUR && p.getRole() != RoleEnum.ANGE).collect(Collectors.toList());
		for (PlayerRole pr : playerRoles) {
			if (pr.getRole() == RoleEnum.ESPION) {
				if (roles.size() == 0) cover = RoleEnum.CIVIL;
				else cover = roles.get(rdm.nextInt(roles.size()));
				pr.setCover(cover);
			}
			else if (pr.getRole() == RoleEnum.ANGE) {
				if (potentialAdmirers.size() == 0) pr.setAdmirer(null);
				else pr.setAdmirer(potentialAdmirers.get(rdm.nextInt(potentialAdmirers.size())));
			}
		}
	}

	public void finalOperations() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			//Role Infos
			pr.showRoleInfo();

			//Teams
			if (pr.getRole() == RoleEnum.CHASSEUR) pr.setTeam(TeamEnum.CHASSEUR);
			else pr.setTeam(TeamEnum.SURVIVANT);

			//Items and Effects
			pr.giveRolePowers();

			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,Integer.MAX_VALUE,1);
		}

		GameManager.getInstance().startGame();
	}

	public void teleportSurvivors() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			if (pr.getTeam() == TeamEnum.SURVIVANT) SpawnManager.getInstance().teleportPlayer(pr);
		}
	}

	public void teleportHunter() {
		PlayerRole hunter = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.CHASSEUR);
		SpawnManager.getInstance().teleportPlayer(hunter);

		this.cancel();
	}
}

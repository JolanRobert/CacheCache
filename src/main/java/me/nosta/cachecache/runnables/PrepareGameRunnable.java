package me.nosta.cachecache.runnables;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.elements.TeamEnum;
import me.nosta.cachecache.game.GameManager;
import me.nosta.cachecache.game.RoleManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PrepareGameRunnable extends BukkitRunnable {

	private final Random rdm = new Random();

	private float timer = 0;

	@Override
	public void run() {
		if (timer < 6) showTitle();
		else if (timer == 6) selectHunter();
		else if (timer == 8) finalOperations();
		timer += 0.25f;
	}

	public void showTitle() {
		if (timer <= 2 || timer <= 4 && timer%0.5f == 0 || timer < 6 && timer%1 == 0) {
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
		List<PlayerRole> twins = playerRoles.stream().filter(p -> p.getRole() == RoleEnum.JUMEAU).collect(Collectors.toList());
		if (twins.size() == 1) {
			List<PlayerRole> survivors = playerRoles.stream().filter(p -> p.getRole() != RoleEnum.CHASSEUR && p.getRole() != RoleEnum.JUMEAU).collect(Collectors.toList());
			if (survivors.size() == 0) twins.get(0).setRole(RoleEnum.CIVIL);
			else {
				PlayerRole newTwin = survivors.get(rdm.nextInt(survivors.size()));
				roles.add(newTwin.getRole());
				newTwin.setRole(RoleEnum.JUMEAU);
				twins.get(0).setTwin(newTwin);
				newTwin.setTwin(twins.get(0));
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

	//Show role info
	//Add to team
	public void finalOperations() {
		for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
			//Role Infos
			pr.showRoleInfo();

			//Teams
			if (pr.getRole() == RoleEnum.CHASSEUR) pr.setTeam(TeamEnum.HUNTER);
			else pr.setTeam(TeamEnum.SURVIVOR);

			//Items and Effects
			pr.giveRolePowers();

			pr.getPlayer().playSound(pr.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,10000,1);
		}
		this.cancel();
		GameManager.getInstance().startGame();
	}
}
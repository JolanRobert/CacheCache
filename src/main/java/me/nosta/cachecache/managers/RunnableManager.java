package me.nosta.cachecache.managers;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.runnables.*;

public class RunnableManager {

    private static RunnableManager instance;

    public static RunnableManager getInstance() {
        if (instance == null) instance = new RunnableManager();
        return instance;
    }

    public void startGameRoleRunnables() {
        PlayerRole capitaine = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.CAPITAINE);
        launchCapitaineRunnable(capitaine);

        PlayerRole jumeau = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.JUMEAU);
        launchJumeauRunnable(jumeau);

        PlayerRole sniper = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.SNIPER);
        launchSniperRunnables(sniper);
    }

    public void launchCapitaineRunnable(PlayerRole capitaine) {
        if (capitaine != null) new CapitaineRunnable(capitaine);
    }

    public void launchJumeauRunnable(PlayerRole jumeau) {
        if (jumeau != null) new JumeauRunnable(jumeau,jumeau.getTwin());
    }

    public void launchNinjaRunnable(PlayerRole ninja) {
        if (ninja != null) new NinjaRunnable(ninja);
    }

    public void launchSniperRunnables(PlayerRole sniper) {
        if (sniper != null) new SniperRunnable(sniper);
    }
}

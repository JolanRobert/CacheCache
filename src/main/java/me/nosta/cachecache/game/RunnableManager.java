package me.nosta.cachecache.game;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.runnables.JumeauRunnable;
import me.nosta.cachecache.runnables.PrepareGameRunnable;
import me.nosta.cachecache.runnables.SniperRunnable;

import java.util.List;

public class RunnableManager {

    private static RunnableManager instance;

    public static RunnableManager getInstance() {
        if (instance == null) instance = new RunnableManager();
        return instance;
    }

    private PrepareGameRunnable prepareGameRunnable; //auto cancel
    private JumeauRunnable jumeauRunnable;
    private SniperRunnable sniperRunnable;

    public void launchRunnable(RunnableEnum runnableEnum) {
        switch (runnableEnum) {
            case PREPARE_GAME:
                prepareGameRunnable.runTaskTimer(Main.getInstance(),0,5);
                break;
            case JUMEAU:
                List<PlayerRole> twins = RoleManager.getInstance().getJumeaux();
                if (twins.size() == 2) jumeauRunnable.runTaskTimer(Main.getInstance(), 0, 1*20);
                break;
            case SNIPER:
                PlayerRole sniper = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.SNIPER);
                if (sniper != null) sniperRunnable.runTaskTimer(Main.getInstance(),0,60*20);
                break;
            default:
                break;
        }
    }

    public void stopRunnable(RunnableEnum runnableEnum) {
        switch (runnableEnum) {
            case JUMEAU:
                jumeauRunnable.cancel();
                break;
            default:
                break;
        }
    }
}

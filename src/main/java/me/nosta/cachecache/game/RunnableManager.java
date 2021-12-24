package me.nosta.cachecache.game;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.runnables.CapitaineRunnable;
import me.nosta.cachecache.runnables.JumeauRunnable;
import me.nosta.cachecache.runnables.PrepareGameRunnable;
import me.nosta.cachecache.runnables.SniperRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class RunnableManager {

    private static RunnableManager instance;

    public static RunnableManager getInstance() {
        if (instance == null) instance = new RunnableManager();
        return instance;
    }

    private PrepareGameRunnable prepareGameRunnable; //auto cancel
    private CapitaineRunnable capitaineRunnable;
    private JumeauRunnable jumeauRunnable;
    private SniperRunnable sniperRunnable;

    public void launchRunnable(RunnableEnum runnableEnum) {
        switch (runnableEnum) {
            case PREPARE_GAME:
                new PrepareGameRunnable();
                break;
            case CAPITAINE:
                PlayerRole capitaine = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.CAPITAINE);
                if (capitaine != null) capitaineRunnable = new CapitaineRunnable(capitaine);
                break;
            case JUMEAU:
                List<PlayerRole> twins = RoleManager.getInstance().getPlayerRoles().stream().filter(pr -> pr.getRole() == RoleEnum.JUMEAU).collect(Collectors.toList());
                if (twins.size() == 2) jumeauRunnable = new JumeauRunnable(twins.get(0),twins.get(1));
                break;
            case SNIPER:
                PlayerRole sniper = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.SNIPER);
                if (sniper != null) sniperRunnable = new SniperRunnable(sniper);
                break;
            default:
                break;
        }
    }

    public void stopRunnable(RunnableEnum runnableEnum) {
        switch (runnableEnum) {
            case CAPITAINE:
                capitaineRunnable.cancel();
                break;
            case JUMEAU:
                jumeauRunnable.cancel();
                break;
            case SNIPER:
                sniperRunnable.cancel();
                break;
            default:
                break;
        }
    }
}

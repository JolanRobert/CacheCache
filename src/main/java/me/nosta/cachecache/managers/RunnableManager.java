package me.nosta.cachecache.managers;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.enums.RunnableEnum;
import me.nosta.cachecache.runnables.*;

import java.util.List;
import java.util.stream.Collectors;

public class RunnableManager {

    private static RunnableManager instance;

    public static RunnableManager getInstance() {
        if (instance == null) instance = new RunnableManager();
        return instance;
    }

    private CapitaineRunnable capitaineRunnable;
    private JumeauRunnable jumeauRunnable;
    private SniperRunnable sniperRunnable;

    public void launchRunnable(RunnableEnum runnableEnum) {
        switch (runnableEnum) {
            case PREPARE_GAME:
                new PrepareGameRunnable();
                break;
            case INGAME:
                new IngameRunnable();
                break;
            case CAPITAINE:
                PlayerRole capitaine = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.CAPITAINE);
                if (capitaine != null) capitaineRunnable = new CapitaineRunnable(capitaine);
                break;
            case JUMEAU:
                List<PlayerRole> twins = RoleManager.getInstance().getPlayerRoles().stream().filter(pr -> pr.getRole() == RoleEnum.JUMEAU).collect(Collectors.toList());
                if (twins.size() == 2) jumeauRunnable = new JumeauRunnable(twins.get(0),twins.get(1));
                break;
            case NINJA:
                PlayerRole ninja = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.NINJA);
                if (ninja != null) new NinjaRunnable(ninja);
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

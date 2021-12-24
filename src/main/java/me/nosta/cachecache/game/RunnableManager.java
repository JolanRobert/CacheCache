package me.nosta.cachecache.game;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.runnables.PrepareGameRunnable;
import me.nosta.cachecache.runnables.TwinRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class RunnableManager {

    private static RunnableManager instance;

    public static RunnableManager getInstance() {
        if (instance == null) instance = new RunnableManager();
        return instance;
    }

    private PrepareGameRunnable prepareGameRunnable; //auto cancel
    private TwinRunnable twinRunnable;

    public void launchRunnable(RunnableEnum runnableEnum) {
        switch (runnableEnum) {
            case PREPARE_GAME:
                prepareGameRunnable.runTaskTimer(Main.getInstance(),0,5);
                break;
            case TWIN:
                List<PlayerRole> twins = RoleManager.getInstance().getPlayerRoles().stream().filter(p -> p.getRole() == RoleEnum.JUMEAU).collect(Collectors.toList());
                if (twins.size() == 2) twinRunnable.runTaskTimer(Main.getInstance(), 0, 20);
                break;
            default:
                break;
        }
    }

    public void stopRunnable(RunnableEnum runnableEnum) {
        switch (runnableEnum) {
            case TWIN:
                twinRunnable.cancel();
                break;
            default:
                break;
        }
    }
}

package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class CapitaineRunnable extends BukkitRunnable {

    PlayerRole capitaine;
    List<PlayerRole> hunters;

    public CapitaineRunnable(PlayerRole capitaine) {
        this.capitaine = capitaine;
        this.runTaskTimer(Main.getInstance(),0,1*20);
    }

    @Override
    public void run() {
        if (capitaine.getRole() != RoleEnum.CAPITAINE) this.cancel();

        hunters = RoleManager.getInstance().getPlayerRoles().stream().filter(pr -> pr.getRole() == RoleEnum.CHASSEUR).collect(Collectors.toList());
        for (PlayerRole pr : hunters) {
            if (capitaine.getPlayer().getLocation().distance(pr.getPlayer().getLocation()) <= 30) {
                pr.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,25,0,false,false));
            }
        }
    }
}

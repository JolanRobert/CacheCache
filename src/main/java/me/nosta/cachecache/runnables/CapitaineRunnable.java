package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.game.RoleManager;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class CapitaineRunnable extends BukkitRunnable {

    Player capitaine;
    List<PlayerRole> hunters;

    public CapitaineRunnable(PlayerRole capitaine) {
        this.capitaine = capitaine.getPlayer();
        this.runTaskTimer(Main.getInstance(),0,1*20);
    }

    @Override
    public void run() {
        hunters = RoleManager.getInstance().getPlayerRoles().stream().filter(pr -> pr.getRole() == RoleEnum.CHASSEUR).collect(Collectors.toList());
        for (PlayerRole pr : hunters) {
            if (capitaine.getLocation().distance(pr.getPlayer().getLocation()) <= 30) {
                pr.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,0,1*20));
            }
        }
    }
}

package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TwinRunnable extends BukkitRunnable {

    Player twin1, twin2;

    public TwinRunnable(Player twin1, Player twin2) {
        this.twin1 = twin1;
        this.twin2 = twin2;
        this.runTaskTimer(Main.instance, 0, 20);
    }

    @Override
    public void run() {
        if (twin1.getLocation().distance(twin2.getLocation()) <= 20) {
            twin1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,2,1,false,false));
            twin2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,2,1,false,false));
        }

        twin1.setCompassTarget(twin2.getLocation());
        twin2.setCompassTarget(twin1.getLocation());
    }
}

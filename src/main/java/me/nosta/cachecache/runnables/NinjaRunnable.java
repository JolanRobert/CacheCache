package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class NinjaRunnable extends BukkitRunnable {

    PlayerRole ninja;

    private int cooldown;

    public NinjaRunnable(PlayerRole ninja) {
        this.ninja = ninja;
        cooldown = 90;
        this.runTaskTimer(Main.getInstance(),0,1*20);
    }

    @Override
    public void run() {
        int newCooldown = cooldown-1;
        ninja.setCooldown(newCooldown);
        if (cooldown == 0) {
            ninja.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Ninja) "+ChatColor.GREEN+"Camouflage rechargé !");
            ninja.getPlayer().playSound(ninja.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,Integer.MAX_VALUE,1);
            this.cancel();
        }
    }
}

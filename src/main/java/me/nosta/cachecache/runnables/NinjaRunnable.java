package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class NinjaRunnable extends BukkitRunnable {

    PlayerRole ninja;

    public NinjaRunnable(PlayerRole ninja) {
        this.ninja = ninja;
        ninja.setCooldown(90);
        this.runTaskTimer(Main.getInstance(),1*20,1*20);
    }

    @Override
    public void run() {
        if (ninja.getRole() != RoleEnum.NINJA) this.cancel();

        ninja.setCooldown(ninja.getCooldown()-1);
        if (ninja.getCooldown() == 0) {
            ninja.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Ninja) "+ChatColor.GREEN+"Camouflage rechargé !");
            ninja.getPlayer().playSound(ninja.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,Integer.MAX_VALUE,1);
            this.cancel();
        }
    }
}

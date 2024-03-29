package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SniperRunnable extends BukkitRunnable {

    PlayerRole sniper;

    public SniperRunnable(PlayerRole sniper) {
        this.sniper = sniper;
        this.runTaskTimer(Main.getInstance(),0,45*20);
    }

    @Override
    public void run() {
        if (sniper.getRole() != RoleEnum.SNIPER) this.cancel();

        sniper.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW));
        sniper.getPlayer().playSound(sniper.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP,Integer.MAX_VALUE,1);
    }
}

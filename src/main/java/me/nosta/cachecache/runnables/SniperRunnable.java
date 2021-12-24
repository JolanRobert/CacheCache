package me.nosta.cachecache.runnables;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SniperRunnable extends BukkitRunnable {

    Player sniper;

    public SniperRunnable(Player sniper) {
        this.sniper = sniper;
    }

    @Override
    public void run() {
        sniper.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}

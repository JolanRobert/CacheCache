package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.managers.DeathManager;
import me.nosta.cachecache.managers.PowerManager;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        PlayerRole pr = RoleManager.getInstance().getPlayerRoleWithPlayer(player);
        if (pr == null) return;

        RoleEnum role = pr.getRole() == RoleEnum.ESPION ? pr.getCover() : pr.getRole();

        if (role == RoleEnum.CAPITAINE) {
            if (DeathManager.getInstance().hasCorrectItem(pr.getPlayer(),Material.NETHER_STAR,"Ralliement")) {
                event.setCancelled(true);
                PowerManager.getInstance().triggerPowerCapitaine(pr);
            }
        }

        else if (role == RoleEnum.NINJA) {
            if (DeathManager.getInstance().hasCorrectItem(pr.getPlayer(),Material.NETHER_STAR,"Camouflage")) {
                event.setCancelled(true);
                PowerManager.getInstance().triggerPowerNinja(pr);
            }
        }
    }
}

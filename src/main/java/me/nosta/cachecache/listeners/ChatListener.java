package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.elements.TeamEnum;
import me.nosta.cachecache.game.GameManager;
import me.nosta.cachecache.game.GameState;
import me.nosta.cachecache.game.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (GameManager.getInstance().getState() == GameState.WAITING || GameManager.getInstance().getState() == GameState.STARTING) return;

        PlayerRole sender = RoleManager.getInstance().getPlayerRoleWithPlayer(event.getPlayer());
        String msg = event.getMessage();

        //Global chat
        if (msg.startsWith("!")) {
            if (sender.getTeam() == TeamEnum.CHASSEUR) Bukkit.broadcastMessage(ChatColor.RED+sender.getPlayer().getName()+": "+ChatColor.WHITE+msg.substring(1));
            else if (sender.getTeam() == TeamEnum.SURVIVANT) Bukkit.broadcastMessage(ChatColor.GREEN+sender.getPlayer().getName()+": "+ChatColor.WHITE+msg.substring(1));
        }

        else {
            if (sender.getTeam() == TeamEnum.SURVIVANT) {
                if (sender.getRole() == RoleEnum.ESPION && msg.startsWith("$")) hunterMsg(ChatColor.DARK_RED+"(Espion) "+ChatColor.RED+sender.getPlayer().getName()+": "+msg.substring(1));
                else survivorMsg(ChatColor.DARK_GREEN+"(Survivant) "+ChatColor.GREEN+sender.getPlayer().getName()+": "+msg);
            }
            else if (sender.getTeam() == TeamEnum.CHASSEUR) sender.getPlayer().sendMessage(ChatColor.RED+"Les chasseurs n'ont pas de chat privé.");
        }

        event.setCancelled(true);
    }

    public void hunterMsg(String msg) {
        for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
            if (pr.getTeam() == TeamEnum.CHASSEUR || pr.getRole() == RoleEnum.ESPION) pr.getPlayer().sendMessage(msg);
        }
    }

    public void survivorMsg(String msg) {
        for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
            if (pr.getTeam() == TeamEnum.SURVIVANT) pr.getPlayer().sendMessage(msg);
        }
    }
}

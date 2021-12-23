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

        PlayerRole sender = RoleManager.getInstance().getPlayerRole(event.getPlayer());
        String msg = event.getMessage();

        //Global chat
        if (msg.startsWith("!")) {
            if (sender.getTeam() == TeamEnum.HUNTER) Bukkit.broadcastMessage(ChatColor.RED+sender.getPlayer().getName()+ChatColor.WHITE+": "+msg.substring(1));
            else if (sender.getTeam() == TeamEnum.SURVIVOR) Bukkit.broadcastMessage(ChatColor.GREEN+sender.getPlayer().getName()+ChatColor.WHITE+": "+msg.substring(1));
        }

        else {
            if (sender.getTeam() == TeamEnum.SURVIVOR) {
                if (sender.getRole() == RoleEnum.ESPION && msg.startsWith("%")) hunterMsg(ChatColor.DARK_RED+"[Espion] "+ChatColor.RED+msg.substring(1));
                else survivorMsg(ChatColor.DARK_GREEN+"[Survivants] "+ChatColor.GREEN+sender.getPlayer().getName()+": "+msg);
            }
            else if (sender.getTeam() == TeamEnum.HUNTER) sender.getPlayer().sendMessage(ChatColor.DARK_RED+"[CC] "+ChatColor.RED+"Les chasseurs n'ont pas de chat privé.");
        }

        event.setCancelled(true);
    }

    public void hunterMsg(String msg) {
        for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
            if (pr.getTeam() == TeamEnum.HUNTER) pr.getPlayer().sendMessage(msg);
        }
    }

    public void survivorMsg(String msg) {
        for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
            if (pr.getTeam() == TeamEnum.SURVIVOR) pr.getPlayer().sendMessage(msg);
        }
    }
}

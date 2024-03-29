package me.nosta.cachecache.managers;

import me.nosta.cachecache.enums.TeamEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

    private static ScoreboardManager instance;

    public static ScoreboardManager getInstance() {
        if (instance == null) instance = new ScoreboardManager();
        return instance;
    }

    private Scoreboard bukkitSB;

    private Team hunter, survivor;

    public ScoreboardManager() {
        bukkitSB = Bukkit.getScoreboardManager().getMainScoreboard();
        createTeams();
    }

    public void createTeams() {
        if (bukkitSB.getTeam("Hunter") == null) {
            hunter = bukkitSB.registerNewTeam("Hunter");
            hunter.setColor(ChatColor.RED);
            setTeamOptions(hunter);
        }
        else hunter = bukkitSB.getTeam("Hunter");

        if (bukkitSB.getTeam("Survivor") == null) {
            survivor = bukkitSB.registerNewTeam("Survivor");
            survivor.setColor(ChatColor.GREEN);
            setTeamOptions(survivor);
        }
        else survivor = bukkitSB.getTeam("Survivor");

        //Remove everyone from teams
        for (Team team : bukkitSB.getTeams()) {
            for (String player : team.getEntries()) {
                team.removeEntry(player);
            }
        }
    }

    public void setTeamOptions(Team team) {
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
    }

    public void joinTeam(TeamEnum team, Player player) {
        player.setScoreboard(bukkitSB);
        if (team == TeamEnum.CHASSEUR) {
            survivor.removeEntry(player.getName());
            hunter.addEntry(player.getName());
        }
        else if (team == TeamEnum.SURVIVANT) {
            hunter.removeEntry(player.getName());
            survivor.addEntry(player.getName());
        }
    }

    public void createQuests() {

    }

    public void resetAll() {
        createTeams();
    }
}

/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.visual.tab;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.visual.chat.GroupFX;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class VisualTeamManager {

    private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private final List<Team> teamList = new ArrayList<>();

    public VisualTeamManager() {
        for (GroupFX team : GroupFX.values()) {
            Team newTeam = scoreboard.registerNewTeam(team.getTeamName());
            teamList.add(newTeam);
            newTeam.prefix(Component.text().content(team.getBadge().toString() + " ").build());

            newTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            newTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        }

    }

    public Team getTeamOf(Player player) {
        GroupFX groupFX = GroupFX.getByName(Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player).getPrimaryGroup());
        for (Team team : teamList)
            if (team.getName().equalsIgnoreCase(groupFX.getTeamName()))
                return team;
        return getTeamOf(GroupFX.DEFAULT);
    }

    public Team getTeamOf(GroupFX groupFX) {
        for (Team team : teamList)
            if (team.getName().equalsIgnoreCase(groupFX.getTeamName()))
                return team;
        return null;
    }

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }
}

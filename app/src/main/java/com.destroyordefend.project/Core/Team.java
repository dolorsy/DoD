package com.destroyordefend.project.Core;

import java.util.ArrayList;
import java.util.List;

public class Team {
    List<Player> teamPlayers;

    Team() {
        teamPlayers = new ArrayList<>();
    }

    public List<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public void addPlayer(Player player) {
        teamPlayers.add(player);
    }
}

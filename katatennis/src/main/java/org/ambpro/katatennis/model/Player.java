package org.ambpro.katatennis.model;

public class Player {
    private final String playerName;
    private int playerScore;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void scorePoint() {
        playerScore++;
    }
}

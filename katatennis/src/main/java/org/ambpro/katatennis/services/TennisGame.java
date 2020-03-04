package org.ambpro.katatennis.services;

import java.util.stream.Stream;

import org.ambpro.katatennis.model.Player;
import org.ambpro.katatennis.utils.CommonConstants;

public class TennisGame {
    private Player player1;
    private Player player2;
    private String gameScore;

    public TennisGame() {
        this("Player 1", "Player 2");
    }

    public TennisGame(String player1Name, String player2Name) {
        player1 = new Player(player1Name);
        player2 = new Player(player2Name);
        gameScore = Score.LOVE + CommonConstants.HYPHEN + CommonConstants.SAME_GAME_SCORE;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getGameScore() {
        if (hasScoresEqual()) {
            gameScore = isPointGreaterOrEqualForty() ? CommonConstants.DEUCE_GAME_SCORE : Score.getScore(player1.getPlayerScore()) + CommonConstants.HYPHEN + CommonConstants.SAME_GAME_SCORE;
        } else if (isAnyPlayerCanWin()) {
            gameScore = isAnyPlayerLeadByOnePoint() ? getTopPlayerName() + CommonConstants.GAME_SCORE_ADVANTAGE : getTopPlayerName() + CommonConstants.GAME_SCORE_WINS;
        } else {
            gameScore = Score.getScore(player1.getPlayerScore()) + CommonConstants.HYPHEN + Score.getScore(player2.getPlayerScore());
        }
        return gameScore;
    }

    private boolean hasScoresEqual() {
        return player1.getPlayerScore() == player2.getPlayerScore();
    }

    private boolean isAnyPlayerCanWin() {
        return player1.getPlayerScore() > Score.FORTY.point || player2.getPlayerScore() > Score.FORTY.point;
    }

    private boolean isPointGreaterOrEqualForty() {
        return player1.getPlayerScore() >= Score.FORTY.point;
    }

    private String getTopPlayerName() {
        return player1.getPlayerScore() > player2.getPlayerScore() ? player1.getPlayerName() : player2.getPlayerName();
    }

    private boolean isAnyPlayerLeadByOnePoint() {
        return CommonConstants.ONE_POINT == Math.abs(player1.getPlayerScore() - player2.getPlayerScore());
    }

    private enum Score {
        LOVE(0, "Love"),
        FIFTEEN(1, "Fifteen"),
        THIRTY(2, "Thirty"),
        FORTY(3, "Forty");

        private final int point;
        private final String pointValue;

        Score(int point, String pointValue) {
            this.point = point;
            this.pointValue = pointValue;
        }

        private static String getScore(int point) {
            return Stream.of(Score.values()).filter(scoreValue -> scoreValue.point == point).findFirst().map(score -> score.pointValue).orElse("");
        }
    }
}

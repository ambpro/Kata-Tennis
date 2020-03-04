package org.ambpro.katatennis.tests;

import org.ambpro.katatennis.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.ambpro.katatennis.services.TennisGame;
import org.ambpro.katatennis.utils.CommonConstants;

public class TennisGameTest {

    TennisGame tennisGame;

    @BeforeEach
    public void newGameSetup() {
        tennisGame = new TennisGame();
    }

    @Test
    @DisplayName("Given a tennis game When tennis game starts Then the two players scores are at zero each")
    public void testNewGameStateShouldHaveTwoPlayerScoresAtZeroEach() {

        assertEquals(0, tennisGame.getPlayer1().getPlayerScore());
        assertEquals(0, tennisGame.getPlayer2().getPlayerScore());
    }

    @Test
    @DisplayName("Given a tennis game When tennis game starts Then there should be two players named Player 1 and Player 2")
    public void testNewGameStateShouldHaveTwoPlayerNamesPlayer1Player2() {

        assertEquals("Player 1", tennisGame.getPlayer1().getPlayerName());
        assertEquals("Player 2", tennisGame.getPlayer2().getPlayerName());
    }

    @Test
    @DisplayName("Given a tennis game started When Player1 scores a point Then the score of Player1 should increase by one point")
    public void testGameInProgressPlayer1ScoresShouldIncreaseScoreOfPlayer1() {

        scoreWinsByPlayer(tennisGame.getPlayer1(), 1);

        assertPointsScoredByPlayers(1, 0);
    }

    @Test
    @DisplayName("Given a tennis game started When Player1 scores two points Then the score of Player1 should increase by two points")
    public void testGameInProgressPlayer1ScoresTwoPointsShouldIncreaseScoreOfPlayer1ByTwoPoints() {

        scoreWinsByPlayer(tennisGame.getPlayer1(), 2);

        assertPointsScoredByPlayers(2, 0);
    }

    @Test
    @DisplayName("Given a tennis game When tennis game starts Then the game score is Love-All")
    public void testNewGameStateShouldHaveGameScoreLoveAll() {

        assertEquals("Love-All", tennisGame.getGameScore());
    }

    @Test
    @DisplayName("Given a tennis game started When Player1 scores a point Then the game score is Fifteen-Love")
    public void testGameInProgressPlayer1ScoresOnceShouldHaveGameScoreFifteenLove() {

        scoreWinsByPlayer(tennisGame.getPlayer1(), 1);

        assertEquals("Fifteen-Love", tennisGame.getGameScore());
    }

    @Test
    @DisplayName("Given a tennis game started When Player 1 scores a point and Player 2 scores a point Then the game score is Fifteen-All")
    public void testGameInProgressPlayer1ScoresOncePlayer2ScoresOnceShouldHaveGameScoreFifteenAll() {

        scoreWinsByPlayer(tennisGame.getPlayer1(), 1);
        scoreWinsByPlayer(tennisGame.getPlayer2(), 1);

        assertEquals("Fifteen-All", tennisGame.getGameScore());
    }

    @Test
    @DisplayName("Given a tennis game started When Player 1 scores no point and Player 2 scores a point Then the game score is Love-Fifteen")
    public void testGameInProgressPlayer2ScoresOnceShouldHaveGameScoreLoveFifteen() {

        scoreWinsByPlayer(tennisGame.getPlayer2(), 1);

        assertEquals("Love-Fifteen", tennisGame.getGameScore());
    }

    @ParameterizedTest
    @CsvSource({"0,Love", "1,Fifteen", "2,Thirty"})
    @DisplayName("Given a tennis game started When Player 1 and Player 2 scores same points Then the game score is followed by -All")
    public void testGameInProgressPlayer1AndPlayer2ScoreSameShouldHaveGameScoreAll(int wins, String scoreCall) {

        scoreWinsByPlayer(tennisGame.getPlayer1(), wins);
        scoreWinsByPlayer(tennisGame.getPlayer2(), wins);

        assertEquals(scoreCall + "-" + CommonConstants.SAME_GAME_SCORE, tennisGame.getGameScore());
    }

    @ParameterizedTest
    @CsvSource({"1,0,Fifteen-Love", "0,1,Love-Fifteen", "2,0,Thirty-Love", "2,1,Thirty-Fifteen", "0,2,Love-Thirty", "1,2,Fifteen-Thirty", "0,3,Love-Forty", "1,3,Fifteen-Forty", "2,3,Thirty-Forty", "3,0,Forty-Love", "3,1,Forty-Fifteen", "3,2,Forty-Thirty"})
    @DisplayName("Given a tennis game started When Player 1 and Player 2 score different points Then the game score contains the score of Player 1 followed by score of Player 2")
    public void testGameInProgressPlayer1AndPlayer2ScoreSameShouldHaveGameScoreAll(int player1Score, int player2Score, String scoreCall) {

        scoreWinsByPlayer(tennisGame.getPlayer1(), player1Score);
        scoreWinsByPlayer(tennisGame.getPlayer2(), player2Score);

        assertEquals(scoreCall, tennisGame.getGameScore());
    }

    @Test
    @DisplayName("Given a tennis game started When Player 1 and Player 2 score 3 points each Then the game score is Deuce")
    public void testGameInProgressPlayer1AndPlayer2ScoreThreePointsEachShouldHaveGameScoreDeuce() {

        scoreWinsByPlayer(tennisGame.getPlayer1(), 3);
        scoreWinsByPlayer(tennisGame.getPlayer2(), 3);

        assertEquals(CommonConstants.DEUCE_GAME_SCORE, tennisGame.getGameScore());
    }

    @Test
    @DisplayName("Given a tennis game started When Player 1 and Player 2 score at least 3 points and each score same points Then the game score is Deuce")
    public void testGameInProgressPlayer1AndPlayer2ScoreAtLeast3PointsAndScoreSamePointsShouldHaveGameScoreDeuce() {
        scoreWinsByPlayer(tennisGame.getPlayer1(), 5);
        scoreWinsByPlayer(tennisGame.getPlayer2(), 5);

        assertEquals(CommonConstants.DEUCE_GAME_SCORE, tennisGame.getGameScore());
    }

    @ParameterizedTest
    @CsvSource({"4,2,Player 1", "3,5,Player 2", "4,6,Player 2", "6,4,Player 1", "0,4,Player 2", "1,4,Player 2", "4,1,Player 1", "12,10,Player 1"})
    @DisplayName("Given a tennis game started When Any Player has scored at least 4 points and is ahead by two points Then the game score is Player Wins")
    public void testGameInProgressPlayerScoresAtLeast4PointsAndPlayerAheadByTwoPointsShouldHaveGameScorePlayerWins(int player1Score, int player2Score, String playerName) {

        scoreWinsByPlayer(tennisGame.getPlayer1(), player1Score);
        scoreWinsByPlayer(tennisGame.getPlayer2(), player2Score);

        assertEquals(playerName + " Wins", tennisGame.getGameScore());
    }

    @ParameterizedTest
    @CsvSource({"6,5,Player 1", "5,6,Player 2", "7,8,Player 2", "4,5,Player 2", "4,3,Player 1"})
    @DisplayName("Given a tennis game started When Player 1 and Player 2 score at least 3 points and is ahead of by 1 point Then the game score is Advantage Player")
    public void testGameInProgressPlayer1AndPlayer2ScoreAtLeast3PointsAndPlayerAheadBy1pointShouldHaveGameScoreAdvantagePlayer(int player1Score, int player2Score, String playerName) {

        scoreWinsByPlayer(tennisGame.getPlayer1(), player1Score);
        scoreWinsByPlayer(tennisGame.getPlayer2(), player2Score);

        assertEquals(playerName + " Advantage", tennisGame.getGameScore());
    }



    private void scoreWinsByPlayer(Player player, int totalWins) {
        for (int ball = 1; ball <= totalWins; ball++) {
            player.scorePoint();
        }
    }

    private void assertPointsScoredByPlayers(int player1Score, int player2Score) {
        assertEquals(player1Score, tennisGame.getPlayer1().getPlayerScore());
        assertEquals(player2Score, tennisGame.getPlayer2().getPlayerScore());
    }

}

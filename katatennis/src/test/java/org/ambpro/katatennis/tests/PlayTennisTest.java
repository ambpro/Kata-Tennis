package org.ambpro.katatennis.tests;

import org.ambpro.katatennis.App;
import org.ambpro.katatennis.services.PlayTennis;
import org.ambpro.katatennis.services.TennisGame;
import org.ambpro.katatennis.utils.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayTennisTest {

    ByteArrayOutputStream outputStream;
    PrintStream printStream;

    @BeforeEach
    public void newGameSetup() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    @ParameterizedTest
    @CsvSource(value = {"0-" + CommonConstants.WELCOME_MESSAGE, "1-" + CommonConstants.PROMPT_FOR_PLAYER1_NAME, "2-" + CommonConstants.PROMPT_FOR_PLAYER2_NAME, "3-" + CommonConstants.GAME_STARTS_NOW_MESSAGE, "4-" + CommonConstants.PLAYING_INSTRUCTIONS, "5-" + CommonConstants.GAME_OVER_MESSAGE}, delimiter = '-')
    public void testTennisApplicationLaunchedDisplayInstructions(int line, String consoleOutput) {
        inputLinesToConsole();
        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines(consoleOutput, line);
    }

    @Test
    @DisplayName("Given tennis application is launched When the prompt to enter Player one name is displayed and entered Then the entered player name is set as Player 1 name")
    public void testTennisApplicationLaunchedAfterPlayer1NamePromptShouldAssignEntryToFirstPlayerName() {

        inputLinesToConsole();
        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertEquals("Amin", tennisGame.getPlayer1().getPlayerName());
    }

    @Test
    @DisplayName("Given tennis application is launched When the prompt to enter Player two name is displayed and entered Then the entered player name is set as Player 2 name")
    public void testTennisApplicationLaunchedAfterPlayer2NamePromptShouldAssignEntryToSecondPlayerName() {

        inputLinesToConsole();
        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertEquals("Antoine", tennisGame.getPlayer2().getPlayerName());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and either player keys is pressed Then the player score increases")
    public void testTennisApplicationLaunchedAfterPlayingInstructionsPlayerKeysIsEnteredShouldIncreasePlayerScore(int wins) {

        String consoleInput = "Amin" + CommonConstants.NEW_LINE + "Antoine" + CommonConstants.NEW_LINE + generateStrings(CommonConstants.PLAYER1_INDICATOR, wins) + CommonConstants.NEW_LINE + generateStrings(CommonConstants.PLAYER2_INDICATOR, wins) + CommonConstants.NEW_LINE + CommonConstants.GAME_CANCEL_INDICATOR;
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertEquals(wins, tennisGame.getPlayer1().getPlayerScore());
        assertEquals(wins, tennisGame.getPlayer2().getPlayerScore());
    }

    @Test
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and either player keys is pressed Then the game score is displayed after every Round")
    public void testTennisApplicationLaunchedAfterPlayingInstructionsPlayerKeysIsEnteredShouldDisplayScore() {

        String consoleInput = "Amin" + CommonConstants.NEW_LINE + "Antoine" + CommonConstants.NEW_LINE + generateStrings(CommonConstants.PLAYER1_INDICATOR, 2) + CommonConstants.NEW_LINE + generateStrings(CommonConstants.PLAYER2_INDICATOR, 1) + CommonConstants.NEW_LINE + CommonConstants.GAME_CANCEL_INDICATOR;
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines("Please enter who won this Ball, Press [1]: Amin / [2]: Antoine Or Press [C] to stop playing", 4);
        assertConsoleLines("Fifteen-Love", 5);
        assertConsoleLines("Please enter who won this Ball, Press [1]: Amin / [2]: Antoine Or Press [C] to stop playing", 6);
        assertConsoleLines("Thirty-Love", 7);
        assertConsoleLines("Please enter who won this Ball, Press [1]: Amin / [2]: Antoine Or Press [C] to stop playing", 8);
        assertConsoleLines("Thirty-Fifteen", 9);
        assertConsoleLines("Please enter who won this Ball, Press [1]: Amin / [2]: Antoine Or Press [C] to stop playing", 10);
        assertConsoleLines("Game Over !!", 11);
    }

    @Test
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and either player Wins Then the Winner is announced and the program exits")
    public void testTennisApplicationLaunchedAfterPlayingInstructionsPlayer1WinsShouldDisplayScoreAndExit() {

        String consoleInput = "Amin" + CommonConstants.NEW_LINE + "Antoine" + CommonConstants.NEW_LINE + generateStrings(CommonConstants.PLAYER1_INDICATOR, 4);
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines("Amin Wins", 11);
        assertConsoleLines("Game Over !!", 12);
    }

    @Test
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and Next key is pressed Then the entered key is validated to be one of acceptable keys")
    public void testTennisApplicationLaunchedAfterPlayingInstructionsShouldValidateUserInputAndDisplayInvalidInputIfInputInvalid() {
        String consoleInput = "Amin" + CommonConstants.NEW_LINE + "Antoine" + CommonConstants.NEW_LINE + "A" + CommonConstants.NEW_LINE + CommonConstants.GAME_CANCEL_INDICATOR;
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines("Please enter a valid Input !!", 5);
    }

    @Test
    @DisplayName("Given a tennis application can be launched When the PlayTennis application is launched Then tennis application can be played")
    public void testTennisApplicationCanBeLaunched() {
        inputLinesToConsole();
        ByteArrayOutputStream console = new ByteArrayOutputStream();
        System.setOut(new PrintStream(console));

        App.main(new String[]{});

        String[] consoleLines = console.toString().split(CommonConstants.NEW_LINE);
        assertEquals(CommonConstants.WELCOME_MESSAGE, consoleLines[0]);
    }

    private void assertConsoleLines(String content, int lineNumber) {
        String console = new String(outputStream.toByteArray());
        String[] consoleLines = console.split(CommonConstants.NEW_LINE);
        assertEquals(content, consoleLines[lineNumber].trim());
    }

    private void inputLinesToConsole() {
        inputThisLineToConsole(CommonConstants.CONSOLE_INPUT_MESSAGES);
    }

    private void inputThisLineToConsole(String consoleInput) {
        System.setIn(new ByteArrayInputStream(consoleInput.getBytes()));
    }

    private String generateStrings(String key, int times) {
        return StringUtils.repeat(key, CommonConstants.NEW_LINE, times);
    }
}

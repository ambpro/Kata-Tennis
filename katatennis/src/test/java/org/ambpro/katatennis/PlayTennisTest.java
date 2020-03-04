package org.ambpro.katatennis;

import org.ambpro.katatennis.services.PlayTennis;
import org.ambpro.katatennis.services.TennisGame;
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

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String PLAYER1_INDICATOR = "1";
    private static final String PLAYER2_INDICATOR = "2";
    private static final String GAME_CANCEL_INDICATOR = "C";
    private static final String WELCOME_MESSAGE = "Welcome! Lets Play Tennis";
    private static final String PROMPT_FOR_PLAYER1_NAME = "Please enter Player One name: ";
    private static final String PROMPT_FOR_PLAYER2_NAME = "Please enter Player Two name: ";
    private static final String GAME_STARTS_NOW_MESSAGE = "Game Starts Now!!";
    private static final String GAME_OVER_MESSAGE = "Game Over !!";
    private static final String CONSOLE_INPUT_MESSAGES = "Rob" + NEW_LINE + "Bob" + NEW_LINE + "C";
    private static final String PLAYING_INSTRUCTIONS = "Please enter who won this Ball, Press [1]: Rob / [2]: Bob Or Press [C] to stop playing";
    ByteArrayOutputStream outputStream;
    PrintStream printStream;

    @BeforeEach
    public void newGameSetup() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    @ParameterizedTest
    @CsvSource(value = {"0-" + WELCOME_MESSAGE, "1-" + PROMPT_FOR_PLAYER1_NAME, "2-" + PROMPT_FOR_PLAYER2_NAME, "3-" + GAME_STARTS_NOW_MESSAGE, "4-" + PLAYING_INSTRUCTIONS, "5-" + GAME_OVER_MESSAGE}, delimiter = '-')
    public void test_TennisApplicationLaunched_DisplayInstructions(int line, String consoleOutput) {
        inputLinesToConsole();
        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines(consoleOutput, line);
    }

    @Test
    @DisplayName("Given tennis application is launched When the prompt to enter Player one name is displayed and entered Then the entered player name is set as Player 1 name")
    public void test_TennisApplicationLaunched_AfterPlayer1NamePrompt_ShouldAssignEntryToFirstPlayerName() {

        inputLinesToConsole();
        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertEquals("Rob", tennisGame.getPlayer1().getPlayerName());
    }

    @Test
    @DisplayName("Given tennis application is launched When the prompt to enter Player two name is displayed and entered Then the entered player name is set as Player 2 name")
    public void test_TennisApplicationLaunched_AfterPlayer2NamePrompt_ShouldAssignEntryToSecondPlayerName() {

        inputLinesToConsole();
        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertEquals("Bob", tennisGame.getPlayer2().getPlayerName());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and either player keys is pressed Then the player score increases")
    public void test_TennisApplicationLaunched_AfterPlayingInstructions_PlayerKeysIsEntered_ShouldIncreasePlayerScore(int wins) {

        String consoleInput = "Rob" + NEW_LINE + "Bob" + NEW_LINE + generateStrings(PLAYER1_INDICATOR, wins) + NEW_LINE + generateStrings(PLAYER2_INDICATOR, wins) + NEW_LINE + GAME_CANCEL_INDICATOR;
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertEquals(wins, tennisGame.getPlayer1().getPlayerScore());
        assertEquals(wins, tennisGame.getPlayer2().getPlayerScore());
    }

    @Test
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and either player keys is pressed Then the game score is displayed after every Round")
    public void test_TennisApplicationLaunched_AfterPlayingInstructions_PlayerKeysIsEntered_ShouldDisplayScore() {

        String consoleInput = "Rob" + NEW_LINE + "Bob" + NEW_LINE + generateStrings(PLAYER1_INDICATOR, 2) + NEW_LINE + generateStrings(PLAYER2_INDICATOR, 1) + NEW_LINE + GAME_CANCEL_INDICATOR;
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines("Please enter who won this Ball, Press [1]: Rob / [2]: Bob Or Press [C] to stop playing", 4);
        assertConsoleLines("Fifteen-Love", 5);
        assertConsoleLines("Please enter who won this Ball, Press [1]: Rob / [2]: Bob Or Press [C] to stop playing", 6);
        assertConsoleLines("Thirty-Love", 7);
        assertConsoleLines("Please enter who won this Ball, Press [1]: Rob / [2]: Bob Or Press [C] to stop playing", 8);
        assertConsoleLines("Thirty-Fifteen", 9);
        assertConsoleLines("Please enter who won this Ball, Press [1]: Rob / [2]: Bob Or Press [C] to stop playing", 10);
        assertConsoleLines("Game Over !!", 11);
    }

    @Test
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and either player Wins Then the Winner is announced and the program exits")
    public void test_TennisApplicationLaunched_AfterPlayingInstructions_Player1Wins_ShouldDisplayScoreAndExit() {

        String consoleInput = "Rob" + NEW_LINE + "Bob" + NEW_LINE + generateStrings(PLAYER1_INDICATOR, 4);
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines("Rob Wins", 11);
        assertConsoleLines("Game Over !!", 12);
    }

    @Test
    @DisplayName("Given tennis application is launched When the Playing instructions are displayed and Next key is pressed Then the entered key is validated to be one of acceptable keys")
    public void test_TennisApplicationLaunched_AfterPlayingInstructions_ShouldValidateUserInput_AndDisplayInvalidInputIfInputInvalid() {
        String consoleInput = "Rob" + NEW_LINE + "Bob" + NEW_LINE + "A" + NEW_LINE + GAME_CANCEL_INDICATOR;
        inputThisLineToConsole(consoleInput);

        TennisGame tennisGame = new PlayTennis().launch(printStream);

        assertConsoleLines("Please enter a valid Input !!", 5);
    }

    @Test
    @DisplayName("Given a tennis application can be launched When the PlayTennis application is launched Then tennis application can be played")
    public void test_TennisApplication_CanBeLaunched() {
        inputLinesToConsole();
        ByteArrayOutputStream console = new ByteArrayOutputStream();
        System.setOut(new PrintStream(console));

        App.main(new String[]{});

        String[] consoleLines = console.toString().split(NEW_LINE);
        assertEquals(WELCOME_MESSAGE, consoleLines[0]);
    }

    private void assertConsoleLines(String content, int lineNumber) {
        String console = new String(outputStream.toByteArray());
        String[] consoleLines = console.split(NEW_LINE);
        assertEquals(content, consoleLines[lineNumber].trim());
    }

    private void inputLinesToConsole() {
        inputThisLineToConsole(CONSOLE_INPUT_MESSAGES);
    }

    private void inputThisLineToConsole(String consoleInput) {
        System.setIn(new ByteArrayInputStream(consoleInput.getBytes()));
    }

    private String generateStrings(String key, int times) {
        return StringUtils.repeat(key, NEW_LINE, times);
    }
}

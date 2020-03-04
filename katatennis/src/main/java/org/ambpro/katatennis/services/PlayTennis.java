package org.ambpro.katatennis.services;

import java.io.PrintStream;
import java.util.Scanner;

public class PlayTennis {

    private static final String WELCOME_MESSAGE = "Welcome! Lets Play Tennis";
    private static final String PROMPT_FOR_PLAYER1_NAME = "Please enter Player One name: ";
    private static final String PROMPT_FOR_PLAYER2_NAME = "Please enter Player Two name: ";
    private static final String GAME_STARTS_NOW_MESSAGE = "Game Starts Now!!";
    private static final String PLAYER_1_INDICATOR = "1";
    private static final String PLAYER_2_INDICATOR = "2";
    private static final String GAME_CANCEL_INDICATOR = "C";
    private static final String PLAYING_INSTRUCTIONS_PART1 = "Please enter who won this Ball, Press [" + PLAYER_1_INDICATOR + "]: ";
    private static final String PLAYING_INSTRUCTIONS_PART2 = " / [" + PLAYER_2_INDICATOR + "]: ";
    private static final String PLAYING_INSTRUCTIONS_PART3 = " Or Press [" + GAME_CANCEL_INDICATOR + "] to stop playing";
    private static final String GAME_OVER_MESSAGE = "Game Over !!";
    private static final String INVALID_INPUT_MESSAGE = "Please enter a valid Input !!";
    private static final String WINS = "Wins";

    /*public static void main(String[] args) {
        new PlayTennis().launch(System.out);
    }*/
    
    public TennisGame launch(PrintStream out) {
        out.println(WELCOME_MESSAGE);
        out.println(PROMPT_FOR_PLAYER1_NAME);
        Scanner inputFromConsole = new Scanner(System.in);
        String player1Name = inputFromConsole.nextLine();
        out.println(PROMPT_FOR_PLAYER2_NAME);
        String player2Name = inputFromConsole.nextLine();
        out.println(GAME_STARTS_NOW_MESSAGE);
        TennisGame tennisGame = new TennisGame(player1Name, player2Name);
        do {
            out.println(PLAYING_INSTRUCTIONS_PART1 + player1Name + PLAYING_INSTRUCTIONS_PART2 + player2Name + PLAYING_INSTRUCTIONS_PART3);
            String input = inputFromConsole.nextLine().toUpperCase();
            if (PLAYER_1_INDICATOR.equals(input)) {
                tennisGame.getPlayer1().scorePoint();
                out.println(tennisGame.getGameScore());
            } else if (PLAYER_2_INDICATOR.equals(input)) {
                tennisGame.getPlayer2().scorePoint();
                out.println(tennisGame.getGameScore());
            } else if (GAME_CANCEL_INDICATOR.equals(input)) {
                break;
            } else {
                out.println(INVALID_INPUT_MESSAGE);
            }
        } while (!tennisGame.getGameScore().contains(WINS));
        out.println(GAME_OVER_MESSAGE);
        return tennisGame;
    }
}

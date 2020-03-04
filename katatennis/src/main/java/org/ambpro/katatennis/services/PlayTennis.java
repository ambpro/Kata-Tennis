package org.ambpro.katatennis.services;

import java.io.PrintStream;
import java.util.Scanner;

import org.ambpro.katatennis.utils.CommonConstants;

public class PlayTennis {
    
    public TennisGame launch(PrintStream out) {
        out.println(CommonConstants.WELCOME_MESSAGE);
        out.println(CommonConstants.PROMPT_FOR_PLAYER1_NAME);
        Scanner inputFromConsole = new Scanner(System.in);
        String player1Name = inputFromConsole.nextLine();
        out.println(CommonConstants.PROMPT_FOR_PLAYER2_NAME);
        String player2Name = inputFromConsole.nextLine();
        out.println(CommonConstants.GAME_STARTS_NOW_MESSAGE);
        TennisGame tennisGame = new TennisGame(player1Name, player2Name);
        do {
            out.println(CommonConstants.PLAYING_INSTRUCTIONS_PART1 + player1Name + CommonConstants.PLAYING_INSTRUCTIONS_PART2 + player2Name + CommonConstants.PLAYING_INSTRUCTIONS_PART3);
            String input = inputFromConsole.nextLine().toUpperCase();
            if (CommonConstants.PLAYER_1_INDICATOR.equals(input)) {
                tennisGame.getPlayer1().scorePoint();
                out.println(tennisGame.getGameScore());
            } else if (CommonConstants.PLAYER_2_INDICATOR.equals(input)) {
                tennisGame.getPlayer2().scorePoint();
                out.println(tennisGame.getGameScore());
            } else if (CommonConstants.GAME_CANCEL_INDICATOR.equals(input)) {
                break;
            } else {
                out.println(CommonConstants.INVALID_INPUT_MESSAGE);
            }
        } while (!tennisGame.getGameScore().contains(CommonConstants.WINS));
        out.println(CommonConstants.GAME_OVER_MESSAGE);
        return tennisGame;
    }
}

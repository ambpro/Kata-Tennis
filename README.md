# Kata-Tennis

## About the Kata Tennis

This Kata is about implementing a simple tennis game. The game is between two players similar to a Singles Tennis match.

The scoring system is rather simple:

1. A game is won by the first player to have won at least four points in total and at least two points more than the opponent.
2. The running score of each game is described in a manner peculiar to tennis: scores from zero to three points are described as “Love”, “Fifteen”, “Thirty”, and “Forty” respectively.
3. If at least three points have been scored by each player, and the scores are equal, the score is “deuce”.
4. If at least three points have been scored by each side and a player has one more point than his opponent, the score of the game is “Advantage” for the player in the lead.

## Prerequisite for this application

- JDK 1.8 
- Maven 3.x

## Set up Application

1. Clone the repository https://github.com/ambpro/Kata-Tennis.git
2. Add as maven project in your IDE
3. Select project sdk as JDK 1.8

## Run Test cases
### Run from command prompt or Terminal

1. Clone the project by following the setup instructions
2. Open the command prompt / terminal from project repository
3. Move to the project directory `cd katatennis`
3. Run `mvn clean test`

### Run from IDE

1. Clone the project by following the setup instructions
2. Run as Maven test

## Run Application
From console,

1. Move to the directory where you have cloned the project
2. Run `mvn clean install` to build the application
3. Launch the application using `mvn exec:java`
4. Follow the onscreen instructions and proceed with the game until a player is a winner
5. You can exit anytime by using 'C' or 'c' when prompted after starting the game or Terminate using abort command (Ctrl+C / Command+C)
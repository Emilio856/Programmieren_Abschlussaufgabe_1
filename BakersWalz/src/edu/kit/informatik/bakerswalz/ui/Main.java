package edu.kit.informatik.bakerswalz.ui;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.bakerswalz.game.GameBoard;
import edu.kit.informatik.bakerswalz.game.Player;

/**
 * Main class for the Bäkers Walz game.
 * 
 * Receives the information about the amount of players and structure of the field
 * through the args[]. 
 * 
 * Receives all input from the players and executes them on the respective class. The
 * syntax of the inputs is inspected and an error is printed when a player sends a
 * command that is not valid.
 * 
 * Prints the outputs for each action and shows error messages in case a command
 * was incorrect or an action couldn't be completed. 
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class Main {
    
    /**
     * Starts an error message.
     */
    public static final String ERROR = "Error, ";
    
    /**
     * Error message in case the commands wasn't found.
     */
    public static final String COMMAND_NOT_FOUND = "command not found!";
    
    /**
     * Error message in case the ingredient doesn't exist.
     */
    public static final String INGREDIENT_NON_EXISTENT = "the given ingredient does not exist!";
    
    /**
     * Error message in case the player does not exist!.
     */
    public static final String PLAYER_NON_EXISTENT = "the player does not exist!";
    
    /**
     * Error message in case the player is located on a starting field.
     */
    public static final String ON_STARTING_FIELD = "the player is located on a starting field!";
    
    /**
     * Error message in case the Player rolls an incorrect number.
     */
    public static final String WRONG_NUMBER = "the player rolled an incorrect number!";
    
    /**
     * Error message in case the player isn't allowed to roll the dice.
     */
    public static final String CANT_ROLL = "the player can't roll the dice!";
    
    /**
     * Error message in case the player can't harvest.
     */
    public static final String CANT_HARVEST = "the player can't harvest!";
    
    /**
     * Error message in case the player can't buy.
     */
    public static final String CANT_BUY = "the player can't buy!";
    
    /**
     * Error message in case a player doesn't have enough gold to buy an item.
     */
    public static final String NOT_ENOUGH_GOLD = "the player doesn't have enough gold!";
    
    /**
     * Error message in case the player can't prepare a certain recipe.
     */
    public static final String CANT_PREPARE = "the player can't prepare ";
    
    /**
     * Error message in case the player can't turn.
     */
    public static final String CANT_TURN = "the player can't currently turn!";
    
    /**
     * Error message in case the players number is incorrect.
     */
    public static final String WRONG_PLAYER = "not a valid player number";
    
    /**
     * Error message in case a player already won and a prohibited command is called.
     */
    public static final String PLAYER_WON = "a player has won and this command is therefore not allowed!";
    
    /**
     * Message in case a player wins the game.
     */
    public static final String PLAYER_WIN = " wins";
    
    /**
     * Pattern for the players number.
     */
    public static final String PLAYER_LETTER = "P";
    
    /**
     * Separates various outputs and the command line argument.
     */
    public static final String SEMICOLON_SEPARATOR = ";";
    
    /**
     * Separates various possible input commands.
     */
    public static final String INPUT_SEPARATOR = " ";
    
    /**
     * Exclamation mark as string.
     */
    public static final String EXCLAMATION_MARK = "!";
    
    /**
     * Lower bound for the dice.
     */
    public static final int DICE_LOWER_BOUND = 1;
    
    /**
     * Upper bound for the dice.
     */
    public static final int DICE_UPPER_BOUND = 6;
    
    /**
     * The string representation for the roll command.
     */
    private static final String ROLL = "roll";
    
    /**
     * The string representation for the harvest command.
     */
    private static final String HARVEST = "harvest";
    
    /**
     * The string representation for the buy command.
     */
    private static final String BUY = "buy";
    
    /**
     * The string representation for the prepare command.
     */
    private static final String PREPARE = "prepare";
    
    /**
     * The string representation for the can-prepare command.
     */
    private static final String CAN_PREPARE = "can-prepare?";
    
    /**
     * The string representation for the show-market command.
     */
    private static final String SHOW_MARKET = "show-market";
    
    /**
     * The string representation for the show-player command.
     */
    private static final String SHOW_PLAYER = "show-player";
    
    /**
     * The string representation for the turn command.
     */
    private static final String TURN = "turn";
    
    /**
     * The string representation for the quit command.
     */
    private static final String QUIT = "quit";
    
    /**
     * Error message in case the args[] input is not correct.
     */
    private static final String WRONG_ARGS = "incorrect command line arguments!";
    
    /**
     * Error message in case the game board is not valid.
     */
    private static final String WRONG_BOARD = "not a valid game board!";
    
    /**
     * Error message in case the commands syntax is not correct.
     */
    private static final String WRONG_SYNTAX = "the commands syntax is incorrect!";
    
    /**
     * The size of commands with length one.
     */
    private static final int LENGTH_ONE = 1;
    
    /**
     * The size of commands with length two. Also length of a
     * correct args[] input.
     */
    private static final int LENGTH_TWO = 2;
    
    /**
     * The index of the active player
     */
    private static int playerIndex;
    
    /**
     * The gameboard for the game.
     */
    private static GameBoard gameBoard;
    
    /**
     * The array containing the participating players.
     */
    private static Player[] players;
    
    /**
     * main method for the game. Receives input from the players and executes the commands.
     * @param args Contains the information about the amount of numbers and the board.
     */
    public static void main(final String[] args) {
        int playersNr;
        
        if (args.length == LENGTH_TWO) {
            gameBoard = new GameBoard(args[1]);
            
            if (args.length == LENGTH_TWO && validPlayer(args[0]) && gameBoard.validBoard(args[1])) {
                playersNr = Integer.parseInt(args[0]);
                players = new Player[playersNr];
                for (int i = 0; i < players.length; i++) {
                    players[i] = new Player();
                }
                Commands commands = new Commands(gameBoard, players);
                
                while (!commands.getQuit()) {
                    String in = Terminal.readLine();
                    matchedCommand(in, commands);
                }
            } else if (!validPlayer(args[0])) {
                Terminal.printLine(ERROR + WRONG_PLAYER);
                
            } else if (!gameBoard.validBoard(args[1])) {
                Terminal.printLine(ERROR + WRONG_BOARD);
            }
        } else {
            Terminal.printLine(ERROR + WRONG_ARGS);
        }
    }
    
    /**
     * Executes the commands for the game
     * @param command Information about the command to run.
     * @param commands Executes the commands.
     */
    private static void executeCommand(String command, Commands commands) {
        String[] input = command.split(Main.INPUT_SEPARATOR);
        
        if (input.length == LENGTH_ONE) {
            switch (input[0]) {
                case HARVEST:
                    Terminal.printLine(commands.harvest(players[playerIndex]));
                    commands.playerWon(players[playerIndex]);
                    break;
                case CAN_PREPARE:
                    canPrepare(commands.canPrepare(players[playerIndex]));
                    break;
                case SHOW_MARKET:
                    Terminal.printLine(commands.showMarket());
                    break;
                case TURN:
                    Terminal.printLine(commands.turn(players[playerIndex]));
                    playerTurn();
                    break;
                case QUIT:
                    commands.quit(true);
                    break;
                default:
                    Terminal.printLine(ERROR + COMMAND_NOT_FOUND);
                    break;
            }
        } else if (input.length == LENGTH_TWO && isNumber(input[1])) {
            switch (input[0]) {
                case ROLL:
                    Terminal.printLine(commands.roll(Integer.parseInt(input[1]), players[playerIndex]));
                    commands.playerWon(players[playerIndex]);
                    break;
                default:
                    Terminal.printLine(ERROR + COMMAND_NOT_FOUND);
                    break;
            }
        } else if (input.length == LENGTH_TWO && input[0].equals(SHOW_PLAYER)
                && isNumber(input[1].split(Main.PLAYER_LETTER)[1])) {
            Terminal.printLine(commands.showPlayer(Integer.parseInt(input[1].split(Main.PLAYER_LETTER)[1])));
        } else if (input.length == LENGTH_TWO) {
            switch (input[0]) {
                case BUY:
                    Terminal.printLine(commands.buy(players[playerIndex], input[1]));
                    break;
                case PREPARE:
                    Terminal.printLine(commands.prepare(players[playerIndex], input[1]));
                    commands.playerWon(players[playerIndex]);
                    break;
                default:
                    Terminal.printLine(ERROR + COMMAND_NOT_FOUND);
                    break;
            }
        } else {
            Terminal.printLine(ERROR + COMMAND_NOT_FOUND);
        }
    }
    
    /**
     * Checks if a string is an integer.
     * @param string The string to be analyzed.
     * @return True if the string can be converted into an integer.
     */
    private static boolean isNumber(String string) {
        if (string == null) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean validPlayer(String str) {
        int temp;
        if (isNumber(str)) {
            temp = Integer.parseInt(str);
            if (temp >= 2 && temp <= 4) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Sets the next player as the active player.
     * @return  The index of the new active player.
     */
    private static void playerTurn() {
        if (playerIndex + 1 >= players.length) {
            playerIndex = 0;
        } else {
            playerIndex++;
        }
    }
    
    /**
     * Checks if a String is empty or not and prints the String in
     * case it is not null.
     * @param str The String to be analyzed.
     */
    private static void canPrepare(String str) {
        if (!str.isEmpty()) {
            Terminal.printLine(str);
        }
    }
    
    /**
     * Uses regular expressions to check the syntax of commands and
     * returns true if the string has the structure of a valid command.
     * @param str The string to be analyzed.
     * @return True if str has a valid structure.
     */
    private static boolean hasMatched(String str) {
        String regex1 = "\\S*";
        String regex2 = "\\S*\\s\\d";
        String regex3 = "\\S*\\s\\S*";
        String regex4 = "\\S*\\s\\S*\\d";
        String[] splittedArr = str.split(INPUT_SEPARATOR);
        
        if (splittedArr.length == LENGTH_ONE && Pattern.matches(regex1, str)) {
            return true;
            
        } else if (splittedArr.length == LENGTH_TWO && splitPlayersNum(splittedArr[1])
                && Pattern.matches(regex4, str)) {
            
            return true;
            
        }  else if (splittedArr.length == LENGTH_TWO && splittedArr[1].length() == LENGTH_ONE
                && isNumber(splittedArr[1]) && Pattern.matches(regex2, str)) {
            return true;
            
        } else if (splittedArr.length == LENGTH_TWO && Pattern.matches(regex3, str)
                && !splittedArr[0].equals(SHOW_PLAYER)) {
            
            return true;
        }
        Terminal.printLine(ERROR + WRONG_SYNTAX);
        return false;
    }
    
    /**
     * Searches for the command in case the syntax has a correct structure.
     * @param in The string to be analyzed.
     * @param commands Contains all the commands that can be executed.
     */
    private static void matchedCommand(String in, Commands commands) {
        if (hasMatched(in)) {
            executeCommand(in, commands);
        }
    }
    
    /**
     * Checks that a parameter for player corresponds the required syntax "Px" being
     * x the players number.
     * @param str The string to analyze.
     * @return True if the syntax matches.
     */
    private static boolean splitPlayersNum(String str) {
        if (str.split("")[0].equals(PLAYER_LETTER) && isNumber(str.split("")[1])) {
            return true;
        }
        return false;
    }
}
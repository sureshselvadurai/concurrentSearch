package hotelapp.Helper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The Utils class provides various utility functions for the hotel application.
 */
public class Utils {

    /**
     * Gets user input from the command line.
     *
     * @return User Input
     */
    public static String getUserInput() {
        Scanner reader = new Scanner(System.in);
        String userInput = reader.nextLine();
        return userInput.strip();
    }

    /**
     * Checks if query parameters are met.
     *
     * @param userInput User Input
     * @return true if parameters are met, false otherwise
     */
    public static boolean checkFuncValidation(String userInput) {
        String functionName = userInput.split(Constants.BLANK)[0].toLowerCase();
        return functionName.equals(Constants.FIND) || functionName.equals(Constants.FIND_REVIEWS) || functionName.equals(Constants.FIND_WORD);
    }

    /**
     * Checks if required arguments are present in the userInput.
     *
     * @param userInput          String containing the arguments and their values
     * @param requiredArguments  Parameters to be checked in the input string
     * @param optionalArgument   Optional parameters to be checked
     * @return A HashMap containing the arguments and their values
     */
    public static HashMap<String, String> validateInput(String userInput, List<String> requiredArguments, List<String> optionalArgument) {
        HashMap<String, String> output = new HashMap<>();
        String[] userArgs = userInput.split(Constants.BLANK);

        if (!(userArgs.length % 2 == 0)) {
            throwError(Constants.ERR_400, Constants.ERR_400_MESSAGE);
        }

        // Stores key-value pairs of arguments and their values
        for (int i = 0; i < userArgs.length; i += 2) {
            String parameter = userArgs[i].substring(1).toLowerCase();
            if (requiredArguments.contains(parameter) || optionalArgument.contains(parameter)) {
                output.put(parameter, userArgs[i + 1]);
            }
            if (!userArgs[i].substring(0, 1).equals("-")) {
                throwError(Constants.ERR_400, Constants.ERR_400_MESSAGE);
            }
        }
        if (output.size() == 0) {
            throwError(Constants.ERR_400, Constants.ERR_400_MESSAGE);
        }
        if(!output.keySet().containsAll(requiredArguments)){
            throwError(Constants.ERR_400, Constants.ERR_400_5MESSAGE+requiredArguments);
        }
        return output;
    }

    /**
     * Prints the error message and exits the run.
     *
     * @param errorCode    Error code
     * @param errorMessage Error message
     */
    public static void throwError(String errorCode, String errorMessage) {
        System.err.println(errorCode);
        System.err.println(errorMessage);
        System.exit(0);
    }

    /**
     * Prints the error message and doesn't exit the run.
     *
     * @param errorCode    Error code
     * @param errorMessage Error message
     */
    public static void throwErrorNoClose(String errorCode, String errorMessage) {
        System.err.println(errorCode);
        System.err.println(errorMessage);
    }

    /**
     * Makes a header in the command line with input.
     *
     * @param hotelDesc Input
     */
    public static void headLineSetter(String hotelDesc) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_YELLOW = "\u001B[33m";
        System.out.println(ANSI_YELLOW + Constants.BLANK + hotelDesc + Constants.BLANK + ANSI_RESET);
    }

    /**
     * Writes text to a file.
     *
     * @param printData   The text to be written
     * @param outputPath  The path to the output file
     */
    public static void textToFile(String printData, String outputPath) {
        try (PrintWriter writer = new PrintWriter(outputPath)) {
            writer.write(printData);
        } catch (FileNotFoundException e) {
            Utils.throwError(Constants.ERR_404_MESSAGE, Constants.ERR_400_3MESSAGE);
        }
    }
}

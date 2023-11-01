package hotelapp.Processer;

import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class handles processing input arguments for the hotel application.
 */
public class ProcessInput {

    /**
     * Parses the input arguments and extracts relevant configuration options.
     * @param args The input arguments provided to the program.
     * @return A map containing the parsed input configuration options.
     */
    public static Map<String, String> getInputConfig(String[] args) {
        // Join the input arguments into a single string
        String userInput = String.join(Constants.BLANK, args);

        // Define the required and optional arguments
        List<String> requiredArgument = Arrays.asList(Constants.HOTELS);
        List<String> optionalArgument = Arrays.asList(Constants.THREADS, Constants.OUTPUT, Constants.REVIEWS);

        // Validate and extract input arguments
        Map<String, String> inputArgs = Utils.validateInput(userInput, requiredArgument, optionalArgument);

        return inputArgs;
    }
}

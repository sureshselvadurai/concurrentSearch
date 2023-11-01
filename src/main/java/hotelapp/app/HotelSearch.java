package hotelapp.app;

import hotelapp.Processer.*;
import java.util.*;

/**
 * The HotelSearch class represents the main entry point for the program.
 * It handles command line arguments and initiates data processing.
 */
public class HotelSearch {

    /**
     * The main entry point for the program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // Create an InputProcessor and DataProcessor to handle input arguments
        ProcessInput processInput = new ProcessInput();
        ProcessData processData = new ProcessData();

        // Read config - program arguments and Parse input arguments and Generate search
        Map<String, String> inputArgs = processInput.getInputConfig(args);
        processData.generateDataSearch(inputArgs);

    }
}

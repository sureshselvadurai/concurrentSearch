package hotelapp.Processer;

import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import hotelapp.Mapper.Data.Data;
import hotelapp.Mapper.Data.ReviewData;
import hotelapp.Mapper.ThreadSafe.ThreadSafeHotelData;
import java.util.HashMap;
import java.util.Map;

/**
 * This class processes input arguments to generate data for searching hotels and reviews.
 */
public class ProcessData {

    /**
     * Generates data for searching hotels and reviews based on input arguments.
     * @param inputArgs The input arguments provided to the program.
     */
    public void generateDataSearch(Map<String, String> inputArgs) {
        Map<String, Data> data = new HashMap<>();

        // Process hotels if requested
        if (inputArgs.containsKey(Constants.HOTELS)) {
            HotelMapper hotelMapper = new HotelMapper();
            ThreadSafeHotelData hotelData = hotelMapper.mapHotel(inputArgs);
            data.put(Constants.HOTEL_DATA, hotelData);
        }

        // Process reviews if requested
        if (inputArgs.containsKey(Constants.REVIEWS)) {
            ReviewMapper reviewMapper = new ReviewMapper();
            ReviewData reviewData = reviewMapper.mapReviewThread(inputArgs);
            data.put(Constants.REVIEW_DATA, reviewData);
        }

        // Print the data based on input arguments
        boolean search = printData(data, inputArgs);

//                Search on user Input
        if(search) {
            ProcessSearch processSearch = new ProcessSearch();
            processSearch.search(data);
        }
    }

    /**
     * Prints the data based on input arguments.
     *
     * @param data      The data to be printed.
     * @param inputArgs The input arguments provided to the program.
     */
    private boolean printData(Map<String, Data> data, Map<String, String> inputArgs) {
        ThreadSafeHotelData hotelData = null;
        ReviewData reviewData = null;
        String outputPath = null;

        if (inputArgs.containsKey(Constants.HOTELS)) {
            hotelData = (ThreadSafeHotelData) data.get(Constants.HOTEL_DATA);
        }
        if (inputArgs.containsKey(Constants.REVIEWS)) {
            reviewData = (ReviewData) data.get(Constants.REVIEW_DATA);
        }
        if (inputArgs.containsKey(Constants.OUTPUT)) {
            outputPath = inputArgs.get(Constants.OUTPUT);
        }
        if (hotelData == null) {
            Utils.throwError(Constants.ERR_400, Constants.ERR_400_MESSAGE);
        }

        // Print the data and save to file if outputPath is provided
        if(outputPath!=null) {
            String printData = hotelData.printReviews(reviewData);
            Utils.textToFile(printData, outputPath);
            return false;
        }
        return true;
    }
}

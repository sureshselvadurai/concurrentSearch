package hotelapp.Helper;

/**
 * The Constants class defines various constants used in the hotel application.
 */
public final class Constants {

    public static final String LINE_SEPARATOR = "\n";
    public static final String LAT = "lat";
    public static final String LONG = "lng";
    public static final String HOTELS = "hotels";
    public static final String REVIEWS = "reviews";
    public static final String THREADS = "threads";
    public static final String FIND = "find";
    public static final String FIND_REVIEWS = "findreviews";
    public static final String WORD_COUNT = "Word Count - ";
    public static final String REVIEWS_DESC = "Hotel Reviews";
    public static final String WORD_COUNT_HEADER = "Reviews containing word: ";
    public static final String HOTEL_DESC = "Hotel Search Description";
    public static final String FIND_WORD = "findword";
    public static final String OUTPUT = "output";
    public static final String BLANK = " ";
    public static final String ERR_400 = "Error 400";
    public static final String ERR_400_MESSAGE = "Invalid Input - Please input value in the required format";
    public static final String ERR_400_1MESSAGE = "HotelId not found";
    public static final String ERR_400_4MESSAGE = "Review data location not provided";
    public static final String ERR_400_5MESSAGE = "Required information not provided : ";
    public static final String ERR_400_2MESSAGE = "Word not found";
    public static final String ERR_404 = "Error 404";
    public static final String ERR_404_MESSAGE = "File/Directory not Found";
    public static final String JSON = ".json";
    public static final String LOOP_STOPPER = "q";
    public static final String NEXT_LINE = "\n";
    public static final String USER_INPUT_MESSAGE = "Please enter the action to perform in the below format" + Constants.NEXT_LINE +
            LINE_SEPARATOR + NEXT_LINE +
            "Function: Find hotelID " + NEXT_LINE +
            "Input Format: Find <hotelID>" + NEXT_LINE +
            "Description: Fetches the details of the hotel for the given HotelID" + NEXT_LINE +
            Constants.LINE_SEPARATOR + NEXT_LINE +
            "Function: findReviews hotelID " + NEXT_LINE +
            "Input Format: findReviews <hotelID>" + NEXT_LINE +
            "Description: Fetches the reviews of the hotel for the given HotelID {Sorted by date}" + NEXT_LINE +
            LINE_SEPARATOR + NEXT_LINE +
            "Function: FindWord word  " + NEXT_LINE +
            "Input Format: FindWord <word>" + NEXT_LINE +
            "Description: Fetches the reviews containing the input word {Sorted by Frequency}" + NEXT_LINE +
            Constants.LINE_SEPARATOR
            ;
    public static final String HOTEL_DATA = "hotelData";
    public static final String REVIEW_DATA = "reviewData";
    public static final String LINE_MAIN_BREAK = "\n" + "********************" + "\n";
    public static final String VALUE_ASSIGN = ": ";
    public static final String LINE_MID_BREAK = "--------------------";
    public static final String ERR_400_3MESSAGE = "Error creating data file";
    public static final String ERR_400_6MESSAGE = "No Files in review directory";
}

package hotelapp.Mapper.Data;

import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import hotelapp.Mapper.Objects.Hotel;
import java.util.*;

public class HotelData implements Data {

    private final Map<String, Hotel> hotelCollections;
    private Map<String, String> dataPrint;

    public HotelData() {
        hotelCollections = new TreeMap<>((o1, o2) -> -o1.compareTo(o2));
        dataPrint = new TreeMap<>(String::compareTo);
    }

    /**
     * Gets a hotel by its ID.
     *
     * @param hotelID Hotel ID
     * @return Hotel object
     */
    public Hotel getHotel(String hotelID) {
        Hotel hotel = hotelCollections.get(hotelID);
        return new Hotel(hotel);
    }

    /**
     * Finds a hotel by its ID.
     *
     * @param hotelID Hotel ID
     */
    public void findHotelFromId(String hotelID) {
        Hotel hotelDesc = getHotel(hotelID);
        Utils.headLineSetter(Constants.HOTEL_DESC);
        if (hotelDesc == null) {
            Utils.throwErrorNoClose(Constants.BLANK, Constants.ERR_400_1MESSAGE);
            return;
        }
        System.out.println(hotelDesc);
    }

    /**
     * Adds an array of hotels to the collection.
     *
     * @param listHotel An array of hotels
     */
    public void addHotels(Hotel[] listHotel) {
        for (Hotel hotel : listHotel) {
            hotelCollections.put(hotel.getHotelID(), hotel);
            dataPrint.put(hotel.getHotelID(), hotel.toPrintString());
        }
    }

    /**
     * Prints reviews for the hotels along with their details.
     *
     * @param reviewData The review data to include in the printout
     * @return A formatted string containing the hotel details and reviews
     */
    public String printReviews(ReviewData reviewData) {
        StringBuilder outPrintBuilder = new StringBuilder();

        if (reviewData == null) {
            return addOnlyHotels();
        }

        for (Map.Entry<String, String> entry : dataPrint.entrySet()) {
            String hotelId = entry.getKey();
            String value = entry.getValue();

            outPrintBuilder.append(Constants.LINE_MAIN_BREAK)
                    .append(value);

            if (reviewData.containsHotelId(hotelId)) {
                String reviews = reviewData.getPrintData(hotelId);
                outPrintBuilder.append(reviews);
            }
        }

        return outPrintBuilder.toString();
    }

    /**
     * @return String having data only about Hotel
     */
    private String addOnlyHotels() {
        StringBuilder outPrintBuilder = new StringBuilder();
        for (Map.Entry<String, String> hotelPrintMap : dataPrint.entrySet()) {
            outPrintBuilder.append(Constants.LINE_MAIN_BREAK)
                    .append(hotelPrintMap.getValue());
        }
        return outPrintBuilder.toString();
    }
}

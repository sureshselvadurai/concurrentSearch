package hotelapp.Mapper.Objects;

import com.google.gson.annotations.SerializedName;
import hotelapp.Helper.Constants;
import java.util.Map;

public final class Hotel {

    @SerializedName(value = "id")
    private final String hotelID;
    @SerializedName(value = "f")
    private final String hotelName;
    @SerializedName(value = "ll")
    private final Map<String, String> hotelCord;
    @SerializedName(value = "ad")
    private final String hotelAddress;

    @SerializedName(value = "pr")
    private final String hotelProvince;
    @SerializedName(value = "ci")
    private final String hotelCity;

    /**
     * Returns a formatted string representation of the hotel information suitable for printing.
     *
     * @return A formatted string representing the hotel
     */
    public String toPrintString() {
        return hotelName + Constants.VALUE_ASSIGN + hotelID + Constants.NEXT_LINE
                + hotelAddress + Constants.NEXT_LINE
                + hotelCity + ", " + hotelProvince + Constants.NEXT_LINE;
    }

    /**
     * Returns a detailed string representation of the hotel, including all available information.
     *
     * @return A detailed string representing the hotel
     */
    public String toString() {
        return Constants.LINE_SEPARATOR + '\n' +
                " hotelID = " + hotelID + '\n' +
                " hotelName = " + hotelName + '\n' +
                " hotelCity = " + hotelCity + '\n' +
                " hotelLatitude = " + hotelCord.get(Constants.LAT) + '\n' +
                " hotelLongitude = " + hotelCord.get(Constants.LONG) + '\n' +
                " hotelAddress = " + hotelAddress + '\n' +
                Constants.LINE_SEPARATOR;
    }

    /**
     * Constructs a new Hotel object with the specified information.
     *
     * @param hotelID       The hotel's unique ID
     * @param hotelName     The name of the hotel
     * @param hotelCord     The coordinates (latitude and longitude) of the hotel
     * @param hotelAddress  The address of the hotel
     * @param hotelCity     The city where the hotel is located
     * @param hotelProvince The province or region where the hotel is situated
     */
    public Hotel(String hotelID, String hotelName, Map<String, String> hotelCord, String hotelAddress, String hotelCity, String hotelProvince) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelCord = hotelCord;
        this.hotelAddress = hotelAddress;
        this.hotelCity = hotelCity;
        this.hotelProvince = hotelProvince;
    }

    /**
     * Constructs a new Hotel object by copying the information from an existing Hotel object.
     *
     * @param hotel The existing Hotel object to copy from
     */
    public Hotel(Hotel hotel) {
        this.hotelID = hotel.hotelID;
        this.hotelName = hotel.hotelName;
        this.hotelCord = hotel.hotelCord;
        this.hotelAddress = hotel.hotelAddress;
        this.hotelCity = hotel.hotelCity;
        this.hotelProvince = hotel.hotelProvince;
    }

    /**
     * Gets the unique ID of the hotel.
     *
     * @return The hotel's unique ID
     */
    public String getHotelID() {
        return hotelID;
    }
}

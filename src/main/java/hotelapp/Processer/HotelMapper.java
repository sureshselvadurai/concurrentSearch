package hotelapp.Processer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import hotelapp.Mapper.Objects.Hotel;
import hotelapp.Mapper.ThreadSafe.ThreadSafeHotelData;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class HotelMapper {

    /**
     * maphotel
     * Maps an input JSON file to hotel class
     *
     * @param inputArgs - user Input
     */
    public ThreadSafeHotelData mapHotel(Map<String, String> inputArgs) {
        String hotelPath = inputArgs.get(Constants.HOTELS);
        DirectoryTraversal directoryTraversal = new DirectoryTraversal();
        List<Path> jsonPath = directoryTraversal.getFilesPath(hotelPath, Constants.JSON);
        ThreadSafeHotelData hotelData = jsonToMapListHotel(jsonPath);
        return hotelData;
    }

    /**
     * jsonToMapListHotel - Converts List of JSON file to Hotel class
     *
     * @param jsonPath - List of paths of JON File
     * @return List of Hotel class
     */
    public ThreadSafeHotelData jsonToMapListHotel(List<Path> jsonPath) {

        ThreadSafeHotelData hotelData = new ThreadSafeHotelData();

        for (int i = 0; i < jsonPath.size(); i++) {
            String filePath = jsonPath.get(i).toString();
            Gson gson = new Gson();

            try (FileReader fr = new FileReader(filePath)) {
                com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
                JsonObject jo = (JsonObject) parser.parse(fr);
                JsonArray jsonArr = jo.getAsJsonArray("sr");

                Hotel[] ListHotel = gson.fromJson(jsonArr, Hotel[].class);
                hotelData.addHotels(ListHotel);
            } catch (IOException e) {
                Utils.throwError(Constants.ERR_404, Constants.ERR_404_MESSAGE);
            }
        }
        return hotelData;
    };
}

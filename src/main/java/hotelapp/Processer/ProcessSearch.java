package hotelapp.Processer;

import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import hotelapp.Mapper.Data.Data;
import hotelapp.Mapper.Data.HotelData;
import hotelapp.Mapper.Data.ReviewData;
import java.util.Map;

public class ProcessSearch {

    public void search(Map<String, Data> data) {
        ReviewData reviewData;
        HotelData hotelData;

        if(!(data.containsKey(Constants.HOTEL_DATA))){
            Utils.throwErrorNoClose(Constants.ERR_400,Constants.ERR_400_MESSAGE);
            return;
        }
        hotelData = (HotelData) data.get(Constants.HOTEL_DATA);
        if(data.containsKey(Constants.REVIEW_DATA)){
            reviewData = (ReviewData) data.get(Constants.REVIEW_DATA);
        }else{
            reviewData = null;
        }

        //        Initiates the loop for user queries
        String userInput = "INIT";
        while (!(userInput.toLowerCase() == Constants.LOOP_STOPPER)){

//          Take user arguments for queries
            Utils.headLineSetter(Constants.USER_INPUT_MESSAGE);
            userInput = Utils.getUserInput();
            String[] userInputList = userInput.split(Constants.BLANK);

//          Validate user input query
            if(userInputList.length !=2){
                Utils.throwErrorNoClose(Constants.ERR_400,Constants.ERR_400_MESSAGE);
                continue;
            } else if (!Utils.checkFuncValidation(userInput)) {
                Utils.throwErrorNoClose(Constants.ERR_400,Constants.ERR_400_MESSAGE);
                continue;
            }

//            Set Parameters
            String functionName = userInput.split(Constants.BLANK)[0].toLowerCase();
            String functionParam = userInput.split(Constants.BLANK)[1];

//            Process query to their respective function
            if(functionName.equals(Constants.FIND)){
                if(!functionParam.matches("\\d+")){
                    Utils.throwErrorNoClose(Constants.ERR_400,Constants.ERR_400_MESSAGE);
                    continue;
                }
                hotelData.findHotelFromId(functionParam);
            }
            if(functionName.equals(Constants.FIND_REVIEWS)){
                if(reviewData==null){
                    Utils.throwErrorNoClose(Constants.ERR_400,Constants.ERR_400_4MESSAGE);
                    continue;
                }
                if(!functionParam.matches("\\d+")){
                    Utils.throwErrorNoClose(Constants.ERR_400,Constants.ERR_400_MESSAGE);
                    continue;
                }
                reviewData.findReviewFromId(functionParam);
            }
            if(functionName.equals(Constants.FIND_WORD)){
                if(reviewData==null){
                    Utils.throwErrorNoClose(Constants.ERR_400,Constants.ERR_400_4MESSAGE);
                    continue;
                }
                reviewData.findReviewFromWord(functionParam);
            }
        }
    }
}

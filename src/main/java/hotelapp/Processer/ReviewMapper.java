package hotelapp.Processer;

import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import hotelapp.Mapper.Data.ReviewData;

import java.io.File;
import java.util.*;

public class ReviewMapper {

    /**
     * Maps JSON files to Review objects using multiple threads and returns a ReviewData instance.
     *
     * @param inputArgs A map of user command-line arguments, including the path to the JSON files and the number of threads to use.
     * @return ReviewData containing the mapped reviews.
     */
    public ReviewData mapReviewThread(Map<String, String> inputArgs) {

        String reviewPath = inputArgs.get(Constants.REVIEWS);
        String threadCount = inputArgs.get(Constants.THREADS);
        Integer threadCountInt = threadCount==null?1:Integer.valueOf(threadCount);

        File dir = new File(reviewPath);
        if(dir.listFiles()==null){Utils.throwError(Constants.ERR_404,Constants.ERR_404_MESSAGE);}
        ProcessReviewThread processReviewThread = new ProcessReviewThread(threadCountInt);
        ReviewData reviewData = processReviewThread.processFilesToReviewData(dir);
        processReviewThread.checkCloseThreads();
        return reviewData;
    }

}

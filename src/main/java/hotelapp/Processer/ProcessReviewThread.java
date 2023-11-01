package hotelapp.Processer;

import com.google.gson.*;
import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import hotelapp.Mapper.Data.ReviewData;
import hotelapp.Mapper.Objects.Review;
import hotelapp.Mapper.ThreadSafe.ThreadSafeReviewData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class ProcessReviewThread {

    private ExecutorService executorService ;
    private Phaser phaser;
    private ThreadSafeReviewData threadSafeReviewData;


    /**
     * Constructs a ReviewThreadProcessor with the specified number of threads.
     *
     * @param numThreads The number of threads to use for parallel processing.
     */
    public ProcessReviewThread(Integer numThreads) {
        this.executorService = Executors.newFixedThreadPool(numThreads);;
        this.phaser = new Phaser();
        this.threadSafeReviewData = new ThreadSafeReviewData();
    }

    /**
     * Waits for all threads to complete and then shuts down the executor service.
     */
    public void checkCloseThreads() {
        phaser.awaitAdvance(0);
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Error while closing Multithread "+e);
        }
    }

    /**
     * A nested class that implements Runnable to process a single JSON file containing reviews.
     */
     private final class ReviewFileProcessor implements Runnable {
        private final File file;
        private final Phaser phaser;
        private final ThreadSafeReviewData threadSafeReviewData;
        private Logger logger = LogManager.getLogger();

        public ReviewFileProcessor(File file, Phaser phaser, ThreadSafeReviewData threadSafeReviewData) {
            this.file = file;
            this.phaser = phaser;
            this.threadSafeReviewData = threadSafeReviewData;
        }

        @Override
        public void run() {
            try {
                JsonToReviewData(file, threadSafeReviewData);
            }
                finally {
                    phaser.arriveAndDeregister();
            }
        }

        // The implementation for processing a JSON file and mapping it to ReviewData.
         private void JsonToReviewData(File file,ThreadSafeReviewData threadSafeReviewData)  {

             String filePath = file.getPath().toString();
             Gson gson = new Gson();
//             logger.debug("Parsing file :  " + file);

             try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                 JsonParser parser = new JsonParser();
                 JsonObject jo = parser.parse(reader).getAsJsonObject();
                 JsonObject reviewDetails = jo.getAsJsonObject("reviewDetails");
                 JsonObject reviewCollection = reviewDetails.getAsJsonObject("reviewCollection");
                 JsonArray jsonArr = reviewCollection.getAsJsonArray("review");

                 if(jsonArr.size()!=0){
                     Review[] reviewList = gson.fromJson(jsonArr, Review[].class);
                     threadSafeReviewData.addReviews(reviewList);
                     String hotelId = reviewList[0].getHotelID();
                     threadSafeReviewData.addReviewWord(reviewList);
                     threadSafeReviewData.addPrint(reviewList,hotelId);
                 }
             } catch (IOException e) {
                 Utils.throwError(Constants.ERR_404, Constants.ERR_404_MESSAGE);
             }
         }
     }

    /**
     * Processes all the files within the specified directory and its subdirectories to map reviews and store them in ReviewData.
     *
     * @param dir The directory containing JSON files to be processed.
     * @return ReviewData containing the mapped reviews.
     */
    public ReviewData processFilesToReviewData(File dir)  {

        File[] files = dir.listFiles();

        if (files == null) {
            Utils.throwError(Constants.ERR_404, Constants.ERR_400_6MESSAGE);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                processFilesToReviewData(file);
            } else if (file.isFile() && file.getName().endsWith(".json")) {
                try {
                    executorService.submit(new ReviewFileProcessor(file, phaser, threadSafeReviewData));
                    phaser.register();
                }catch (Exception e){
                    System.out.println("Error in processing File - "+ file);
                }
            }
        }
        return new ReviewData(threadSafeReviewData);
    }


}

package hotelapp.Mapper.Data;

import hotelapp.Helper.Constants;
import hotelapp.Helper.Utils;
import hotelapp.Mapper.Objects.Review;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReviewData implements Data{

    private Map<String, List<Review>> reviewSearchCollection;
    private List<Review> reviewCollection;
    private Map<String, List<Map<Review, Integer>>> wordData;
    private Map<String, String> dataPrint;

    /**
     * Copy constructor to create a new instance based on an existing ReviewData.
     *
     * @param reviewData The existing ReviewData to copy from
     */
    public ReviewData(ReviewData reviewData) {
        this.reviewSearchCollection = reviewData.reviewSearchCollection;
        reviewCollection = reviewData.reviewCollection;
        wordData = reviewData.wordData;
        dataPrint = reviewData.dataPrint;
    }

    /**
     * Default constructor to create a new instance of ReviewData.
     */
    public ReviewData() {
        this.reviewSearchCollection = new HashMap<>();
        reviewCollection = new ArrayList<>();
        wordData = new HashMap<>();
        dataPrint = new HashMap<>();
    }

    /**
     * Sorts and aggregates the word occurrences in the provided reviews.
     *
     * @param reviews A list of word occurrences in reviews
     * @return A sorted list of word occurrences with aggregated counts
     */
    private List<Map<Review, Integer>> sortWordData(List<Map<Review, Integer>> reviews) {

        Map<Review, Integer> reviewCountMap = new HashMap<>();
        List<Map<Review, Integer>> out = new ArrayList<>();
        for (Map<Review, Integer> reviewMap : reviews) {
            for (Map.Entry<Review, Integer> entry : reviewMap.entrySet()) {
                Review review = entry.getKey();
                int count = entry.getValue();

                // If the review is already encountered, increment the count
                if (reviewCountMap.containsKey(review)) {
                    int updatedCount = reviewCountMap.get(review) + count;
                    reviewCountMap.put(review, updatedCount);
                } else {
                    reviewCountMap.put(review, count);
                }
            }
        }
        for (Map.Entry<Review, Integer> reviewMap : reviewCountMap.entrySet()) {
            out.add(new HashMap() {{
                put(reviewMap.getKey(),reviewMap.getValue() );
            }});
        }
        sortCountReviewWord(out);

        return out;
    }

    /**
     * @param countReviewWord - Sorts the list
     */
    // Custom sorting method for countReviewWord list
    private void sortCountReviewWord(List<Map<Review, Integer>> countReviewWord) {
        countReviewWord.sort((o1, o2) -> {
            Map.Entry<Review, Integer> entry1 = o1.entrySet().iterator().next();
            Map.Entry<Review, Integer> entry2 = o2.entrySet().iterator().next();
            int entry1Count = entry1.getValue();
            int entry2Count = entry2.getValue();
            int comparor;

            if (entry1Count != entry2Count) {
                comparor = -Integer.compare(entry1Count, entry2Count);
            } else {
                LocalDate date1Obj = LocalDate.parse(entry1.getKey().getDatePosted(), DateTimeFormatter.ISO_DATE_TIME);
                LocalDate date2Obj = LocalDate.parse(entry2.getKey().getDatePosted(), DateTimeFormatter.ISO_DATE_TIME);
                comparor = -date1Obj.compareTo(date2Obj);

                if (comparor == 0) {
                    comparor = entry1.getKey().getReviewID().compareTo(entry2.getKey().getReviewID());
                }
            }
            return comparor;
        });
    }

    /**
     * @param review - Check for usernickname and populate
     */
    private void addValidateReviewCollection(Review review){
        review.checkAnonymous();
        addReviewCollection(review);
    }

    /**
     * @param review - Add review Collection
     */
    protected void addReviewCollection(Review review) {
        reviewCollection.add(review);
    }

    /**
     * @param reviews
     * Add reviews
     */
//    The methods inside are thread safe
    public void addReviews(Review[] reviews) {
        List<Review> newReviews = new ArrayList<>();

        // Add words and collect new reviews
        for (Review review : reviews) {
            addValidateReviewCollection(review);
            newReviews.add(review);
        }
        // Sort the newly added reviews
        newReviews.sort(sortReviewMap);

        // Add reviews to the search collection
        for (Review reviewMapper : newReviews) {
            List<Review> value = getReviewSearchCollection(reviewMapper.getHotelID());
            value.add(reviewMapper);
            putReviewSearchCollection(reviewMapper.getHotelID(),value);
        }
    }

    /**
     * @param hotelID
     * @param value
     * Add review search collection
     */
    protected void putReviewSearchCollection(String hotelID, List<Review> value) {
        reviewSearchCollection.put(hotelID, value);
    }

    /**
     * @param hotelID
     * @return review search collection for hotelID
     */
    protected List<Review> getReviewSearchCollection(String hotelID) {
        return reviewSearchCollection.getOrDefault(hotelID, new ArrayList<>());
    }

    /**
     * Comparator for review map
     */
    private static Comparator<Review>sortReviewMap = new Comparator<>() {
        /**
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return Sorted 2 objects based on date
         */
        @Override
        public int compare(Review o1, Review o2) {

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDate date1Obj = LocalDate.parse(o1.getDatePosted(), formatter);
            LocalDate date2Obj = LocalDate.parse(o2.getDatePosted(), formatter);
            int comparor = -1*date1Obj.compareTo(date2Obj);
            if(comparor==0){
                comparor = o1.getReviewID().compareTo(o2.getReviewID());
            }
            return comparor;
        }
    };

    /**
     * Find reviews for a specific hotel based on its ID and print them.
     *
     * @param hotelID The ID of the hotel
     */
    public void findReviewFromId(String hotelID) {

        {
            String output = Constants.LINE_SEPARATOR ;
            List<Review> ReviewDesc = reviewSearchCollection.get(hotelID);
            if(ReviewDesc==null){
                Utils.throwErrorNoClose(Constants.BLANK,Constants.ERR_400_1MESSAGE);
                return;
            }
            sortReviews(ReviewDesc);
            Utils.headLineSetter(Constants.REVIEWS_DESC);
            for(Review i : ReviewDesc){
                output += i.toString();
                output += Constants.LINE_SEPARATOR + Constants.LINE_SEPARATOR;
            }
            System.out.println(output);
        }

    }

    /**
     * Find reviews containing a specific word and print them.
     *
     * @param functionParam The word to search for in reviews
     */
    public void findReviewFromWord(String functionParam) {
        String output = Constants.LINE_SEPARATOR ;
        output += Constants.WORD_COUNT_HEADER;
        output += functionParam;
        output += Constants.LINE_SEPARATOR ;

        List<Map<Review, Integer>> reviews = wordData.get(functionParam);
        if(reviews==null){
            Utils.throwErrorNoClose(Constants.BLANK,Constants.ERR_400_2MESSAGE);
            return;
        }
        List<Map<Review, Integer>> processedSearch = sortWordData(reviews);
        for(Map<Review, Integer> review : processedSearch){
            Map.Entry<Review, Integer> entry = review.entrySet().iterator().next();
            output += entry.getKey().toString()+Constants.NEXT_LINE;
            output+= Constants.WORD_COUNT+entry.getValue()+ Constants.NEXT_LINE;
        }
        System.out.println(output);
    }

    /**
     * Check if the ReviewData contains data for a specific hotel ID.
     *
     * @param key The hotel ID to check
     * @return True if data for the hotel ID is present, false otherwise
     */
    protected boolean containsHotelId(String key) {
        return dataPrint.containsKey(key);
    }

//    addPrintListHotelReview - method inside is thread protected
    /**
     * Add reviews to the data and prepare them for printing for a specific hotel.
     *
     * @param reviewList The array of reviews to be added
     * @param hotelID    The ID of the hotel to which the reviews belong
     */
    public void addPrint(Review[] reviewList, String hotelID) {
        Arrays.sort(reviewList, sortReviewMap);

        StringBuilder reviewPrintBuilder = new StringBuilder();
        for (Review review : reviewList) {
            reviewPrintBuilder.append(Constants.LINE_MID_BREAK)
                    .append(Constants.NEXT_LINE)
                    .append(review.toPrintString());
        }

        String reviewPrint = reviewPrintBuilder.toString();
        addPrintListHotelReview(hotelID, reviewPrint);
    }

    /**
     * Add a formatted review print for a specific hotel.
     *
     * @param hotelID     The ID of the hotel
     * @param reviewPrint The formatted review print
     */
    protected void addPrintListHotelReview(String hotelID, String reviewPrint) {
        dataPrint.put(hotelID, reviewPrint);
    }

    /**
     * Sort a list of reviews based on date and ID.
     *
     * @param reviewList The list of reviews to be sorted
     */
    private void sortReviews(List<Review> reviewList) {
        Collections.sort(reviewList,sortReviewMap);
    }

    /**
     * Get the formatted review print data for a specific hotel.
     *
     * @param key The hotel ID for which to retrieve the review print data
     * @return The formatted review print data
     */
    protected String getPrintData(String key) {
        return dataPrint.get(key);
    }

//    adding synchronized to make it thread safe
    /**
     * Add words from reviews to the wordData collection and count their occurrences.
     *
     * @param reviewList The array of reviews containing words to be added to wordData
     */
    protected void addReviewWord(Review[] reviewList) {
            for (Review review : reviewList) {
                String[] words = review.getReviewText().split("\\s+");
                for (String word : words) {
                    wordData.computeIfAbsent(word, k -> new ArrayList<>())
                            .add(Collections.singletonMap(review, 1));
                }
            }
    }
}

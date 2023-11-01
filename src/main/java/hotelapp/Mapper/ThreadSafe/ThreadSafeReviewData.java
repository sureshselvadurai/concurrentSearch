package hotelapp.Mapper.ThreadSafe;

import hotelapp.Mapper.Data.ReviewData;
import hotelapp.Mapper.Objects.Review;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The ThreadSafeReviewData class extends ReviewData and provides thread-safe access to review data.
 * It uses multiple read-write locks to manage concurrent access to the data.
 */
public final class ThreadSafeReviewData extends ReviewData {
    private final ReadWriteLock printListHotelReviewLock = new ReentrantReadWriteLock();
    private final ReadWriteLock reviewSearchCollectionLock = new ReentrantReadWriteLock();
    private final ReadWriteLock reviewCollectionLock = new ReentrantReadWriteLock();

    private final ReadWriteLock wordDataLock = new ReentrantReadWriteLock();

    /**
     * Constructs a new ThreadSafeReviewData object.
     */
    public ThreadSafeReviewData() {}

    /**
     * Retrieves a formatted string with reviews of a specific hotel by its ID in a thread-safe manner.
     *
     * @param key The unique ID of the hotel for which reviews are requested
     * @return A formatted string with hotel reviews
     */
    @Override
    public String getPrintData(String key) {
        try {
            printListHotelReviewLock.writeLock().lock();
            return super.getPrintData(key);
        } finally {
            printListHotelReviewLock.writeLock().unlock();
        }
    }

    /**
     * Checks if the review data contains reviews for a specific hotel in a thread-safe manner.
     *
     * @param key The unique ID of the hotel to check for reviews
     * @return true if the data contains reviews for the specified hotel; otherwise, false
     */
    @Override
    public boolean containsHotelId(String key) {
        try {
            printListHotelReviewLock.writeLock().lock();
            return super.containsHotelId(key);
        } finally {
            printListHotelReviewLock.writeLock().unlock();
        }
    }

    /**
     * Adds a formatted string of hotel reviews for a specific hotel in a thread-safe manner.
     *
     * @param hotelID     The unique ID of the hotel for which reviews are added
     * @param reviewPrint A formatted string containing the reviews
     */
    @Override
    public void addPrintListHotelReview(String hotelID, String reviewPrint) {
        try {
            printListHotelReviewLock.writeLock().lock();
            super.addPrintListHotelReview(hotelID, reviewPrint);
        } finally {
            printListHotelReviewLock.writeLock().unlock();
        }
    }

    /**
     * Adds a review to the review collection in a thread-safe manner.
     *
     * @param review The Review object to be added
     */
    @Override
    public void addReviewCollection(Review review) {
        try {
            reviewCollectionLock.writeLock().lock();
            super.addReviewCollection(review);
        } finally {
            reviewCollectionLock.writeLock().unlock();
        }
    }

    /**
     * Puts a list of reviews for a specific hotel in the search collection in a thread-safe manner.
     *
     * @param hotelID The unique ID of the hotel for which reviews are added
     * @param value   A list of Review objects
     */
    @Override
    public void putReviewSearchCollection(String hotelID, List<Review> value) {
        try {
            reviewSearchCollectionLock.writeLock().lock();
            super.putReviewSearchCollection(hotelID, value);
        } finally {
            reviewSearchCollectionLock.writeLock().unlock();
        }
    }

    /**
     * Retrieves a list of reviews for a specific hotel from the search collection in a thread-safe manner.
     *
     * @param hotelID The unique ID of the hotel for which reviews are requested
     * @return A list of Review objects
     */
    @Override
    public List<Review> getReviewSearchCollection(String hotelID) {
        try {
            reviewSearchCollectionLock.readLock().lock();
            return super.getReviewSearchCollection(hotelID);
        } finally {
            reviewSearchCollectionLock.readLock().unlock();
        }
    }

    @Override
    public void addReviewWord(Review[] reviewList) {
        try {
            wordDataLock.writeLock().lock();
            super.addReviewWord(reviewList);
        } finally {
            wordDataLock.writeLock().unlock();
        }

    }
}
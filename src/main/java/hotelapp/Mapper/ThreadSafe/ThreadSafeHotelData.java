package hotelapp.Mapper.ThreadSafe;

import hotelapp.Mapper.Data.HotelData;
import hotelapp.Mapper.Data.ReviewData;
import hotelapp.Mapper.Objects.Hotel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class ThreadSafeHotelData extends HotelData {

    private final ReentrantReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;
    private final ReentrantReadWriteLock readWriteLockPrint;

    public ThreadSafeHotelData() {
        super();
        this.readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
        this.readWriteLockPrint = new ReentrantReadWriteLock();
    }

    @Override
    public Hotel getHotel(String hotelID) {
        try {
            readLock.lock();
            return super.getHotel(hotelID);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void addHotels(Hotel[] listHotel) {
        try {
            writeLock.lock();
            super.addHotels(listHotel);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String printReviews(ReviewData reviewData) {
        try {
            readWriteLockPrint.readLock().lock();
            return super.printReviews(reviewData);
        } finally {
            readWriteLockPrint.readLock().unlock();
        }
    }
}
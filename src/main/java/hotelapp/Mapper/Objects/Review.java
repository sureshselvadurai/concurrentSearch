package hotelapp.Mapper.Objects;

import com.google.gson.annotations.SerializedName;
import hotelapp.Helper.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Review class represents a hotel review.
 */
public final class Review {

    @SerializedName(value = "hotelId")
    private final String hotelID;
    @SerializedName(value = "reviewId")
    private final String reviewID;
    @SerializedName(value = "ratingOverall")
    private final String ratingOverall;
    @SerializedName(value = "title")
    private final String reviewTitle;
    @SerializedName(value = "reviewText")
    private final String reviewText;
    @SerializedName(value = "userNickname")
    private String userNickName;
    @SerializedName(value = "reviewSubmissionTime")
    private final String datePosted;

    /**
     * Constructs a new Review object with the specified review details.
     *
     * @param hotelID      The ID of the hotel that the review is associated with
     * @param reviewID     The unique ID of the review
     * @param ratingOverall The overall rating given in the review
     * @param reviewTitle  The title of the review
     * @param reviewText   The text content of the review
     * @param userNickName The nickname of the user who submitted the review
     * @param datePosted   The submission date and time of the review
     */
    public Review(String hotelID, String reviewID, String ratingOverall, String reviewTitle, String reviewText, String userNickName, String datePosted) {
        this.hotelID = hotelID;
        this.reviewID = reviewID;
        this.ratingOverall = ratingOverall;
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.userNickName = userNickName;
        this.datePosted = datePosted;
    }

    /**
     * Returns a string representation of the review, including all available information.
     *
     * @return A string representing the review
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDate localDatePosted = LocalDate.parse(datePosted, formatter);

        return Constants.LINE_SEPARATOR + '\n' +
                " hotelID = " + hotelID + '\n' +
                " reviewID = " + reviewID + '\n' +
                " ratingOverall = " + ratingOverall + '\n' +
                " reviewTitle = " + reviewTitle + '\n' +
                " reviewText = " + reviewText + '\n' +
                " userNickName = " + userNickName + '\n' +
                " datePosted = " + localDatePosted + '\n' +
                Constants.LINE_SEPARATOR;
    }

    /**
     * Gets the ID of the hotel associated with the review.
     *
     * @return The hotel's unique ID
     */
    public String getHotelID() {
        return this.hotelID;
    }

    /**
     * Gets the submission date and time of the review.
     *
     * @return The date and time when the review was submitted
     */
    public String getDatePosted() {
        return datePosted;
    }

    /**
     * Gets the unique ID of the review.
     *
     * @return The unique ID of the review
     */
    public String getReviewID() {
        return reviewID;
    }

    /**
     * Gets the text content of the review.
     *
     * @return The text content of the review
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Checks if the user nickname is empty and replaces it with "Anonymous" if needed.
     */
    public void checkAnonymous() {
        this.userNickName = userNickName.isEmpty() ? "Anonymous" : userNickName;
    }

    /**
     * Returns a formatted string representation of the review suitable for printing.
     *
     * @return A formatted string representing the review
     */
    public String toPrintString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDate localDate = LocalDate.parse(datePosted, formatter);

        return "Review by " + userNickName + " on " + localDate + Constants.NEXT_LINE
                + "Rating: " + ratingOverall + Constants.NEXT_LINE
                + "ReviewId: " + reviewID + Constants.NEXT_LINE
                + reviewTitle + Constants.NEXT_LINE
                + reviewText + Constants.NEXT_LINE;
    }
}

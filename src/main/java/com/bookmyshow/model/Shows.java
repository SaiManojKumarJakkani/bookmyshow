package src.main.java.com.bookmyshow.model;
import java.time.LocalTime;
import java.time.LocalDate;

public class Shows {
    private String showID;
    private String movieID;
    private String hallID;
    private String nameOfShow;
    private String venueID;
    private int numOfTotalSeats;
    private int numOfSeatsBooked;
    private LocalDate showDate;
    private LocalTime timing;
    private String numOfSeatsBookedPerTier;
    private String pricingTiers;

    // Getter and setter for showID
    public String getNumOfSeatsBookedPerTier() {
        return numOfSeatsBookedPerTier;
    }

    public void setNumOfSeatsBookedPerTier(String numOfSeatsBookedPerTier) {
        this.numOfSeatsBookedPerTier = numOfSeatsBookedPerTier;
    }
    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }
    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    // Getter and setter for movieID
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    // Getter and setter for hallID
    public String getHallID() {
        return hallID;
    }

    public void setHallID(String hallID) {
        this.hallID = hallID;
    }

    // Getter and setter for nameOfShow
    public String getNameOfShow() {
        return nameOfShow;
    }

    public void setNameOfShow(String nameOfShow) {
        this.nameOfShow = nameOfShow;
    }

    // Getter and setter for venueID
    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    // Getter and setter for numOfTotalSeats
    public int getNumOfTotalSeats() {
        return numOfTotalSeats;
    }

    public void setNumOfTotalSeats(int numOfTotalSeats) {
        this.numOfTotalSeats = numOfTotalSeats;
    }

    // Getter and setter for numOfSeatsBooked
    public int getNumOfSeatsBooked() {
        return numOfSeatsBooked;
    }

    public void setNumOfSeatsBooked(int numOfSeatsBooked) {
        this.numOfSeatsBooked = numOfSeatsBooked;
    }

    // Getter and setter for timing
    public LocalTime getTiming() {
        return timing;
    }

    public void setTiming(LocalTime timing) {
        this.timing = timing;
    }

    // Getter and setter for tiers
    public String getPricingTiers() {
        return pricingTiers;
    }

    public void setPricingTiers(String pricingTiers) {
        this.pricingTiers = pricingTiers;
    }


    public Shows addShow(){

        return null;

    }
    @Override
    public String toString() {
        return "Show{" +
                "showID='" + showID + '\'' +
                ", movieID='" + movieID + '\'' +
                ", hallID='" + hallID + '\'' +
                ", nameOfShow='" + nameOfShow + '\'' +
                ", venueID='" + venueID + '\'' +
                ", numOfTotalSeats=" + numOfTotalSeats +
                ", numOfSeatsBooked=" + numOfSeatsBooked +
                ", showDate=" + showDate +
                ", timing=" + timing +
                ", pricingTiers='" + pricingTiers + '\'' +
                '}';
    }
}

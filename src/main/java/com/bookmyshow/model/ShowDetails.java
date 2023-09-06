package src.main.java.com.bookmyshow.model;
import java.time.LocalTime;
import java.time.LocalDate;

public class ShowDetails {
    private String showID;
    private String movieID;
    private String hallID;
    private String nameOfShow;
    private String venueID;
    private int numOfTotalSeats;
    private int numOfSeatsBooked;
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String numOfSeatsBookedPerTier;
    private String pricingTiers;

    // Getter and setter for showID
    public LocalTime getStartTime() {
        return startTime;
    }

    // Setter for startTime
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // Getter for endTime
    public LocalTime getEndTime() {
        return endTime;
    }

    // Setter for endTime
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
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


    // Getter and setter for tiers
    public String getPricingTiers() {
        return pricingTiers;
    }

    public void setPricingTiers(String pricingTiers) {
        this.pricingTiers = pricingTiers;
    }


    public ShowDetails addShow(ShowDetails show){

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
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", pricingTiers='" + pricingTiers + '\'' +
                '}';
    }
}

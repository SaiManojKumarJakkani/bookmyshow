package src.main.java.com.bookmyshow.model;

import src.main.java.com.bookmyshow.jdbc.MovieDAO;
import src.main.java.com.bookmyshow.jdbc.ShowDetailsDAO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class ShowDetails {
    private String showID;
    private String hallID;
    private String movieName;
    private String venueID;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private double silverPrice;
    private double goldPrice;
    private double platinumPrice;
    private ShowDetailsDAO showDetailsDAO;
    private MovieDAO movieDAO;
    private int numSilverBooked;
    private int numGoldBooked;
    private int numPlatinumBooked;

    public ShowDetails(){
        showDetailsDAO = new ShowDetailsDAO();
        movieDAO = new MovieDAO();
    }

    public ShowDetails( String showID, String hallID, String movieName,
                String venueID, LocalDate date, LocalTime startTime, LocalTime endTime, double silverPrice,
                double goldPrice, double platinumPrice) {
        showDetailsDAO = new ShowDetailsDAO();
        movieDAO = new MovieDAO();
        this.showID=showID;
        this.hallID = hallID;
        this.movieName = movieName;
        this.venueID = venueID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.silverPrice = silverPrice;
        this.goldPrice = goldPrice;
        this.platinumPrice = platinumPrice;
    }

    public String toString(){
        return "ShowID: "+showID+
                "\nvenueID: "+venueID+
                "\nhallID: "+hallID+
                "\nmovie Name: "+movieName+
                "\nshow Date: "+date+
                "\nstart Time: "+ startTime+
                "\nend Time: "+ endTime+
                "\nsilver price: " + silverPrice+
                "\ngold price: " + goldPrice+
                "\nplatinum price: "+ platinumPrice;
    }

    public int getAvailableSeatsForSilver(){
        return showDetailsDAO.getAvailableSeatsForSilver(this.showID);
    }
    public int getAvailableSeatsForGold(){
        return showDetailsDAO.getAvailableSeatsForGold(this.showID);
    }
    public int getAvailableSeatsForPlatinum(){
        return showDetailsDAO.getAvailableSeatsForPlatinum(this.showID);
    }
    public String getShowID() {
        return showID;
    }
    public String getMovieName() {
        return movieName;
    }
    public String getVenueID() {
        return venueID;
    }
    public String getHallID() {
        return hallID;
    }
    public String getPricingTiers() {
       return Double.toString(this.silverPrice) + ","
               + Double.toString(this.goldPrice) + ","
               + Double.toString(this.platinumPrice);
    }
    public Date getShowDate() {
        return Date.valueOf(date);
    }
    public Time getStartTime() {
        return Time.valueOf(startTime);
    }
    public Time getEndTime() {
        return Time.valueOf(endTime);
    }
    public void addShow(){
        showDetailsDAO.addShow(this);
    }
}

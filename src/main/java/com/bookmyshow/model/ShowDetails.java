package src.main.java.com.bookmyshow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import src.main.java.com.bookmyshow.jdbc.MovieDAO;
import src.main.java.com.bookmyshow.jdbc.ShowDetailsDAO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class ShowDetails {
    private int showID;
    private int hallID;
    private String movieName;
    private int venueID;
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
    public ShowDetails( int showID, int hallID, String movieName,
                int venueID, LocalDate date, LocalTime startTime, LocalTime endTime, double silverPrice,
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
    public void addShow(){
        showDetailsDAO.addShow(this);
    }
    public int getNumSilverBooked() {
        return numSilverBooked;
    }
    public int getNumGoldBooked() {
        return numGoldBooked;
    }
    public int getNumPlatinumBooked() {
        return numPlatinumBooked;
    }

    //setters
    public void setHallID(int hallID) {
        this.hallID = hallID;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public void setVenueID(int venueID) {
        this.venueID = venueID;
    }
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }
    public void setStartTime(String  startTime) {
        this.startTime = LocalTime.parse(startTime);
    }
    public void setEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
    }
    public void setSilverPrice(double silverPrice) {
        this.silverPrice = silverPrice;
    }
    public void setGoldPrice(double goldPrice) {
        this.goldPrice = goldPrice;
    }
    public void setPlatinumPrice(double platinumPrice) {
        this.platinumPrice = platinumPrice;
    }

    //getters
    public int getShowID(){
        return showID;
    }
    public String getMovieName() {
        return movieName;
    }
    public int getVenueID() {
        return venueID;
    }
    public int getHallID() {
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
    public int getAvailableSeatsForSilver(){
        return showDetailsDAO.getAvailableSeatsForSilver(this.showID);
    }
    public int getAvailableSeatsForGold(){
        return showDetailsDAO.getAvailableSeatsForGold(this.showID);
    }
    public int getAvailableSeatsForPlatinum(){
        return showDetailsDAO.getAvailableSeatsForPlatinum(this.showID);
    }


}

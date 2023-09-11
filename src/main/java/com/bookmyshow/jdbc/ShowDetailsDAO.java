package src.main.java.com.bookmyshow.jdbc;

import src.main.java.com.bookmyshow.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShowDetailsDAO {
    private Connection connection;
    private String tableName="Shows";
    public ShowDetailsDAO(){
        connection = DatabaseConnectionManager.getConnection();
    }

    public List<ShowDetails> getShowsInDateRange(LocalDate startDate, LocalDate endDate, String venueName){
        List<ShowDetails> shows = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Shows JOIN (SELECT venueID FROM Venue WHERE Venue.name=?) AS q WHERE Shows.venueID=q.venueID AND Shows.showDate BETWEEN ? AND ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,venueName);
            pstmt.setString(2, String.valueOf(startDate));
            pstmt.setString(3, String.valueOf(endDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pricingTiers = rs.getString("pricingTiers");
                String[] temp = pricingTiers.split(",");
                double silverPrice = Double.parseDouble(temp[0]);
                double goldPrice = Double.parseDouble(temp[1]);
                double platinumPrice = Double.parseDouble(temp[2]);
                ShowDetails sd=new ShowDetails(
                        rs.getInt("showID"),
                        rs.getInt("hallID"),
                        rs.getString("movieName"),
                        rs.getInt("venueID"),
                        rs.getDate("showDate").toLocalDate(),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        silverPrice,goldPrice,platinumPrice
                        );
                shows.add(sd);
            }
            pstmt.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return shows;
    }

    public List<ShowDetails> getShowsOnDate(LocalDate date, String venueName){
        return getShowsInDateRange(date,date,venueName);
    }

    public List<ShowDetails> getShowsForMovieInDateRange(String movieName,LocalDate startDate, LocalDate endDate){
        List<ShowDetails> shows = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " +tableName+ " WHERE movieName=? AND showDate BETWEEN ? AND ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,movieName);
            pstmt.setString(2, String.valueOf(startDate));
            pstmt.setString(3, String.valueOf(endDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pricingTiers = rs.getString("pricingTiers");
                String[] temp = pricingTiers.split(",");
                double silverPrice = Double.parseDouble(temp[0]);
                double goldPrice = Double.parseDouble(temp[1]);
                double platinumPrice = Double.parseDouble(temp[2]);
                ShowDetails sd=new ShowDetails(
                        rs.getInt("showID"),
                        rs.getInt("hallID"),
                        rs.getString("movieName"),
                        rs.getInt("venueID"),
                        rs.getDate("showDate").toLocalDate(),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        silverPrice,goldPrice,platinumPrice
                );
                shows.add(sd);
            }
            pstmt.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return shows;
    }
    public List<ShowDetails> getShowsForMovieOnDate(String movieName,LocalDate date) {
        return getShowsForMovieInDateRange(movieName,date,date);
    }

    public int getAvailableSeatsForSilver(int showID){
        int numSilverAvailable = 0;
        String sqlQuery = "SELECT numSeatsSilver-numSilverBooked AS diff FROM Shows JOIN Hall WHERE Shows.hallID=Hall.hallID AND showID=?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numSilverAvailable = Integer.parseInt(rs.getString("diff"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return numSilverAvailable;
    }

    public int getAvailableSeatsForGold(int showID){
        int numGoldAvailable = 0;
        String sqlQuery = "SELECT numSeatsGold-numGoldBooked AS diff FROM Shows JOIN Hall WHERE Shows.hallID=Hall.hallID AND showID=?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numGoldAvailable = Integer.parseInt(rs.getString("diff"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return numGoldAvailable;
    }

    public int getAvailableSeatsForPlatinum(int showID){
        int numPlatinumAvailable = 0;
        String sqlQuery = "SELECT numSeatsPlatinum-numPlatinumBooked AS diff FROM Shows JOIN Hall WHERE Shows.hallID=Hall.hallID AND showID=?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numPlatinumAvailable = Integer.parseInt(rs.getString("diff"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return numPlatinumAvailable;
    }

    public void incrementBookedSeats(int showID,int numSeatsBooked, String pricingTier){
        String sqlQuery = "UPDATE Shows SET num"+pricingTier.toLowerCase()+
                "Booked = num"+pricingTier.toLowerCase()+"Booked+? WHERE showID=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1,numSeatsBooked);
            pstmt.setInt(2,showID);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated "+rowsAffected+" in shows table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addShow(ShowDetails showDetails){
        String sqlQuery = "INSERT INTO " + tableName +" VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, showDetails.getShowID());
            pstmt.setString(2, showDetails.getMovieName());
            pstmt.setInt(3, showDetails.getVenueID());
            pstmt.setInt(4, showDetails.getHallID());
            pstmt.setString(5, showDetails.getPricingTiers());
            pstmt.setDate(6, showDetails.getShowDate());
            pstmt.setTime(7, showDetails.getStartTime());
            pstmt.setTime(8, showDetails.getEndTime());
            pstmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public int getRowsInShowsTable() {
        String sqlQuery = "SELECT COUNT(userID) FROM " + tableName;
        int rows = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            rs.next();
            rows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return rows;
    }

    public void removeShow(int showID){
        String sqlQuery = "DELETE FROM Shows WHERE showID=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: "+ rowsAffected);
        }catch (Exception e){
            e.getMessage();
        }
    }
}

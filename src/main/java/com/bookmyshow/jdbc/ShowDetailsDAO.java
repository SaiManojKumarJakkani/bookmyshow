package src.main.java.com.bookmyshow.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.main.java.com.bookmyshow.model.*;

public class ShowDetailsDAO {
    
    private Connection connection;
    private String tableName="ShowDetails";
    public ShowDetailsDAO(){
        connection=DatabaseConnectionManager.getConnection();
    }

    public void getShowDetails(String venueId){
        String sqlQuery = "SELECT * FROM " + tableName +" where venuelD = '"+venueId+"'";
        List<User> owners=new ArrayList<User>();
        try{
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sqlQuery);
        int columnCount = rs.getMetaData().getColumnCount();

        while (rs.next()) {
            User u=new Owner(rs.getString("userID"), rs.getString("password"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("emailID"),rs.getString("bankAccNo") );
            owners.add(u);
        }
        }
        catch(Exception e){
            System.out.println(e);
            
        }
    }
        
    public List<ShowDetails> getShowDetailsNextThreeDays(List<String> listOfVenueIDs ){
        List<ShowDetails> showList = new ArrayList<>();
        // Create the SQL query string with dynamic placeholders
        String sql = "SELECT * FROM "+tableName+" WHERE showDate BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 3 DAY) AND venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") ORDER BY showDate ASC, venueID ASC;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setString(i + 1, listOfVenueIDs.get(i));
            }


            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                    ShowDetails show = new ShowDetails();
                    show.setShowID(rs.getString("showID"));
                    show.setMovieID(rs.getString("movieID"));
                    show.setHallID(rs.getString("hallID"));
                    show.setNameOfShow(rs.getString("nameOfShow"));
                    show.setVenueID(rs.getString("venueID"));
                    show.setNumOfTotalSeats(rs.getInt("numOfTotalSeats"));
                    show.setNumOfSeatsBooked(rs.getInt("numOfSeatsBooked"));
                    show.setShowDate(rs.getDate("showDate").toLocalDate());
                    show.setStartTime(rs.getTime("startTime").toLocalTime());
                    show.setEndTime(rs.getTime("endTime").toLocalTime());
                    show.setPricingTiers(rs.getString("pricingTiers"));
                    showList.add(show);
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return showList;
    }

    public List<List<Object>> getShowDetailsPastSevenDays(List<String> listOfVenueIDs ){
        List<List<Object>> showList = new ArrayList<>();
        // Create the SQL query string with dynamic placeholders
        String sql = "SELECT *, "+
                "(CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 2), ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', -1) AS DECIMAL(10, 2))) AS calculatedResult"
                +" FROM "+tableName+" WHERE showDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE() AND venueID IN (";
            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                sql += "?"; // Add a placeholder
                if (i < listOfVenueIDs.size() - 1) {
                    sql += ", "; // Add a comma if not the last placeholder
                }
            }
            sql += ") ORDER BY showDate DESC, venueID ASC;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setString(i + 1, listOfVenueIDs.get(i));
            }
            System.out.println(preparedStatement.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                    ShowDetails show = new ShowDetails();
                    show.setShowID(rs.getString("showID"));
                    show.setMovieID(rs.getString("movieID"));
                    show.setHallID(rs.getString("hallID"));
                    show.setNameOfShow(rs.getString("nameOfShow"));
                    show.setVenueID(rs.getString("venueID"));
                    show.setNumOfTotalSeats(rs.getInt("numOfTotalSeats"));
                    show.setNumOfSeatsBooked(rs.getInt("numOfSeatsBooked"));
                    show.setShowDate(rs.getDate("showDate").toLocalDate());
                    show.setStartTime(rs.getTime("startTime").toLocalTime());
                    show.setEndTime(rs.getTime("endTime").toLocalTime());
                    show.setPricingTiers(rs.getString("pricingTiers"));
                    List<Object> innerList = new ArrayList<>();
                    innerList.add(show);
                    innerList.add(rs.getString("calculatedResult"));
                    showList.add(innerList);  
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return showList;
    }

    public List<ShowDetails> getShowDetailsOnDate(List<String> listOfVenueIDs,LocalDate date){
        List<ShowDetails> showList = new ArrayList<>();
        // Create the SQL query string with dynamic placeholders
        String sql = "SELECT * FROM "+tableName+" WHERE showDate = '"+date+"' AND venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") ORDER BY venueID ASC;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setString(i + 1, listOfVenueIDs.get(i));
            }


            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                    ShowDetails show = new ShowDetails();
                    show.setShowID(rs.getString("showID"));
                    show.setMovieID(rs.getString("movieID"));
                    show.setHallID(rs.getString("hallID"));
                    show.setNameOfShow(rs.getString("nameOfShow"));
                    show.setVenueID(rs.getString("venueID"));
                    show.setNumOfTotalSeats(rs.getInt("numOfTotalSeats"));
                    show.setNumOfSeatsBooked(rs.getInt("numOfSeatsBooked"));
                    show.setShowDate(rs.getDate("showDate").toLocalDate());
                    show.setStartTime(rs.getTime("startTime").toLocalTime());
                    show.setEndTime(rs.getTime("endTime").toLocalTime());
                    show.setPricingTiers(rs.getString("pricingTiers"));
                    showList.add(show);
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return showList;
    }

    public List<ShowDetails> getShowDetailsMovie(List<String> listOfVenueIDs,LocalDate startDate,LocalDate endDate, Movie movie){
        List<ShowDetails> showList = new ArrayList<>();

        String sql = "SELECT * FROM "+tableName+" WHERE showDate =  BETWEEN "+startDate+" AND "+endDate+"  AND venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") AND movieID = "+movie.getMovieID()+" ORDER BY venueID ASC;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setString(i + 1, listOfVenueIDs.get(i));
            }


            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                    ShowDetails show = new ShowDetails();
                    show.setShowID(rs.getString("showID"));
                    show.setMovieID(rs.getString("movieID"));
                    show.setHallID(rs.getString("hallID"));
                    show.setNameOfShow(rs.getString("nameOfShow"));
                    show.setVenueID(rs.getString("venueID"));
                    show.setNumOfTotalSeats(rs.getInt("numOfTotalSeats"));
                    show.setNumOfSeatsBooked(rs.getInt("numOfSeatsBooked"));
                    show.setShowDate(rs.getDate("showDate").toLocalDate());
                    show.setStartTime(rs.getTime("startTime").toLocalTime());
                    show.setEndTime(rs.getTime("endTime").toLocalTime());
                    show.setPricingTiers(rs.getString("pricingTiers"));
                    showList.add(show);
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return showList;
    }

    public List<List<Object>> getShowDetailsRevenue(List<String> listOfVenueIDs ){
        List<List<Object>> showList = new ArrayList<>();
        String sql = "SELECT *, "+
                "(CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 2), ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', -1) AS DECIMAL(10, 2))) AS calculatedResult FROM "+tableName+" WHERE venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") ORDER BY calculatedResult ASC;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setString(i + 1, listOfVenueIDs.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                    ShowDetails show = new ShowDetails();
                    show.setShowID(rs.getString("showID"));
                    show.setMovieID(rs.getString("movieID"));
                    show.setHallID(rs.getString("hallID"));
                    show.setNameOfShow(rs.getString("nameOfShow"));
                    show.setVenueID(rs.getString("venueID"));
                    show.setNumOfTotalSeats(rs.getInt("numOfTotalSeats"));
                    show.setNumOfSeatsBooked(rs.getInt("numOfSeatsBooked"));
                    show.setShowDate(rs.getDate("showDate").toLocalDate());
                    show.setStartTime(rs.getTime("startTime").toLocalTime());
                    show.setEndTime(rs.getTime("endTime").toLocalTime());
                    show.setPricingTiers(rs.getString("pricingTiers"));
                    List<Object> innerList = new ArrayList<>();
                    innerList.add(show);
                    innerList.add(rs.getString("calculatedResult"));
                    showList.add(innerList);                      
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return showList;

    }

    public List<List<String>> getRevenuePerDay(List<String> listOfVenueIDs){
        List<List<String>> showList = new ArrayList<>();
        String sql = "SELECT showDate, "+
                "SUM((CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 2), ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', -1) AS DECIMAL(10, 2)))) AS calculatedResultPerDay FROM "+tableName+" WHERE venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") GROUP BY showDate ORDER BY showDate DESC";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setString(i + 1, listOfVenueIDs.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                    List<String> innerList = new ArrayList<>();
                    innerList.add(rs.getString("showDate"));
                    innerList.add(rs.getString("calculatedResultPerDay"));
                    showList.add(innerList);                      
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return showList;
    }

    public List<List<String>> getRevenuePerMovie(List<String> listOfVenueIDs){
        List<List<String>> showList = new ArrayList<>();
        String sql = "SELECT nameOfShow, "+
                "SUM((CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', 2), ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "CAST(SUBSTRING_INDEX(numOfSeatsBookedPerTier, ',', -1) AS DECIMAL(10, 2)) * CAST(SUBSTRING_INDEX(pricingTiers, ',', -1) AS DECIMAL(10, 2)))) AS calculatedResultPerDay FROM "+tableName+" WHERE venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") GROUP BY nameOfShow";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setString(i + 1, listOfVenueIDs.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                    List<String> innerList = new ArrayList<>();
                    innerList.add(rs.getString("nameOfShow"));
                    innerList.add(rs.getString("calculatedResultPerDay"));
                    showList.add(innerList);                      
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return showList;
    }

    }
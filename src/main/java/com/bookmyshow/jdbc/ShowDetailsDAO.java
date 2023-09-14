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

    @Override
    protected void finalize(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection not closed due to " + e);
        }
    }

    private boolean checkDateRange(LocalDate startDate, LocalDate endDate){
        if(endDate.isBefore(startDate)){
            return false;
        }else return true;
    }

    public List<ShowDetails> getShowsInDateRange(LocalDate startDate, LocalDate endDate, String venueName){
        List<ShowDetails> shows = new ArrayList<>();
        if(checkDateRange(startDate,endDate)){
            String sqlQuery = "SELECT * FROM Shows JOIN (SELECT venueID FROM Venue WHERE Venue.name=?) AS q WHERE Shows.venueID=q.venueID AND Shows.showDate BETWEEN ? AND ?";
            PreparedStatement pstmt=null;
            ResultSet rs = null;
            try{
                pstmt= connection.prepareStatement(sqlQuery);
                pstmt.setString(1, venueName);
                pstmt.setString(2, String.valueOf(startDate));
                pstmt.setString(3, String.valueOf(endDate));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String pricingTiers = rs.getString("pricingTiers");
                    String[] temp = pricingTiers.split(",");
                    double silverPrice = Double.parseDouble(temp[0]);
                    double goldPrice = Double.parseDouble(temp[1]);
                    double platinumPrice = Double.parseDouble(temp[2]);
                    ShowDetails sd = new ShowDetails(
                            rs.getInt("showID"),
                            rs.getInt("hallID"),
                            rs.getString("movieName"),
                            rs.getInt("venueID"),
                            rs.getDate("showDate").toLocalDate(),
                            rs.getTime("startTime").toLocalTime(),
                            rs.getTime("endTime").toLocalTime(),
                            silverPrice, goldPrice, platinumPrice
                    );
                    shows.add(sd);
                }
                if(shows.isEmpty()){
                    System.out.println("This venue does not exist in database.");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        System.out.println("Result set not closed: "+ e);
                    }
                }
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        System.out.println("Prepared statement not closed: "+ e);
                    }
                }
            }
        }
        else {
            System.out.println("Entered date range is not valid, try again.");
        }
        return shows;
    }

    public List<ShowDetails> getShowsOnDate(LocalDate date, String venueName){
        return getShowsInDateRange(date,date,venueName);
    }

    public List<ShowDetails> getShowsForMovieInDateRange(String movieName,LocalDate startDate, LocalDate endDate){
        List<ShowDetails> shows = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " +tableName+ " WHERE movieName=? AND showDate BETWEEN ? AND ?";
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        if(checkDateRange(startDate,endDate)){
            try{
                pstmt = connection.prepareStatement(sqlQuery);
                pstmt.setString(1,movieName);
                pstmt.setString(2, String.valueOf(startDate));
                pstmt.setString(3, String.valueOf(endDate));
                rs = pstmt.executeQuery();
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
                if(shows.isEmpty()){
                    System.out.println("This movie does not exist in database.");
                }
            }catch (SQLException e) {
                System.out.println(e);
            }finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        System.out.println("Result set not closed: "+ e);
                    }
                }
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        System.out.println("Prepared statement not closed: "+ e);
                    }
                }
            }
        }

        return shows;
    }

    public List<ShowDetails> getShowsForMovieOnDate(String movieName,LocalDate date) {
        return getShowsForMovieInDateRange(movieName,date,date);
    }

    public int getAvailableSeatsForSilver(int showID){
        int numSilverAvailable = 0;
        String sqlQuery = "SELECT numSeatsSilver-numSilverBooked AS diff FROM Shows JOIN Hall WHERE Shows.hallID=Hall.hallID AND showID=?";
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        try{
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                numSilverAvailable = Integer.parseInt(rs.getString("diff"));
            }
        }catch (SQLException e) {
            System.out.println(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result set not closed: "+ e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Prepared statement not closed: "+ e);
                }
            }

        }
        return numSilverAvailable;
    }

    public int getAvailableSeatsForGold(int showID){
        int numGoldAvailable = 0;
        String sqlQuery = "SELECT numSeatsGold-numGoldBooked AS diff FROM Shows JOIN Hall WHERE Shows.hallID=Hall.hallID AND showID=?";
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        try{
            pstmt=connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                numGoldAvailable = Integer.parseInt(rs.getString("diff"));
            }
        }catch (SQLException e) {
            System.out.println(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result set not closed: "+ e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Prepared statement not closed: "+ e);
                }
            }

        }

        return numGoldAvailable;
    }

    public int getAvailableSeatsForPlatinum(int showID){
        int numPlatinumAvailable = 0;
        String sqlQuery = "SELECT numSeatsPlatinum-numPlatinumBooked AS diff FROM Shows JOIN Hall WHERE Shows.hallID=Hall.hallID AND showID=?";
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        try{
            pstmt=connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                numPlatinumAvailable = Integer.parseInt(rs.getString("diff"));
            }
        }catch (SQLException e) {
            System.out.println(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result set not closed: "+ e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Prepared statement not closed: "+ e);
                }
            }

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

    private boolean checkClashWithExistingShows(ShowDetails showDetails) {
        boolean isClash=true;
        int queryVenueID = showDetails.getVenueID();
        int queryHallID = showDetails.getHallID();
        Date queryDate = showDetails.getShowDate();
        Time queryStartTime = showDetails.getStartTime();
        Time queryEndTime = showDetails.getEndTime();
        String sqlQuery = "SELECT venueID,hallID,showDate,startTime,endTime FROM Shows WHERE " +
                "venueID=? AND hallID=? AND showDate=? AND startTime<= ? AND endTime>=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,queryVenueID);
            pstmt.setInt(2,queryHallID);
            pstmt.setDate(3,queryDate);
            pstmt.setTime(4,queryStartTime);
            pstmt.setTime(5,queryStartTime);
            rs = pstmt.executeQuery();
            if(rs.next()==false) {
                isClash = false;
            }
        }catch (SQLException e) {
            System.out.println(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result set not closed: "+ e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Prepared statement not closed: "+ e);
                }
            }
        }
        return isClash;
    }

    public void addShow(ShowDetails showDetails){
        boolean isClashing = checkClashWithExistingShows(showDetails);
        if(!isClashing) {
            String sqlQuery = "INSERT INTO Shows(movieName,venueID,hallID,pricingTiers,showDate,startTime,endTime) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
                pstmt.setString(1, showDetails.getMovieName());
                pstmt.setInt(2, showDetails.getVenueID());
                pstmt.setInt(3, showDetails.getHallID());
                pstmt.setString(4, showDetails.getPricingTiers());
                pstmt.setDate(5, showDetails.getShowDate());
                pstmt.setTime(6, showDetails.getStartTime());
                pstmt.setTime(7, showDetails.getEndTime());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }else {
            System.out.println("This show clashes ");
        }
    }

    public int getRowsInShowsTable() {
        String sqlQuery = "SELECT COUNT(userID) FROM " + tableName;
        int rows = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result set not closed: "+ e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Prepared statement not closed: "+ e);
                }
            }

        }
        return rows;
    }

    public void removeShow(int showID){
        String sqlQuery = "DELETE FROM Shows WHERE showID=?";
        PreparedStatement pstmt =null;
        try {
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,showID);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: "+ rowsAffected);
        }catch (SQLException e){
            System.out.println(e);
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Prepared statement not closed: "+ e);
                }
            }
        }
    }

    public void getShowDetails(int venueId) throws SQLException{
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE venuelD = ?";
        List<User> owners=new ArrayList<User>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        try{
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, venueId); 
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User u=new Owner(rs.getString("userID"), rs.getString("password"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("emailID"),rs.getString("bankAccNo") );
                owners.add(u);
            }
        }
        catch(Exception e){
            System.out.println(e);

        }
        finally{
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<List<Object>> getShowDetailsNextThreeDays(List<Integer> listOfVenueIDs ){

        List<List<Object>> showList = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;

        String sql = "SELECT Shows.*, Venue.name AS venueName, Hall.* FROM "+tableName+" JOIN Venue ON Shows.venueID = Venue.venueID "+
                "JOIN Hall ON Shows.hallID = Hall.hallID WHERE Shows.showDate BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 3 DAY) AND Shows.venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") ORDER BY Shows.showDate ASC, venueName ASC;";
        try{
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setInt(i + 1, listOfVenueIDs.get(i));
            }

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] parts = rs.getString("pricingTiers").split(",");
                ShowDetails show = new ShowDetails(Integer.parseInt(rs.getString("showID")),Integer.parseInt(rs.getString("hallID")),rs.getString("movieName"),
                        Integer.parseInt(rs.getString("venueID")) ,rs.getDate("showDate").toLocalDate(), rs.getTime("startTime").toLocalTime(),rs.getTime("endTime").toLocalTime(),
                        Double.parseDouble(parts[0]),Double.parseDouble(parts[1]),Double.parseDouble(parts[2]));
                Hall hall = new Hall();
                hall.setHallId(Integer.parseInt(rs.getString("hallID")));
                hall.setHallName(rs.getString("hallName"));
                hall.setNumSeatsGold(Integer.parseInt(rs.getString("numSeatsGold")));
                hall.setNumSeatsSilver(Integer.parseInt(rs.getString("numSeatsSilver")));
                hall.setNumSeatsPlatinum(Integer.parseInt(rs.getString("numSeatsPlatinum")));

                List<Object> innerList = new ArrayList<>();
                innerList.add(show);
                innerList.add(rs.getString("venueName"));
                innerList.add(hall);
                showList.add(innerList);
            }
        }
        catch(Exception e) {
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset has been failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement has been failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection has been failed!");
                }
            }
        }
        return showList;
    }

    public List<List<Object>> getShowDetailsPastSevenDays(List<Integer> listOfVenueIDs ){
        List<List<Object>> showList = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        String sql = "SELECT Shows.*, Venue.name AS venueName, Hall.*, "+
                "(Shows.numSilverBooked * CAST(SUBSTRING_INDEX(Shows.pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "Shows.numGoldBooked * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(Shows.pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "Shows.numPlatinumBooked * CAST(SUBSTRING_INDEX(Shows.pricingTiers, ',', -1) AS DECIMAL(10, 2))) AS calculatedResult"
                +" FROM "+tableName+" JOIN Venue ON Shows.venueID = Venue.venueID JOIN Hall ON Shows.hallID = Hall.hallID WHERE Shows.showDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE() AND Shows.venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") ORDER BY Shows.showDate DESC, venueName ASC;";
        try{
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setInt(i + 1, listOfVenueIDs.get(i));
            }

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] parts = rs.getString("pricingTiers").split(",");
                ShowDetails show = new ShowDetails(Integer.parseInt(rs.getString("showID")),Integer.parseInt(rs.getString("hallID")),rs.getString("movieName"),
                        Integer.parseInt(rs.getString("venueID")) ,rs.getDate("showDate").toLocalDate(), rs.getTime("startTime").toLocalTime(),rs.getTime("endTime").toLocalTime(),
                        Double.parseDouble(parts[0]),Double.parseDouble(parts[1]),Double.parseDouble(parts[2]));
                Hall hall = new Hall();
                hall.setHallId(Integer.parseInt(rs.getString("hallID")));
                hall.setHallName(rs.getString("hallName"));
                hall.setNumSeatsGold(Integer.parseInt(rs.getString("numSeatsGold")));
                hall.setNumSeatsSilver(Integer.parseInt(rs.getString("numSeatsSilver")));
                hall.setNumSeatsPlatinum(Integer.parseInt(rs.getString("numSeatsPlatinum")));

                List<Object> innerList = new ArrayList<>();
                innerList.add(show);
                innerList.add(rs.getString("venueName"));
                innerList.add(hall);
                innerList.add(rs.getString("calculatedResult"));
                showList.add(innerList);
            }
        }
        catch(Exception e) {
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset has been failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement has been failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection has been failed!");
                }
            }
        }
        return showList;
    }

    public List<List<String>> getRevenuePerDay(List<Integer> listOfVenueIDs){
        List<List<String>> showList = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        String sql = "SELECT showDate, "+
                "SUM(( numSilverBooked * CAST(SUBSTRING_INDEX(pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "numGoldBooked * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "numPlatinumBooked * CAST(SUBSTRING_INDEX(pricingTiers, ',', -1) AS DECIMAL(10, 2)))) AS calculatedResultPerDay FROM "+tableName+" WHERE venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") GROUP BY showDate ORDER BY showDate DESC";
        try{
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setInt(i + 1, listOfVenueIDs.get(i));
            }
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                List<String> innerList = new ArrayList<>();
                innerList.add(rs.getString("showDate"));
                innerList.add(rs.getString("calculatedResultPerDay"));
                showList.add(innerList);
            }
        }
        catch(Exception e) {
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset has been failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement has been failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection has been failed!");
                }
            }
        }

        return showList;
    }

    public List<List<String>> getRevenuePerMovie(List<Integer> listOfVenueIDs){
        List<List<String>> showList = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        String sql = "SELECT movieName, "+
                "SUM((numSilverBooked * CAST(SUBSTRING_INDEX(pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "numGoldBooked * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "numPlatinumBooked * CAST(SUBSTRING_INDEX(pricingTiers, ',', -1) AS DECIMAL(10, 2)))) AS calculatedResultPerDay FROM "+tableName+" WHERE venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") GROUP BY movieName";
        try{
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setInt(i + 1, listOfVenueIDs.get(i));
            }

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                List<String> innerList = new ArrayList<>();
                innerList.add(rs.getString("movieName"));
                innerList.add(rs.getString("calculatedResultPerDay"));
                showList.add(innerList);
            }
        }
        catch(Exception e) {
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset has been failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement has been failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection has been failed!");
                }
            }
        }
        return showList;
    }

    public List<List<Object>> getShowDetailsRevenue(List<Integer> listOfVenueIDs ){
        List<List<Object>> showList = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        String sql = "SELECT Shows.*, Venue.name AS venueName, Hall.* , "+
                "(Shows.numSilverBooked * CAST(SUBSTRING_INDEX(Shows.pricingTiers, ',', 1) AS DECIMAL(10, 2)) + " +
                "Shows.numGoldBooked * CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(Shows.pricingTiers, ',', 2), ',', -1) AS DECIMAL(10, 2)) + "+
                "Shows.numPlatinumBooked * CAST(SUBSTRING_INDEX(Shows.pricingTiers, ',', -1) AS DECIMAL(10, 2))) AS calculatedResult FROM "+tableName+" JOIN Venue ON Shows.venueID = Venue.venueID "+
                "JOIN Hall ON Shows.hallID = Hall.hallID WHERE Shows.venueID IN (";
        for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
                sql += ", "; // Add a comma if not the last placeholder
            }
        }
        sql += ") ORDER BY calculatedResult ASC;";
        try{
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setInt(i + 1, listOfVenueIDs.get(i));
            }
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] parts = rs.getString("pricingTiers").split(",");
                ShowDetails show = new ShowDetails(Integer.parseInt(rs.getString("showID")),Integer.parseInt(rs.getString("hallID")),rs.getString("movieName"),
                        Integer.parseInt(rs.getString("venueID")) ,rs.getDate("showDate").toLocalDate(), rs.getTime("startTime").toLocalTime(),rs.getTime("endTime").toLocalTime(),
                        Double.parseDouble(parts[0]),Double.parseDouble(parts[1]),Double.parseDouble(parts[2]));
                Hall hall = new Hall();
                hall.setHallId(Integer.parseInt(rs.getString("hallID")));
                hall.setHallName(rs.getString("hallName"));
                hall.setNumSeatsGold(Integer.parseInt(rs.getString("numSeatsGold")));
                hall.setNumSeatsSilver(Integer.parseInt(rs.getString("numSeatsSilver")));
                hall.setNumSeatsPlatinum(Integer.parseInt(rs.getString("numSeatsPlatinum")));

                List<Object> innerList = new ArrayList<>();
                innerList.add(show);
                innerList.add(rs.getString("venueName"));
                innerList.add(hall);
                innerList.add(rs.getString("calculatedResult"));
                showList.add(innerList);
            }
        }
        catch(Exception e) {
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset has been failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement has been failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection has been failed!");
                }
            }
        }
        return showList;

    }

    public List<List<Object>> getShowDetailsOnDate(List<Integer> listOfVenueIDs, LocalDate date){
        List<List<Object>> showList = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        String sql = "SELECT Shows.*, Venue.name AS venueName, Hall.* " +
            "FROM " + tableName + " " +
            "JOIN Venue ON Shows.venueID = Venue.venueID " +
            "JOIN Hall ON Shows.hallID = Hall.hallID " +
            "WHERE showDate = ? " +
            "AND Shows.venueID IN (";

            for (int i = 0; i < listOfVenueIDs.size(); i++) {
            sql += "?"; // Add a placeholder
            if (i < listOfVenueIDs.size() - 1) {
            sql += ", "; // Add a comma if not the last placeholder
            }
            }

        sql += ") ORDER BY venueName ASC;";

        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, java.sql.Date.valueOf(date));

            int parameterIndex = 2;
            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setInt(parameterIndex, listOfVenueIDs.get(i));
                parameterIndex++;
            }
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] parts = rs.getString("pricingTiers").split(",");
                ShowDetails show = new ShowDetails(Integer.parseInt(rs.getString("showID")),Integer.parseInt(rs.getString("hallID")),rs.getString("movieName"),
                        Integer.parseInt(rs.getString("venueID")) ,rs.getDate("showDate").toLocalDate(), rs.getTime("startTime").toLocalTime(),rs.getTime("endTime").toLocalTime(),
                        Double.parseDouble(parts[0]),Double.parseDouble(parts[1]),Double.parseDouble(parts[2]));
                Hall hall = new Hall();
                hall.setHallId(Integer.parseInt(rs.getString("hallID")));
                hall.setHallName(rs.getString("hallName"));
                hall.setNumSeatsGold(Integer.parseInt(rs.getString("numSeatsGold")));
                hall.setNumSeatsSilver(Integer.parseInt(rs.getString("numSeatsSilver")));
                hall.setNumSeatsPlatinum(Integer.parseInt(rs.getString("numSeatsPlatinum")));

                List<Object> innerList = new ArrayList<>();
                innerList.add(show);
                innerList.add(rs.getString("venueName"));
                innerList.add(hall);
                showList.add(innerList);
            }
        }
        catch(Exception e) {
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset has been failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement has been failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection has been failed!");
                }
            }
        }
        return showList;
    }

    public List<List<Object>> getShowDetailsMovie(List<Integer> listOfVenueIDs,LocalDate startDate,LocalDate endDate, String movieName){
        List<List<Object>> showList = new ArrayList<>();
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        String sql = "SELECT Shows.*, Venue.name AS venueName, Hall.* " +
                        "FROM " + tableName + " " +
                        "JOIN Venue ON Shows.venueID = Venue.venueID " +
                        "JOIN Hall ON Shows.hallID = Hall.hallID " +
                        "WHERE Shows.showDate BETWEEN ? AND ? " +
                        "AND Shows.venueID IN (";
                        for (int i = 0; i < listOfVenueIDs.size(); i++) {
                            sql += "?"; // Add a placeholder
                            if (i < listOfVenueIDs.size() - 1) {
                                sql += ", "; // Add a comma if not the last placeholder
                            }
                        }
        sql += ") AND Shows.movieName = ? ORDER BY venueName ASC;";

        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));
            int parameterIndex = 3; // Start from index 3 for venue IDs
            for (int i = 0; i < listOfVenueIDs.size(); i++) {
                preparedStatement.setInt(parameterIndex, listOfVenueIDs.get(i));
                parameterIndex++;
            }
            preparedStatement.setString(parameterIndex, movieName);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] parts = rs.getString("pricingTiers").split(",");
                ShowDetails show = new ShowDetails(Integer.parseInt(rs.getString("showID")),Integer.parseInt(rs.getString("hallID")),rs.getString("movieName"),
                        Integer.parseInt(rs.getString("venueID")) ,rs.getDate("showDate").toLocalDate(), rs.getTime("startTime").toLocalTime(),rs.getTime("endTime").toLocalTime(),
                        Double.parseDouble(parts[0]),Double.parseDouble(parts[1]),Double.parseDouble(parts[2]));
                Hall hall = new Hall();
                hall.setHallId(Integer.parseInt(rs.getString("hallID")));
                hall.setHallName(rs.getString("hallName"));
                hall.setNumSeatsGold(Integer.parseInt(rs.getString("numSeatsGold")));
                hall.setNumSeatsSilver(Integer.parseInt(rs.getString("numSeatsSilver")));
                hall.setNumSeatsPlatinum(Integer.parseInt(rs.getString("numSeatsPlatinum")));

                List<Object> innerList = new ArrayList<>();
                innerList.add(show);
                innerList.add(rs.getString("venueName"));
                innerList.add(hall);
                showList.add(innerList);
            }
        }
        catch(Exception e) {
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset has been failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement has been failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection has been failed!");
                }
            }
        }
        return showList;
    }
}

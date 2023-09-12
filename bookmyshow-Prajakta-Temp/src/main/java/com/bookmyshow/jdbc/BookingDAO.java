package src.main.java.com.bookmyshow.jdbc;

import src.main.java.com.bookmyshow.model.Booking;
import src.main.java.com.bookmyshow.model.ShowDetails;
import src.main.java.com.bookmyshow.service.JDBCMain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private Connection connection;
    private String tableName="Booking";
    public BookingDAO(){
        connection = DatabaseConnectionManager.getConnection();
    }


    public void getAllBookingDetails() throws Exception {
        String tableName = "Booking";
        String sqlQuery = "SELECT * FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery, tableName);
    }

    public List<Booking> getBookingHistoryOfCustomer(String custID){
        List<Booking> bookings = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Booking WHERE custID=?";
        PreparedStatement pstmt = null;
        Connection connection = null;
        try{
            connection = DatabaseConnectionManager.getConnection();
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,custID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Booking bk=new Booking(rs.getInt("bookingID"),
                        rs.getString("custID"),
                        rs.getInt("venueID"),
                        rs.getInt("hallID"),
                        rs.getInt("showID"),
                        rs.getDate("bookingDate").toLocalDate(),
                        rs.getInt("numSeatsBooked"),
                        rs.getString("bankDetailsCust"),
                        rs.getString("pricingTierChosen"),
                        rs.getString("transactionStatus"));
                bookings.add(bk);
            }
            pstmt.close();
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        finally {
            //Close pstmt and connection here
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection!= null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error ");
            }
        }
        return bookings;
    }

    public void insertNewBooking(Booking booking) throws SQLException {
        PreparedStatement pstmt = null;
        Connection connection = null;
        String createSQLQuery  = "CREATE TABLE IF NOT EXISTS Booking (bookingID INT AUTO_INCREMENT PRIMARY KEY,  custID INT,  venueID INT,  showID INT, bookingDate VARCHAR(255),  numSeatsBooked INT,  bankDetailsCust VARCHAR(255),  pricingTierChosen VARCHAR(255), transactionStatus VARCHAR(255))";
        String sqlQuery = "INSERT INTO " + tableName +
                "(custID,venueID,hallID,showID,bookingDate,numSeatsBooked,pricingTierChosen,bankDetailsCust,transactionStatus )" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            connection= DatabaseConnectionManager.getConnection();
            pstmt = connection.prepareStatement(createSQLQuery);
            pstmt.execute();

            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,booking.getCustID());
            pstmt.setInt(2,booking.getVenueID());
            pstmt.setInt(3,booking.getHallID());
            pstmt.setInt(4,booking.getShowID());
            pstmt.setDate(5, Date.valueOf(booking.getBookingDate()));
            pstmt.setInt(6,booking.getNumSeatsBooked());
            pstmt.setString(7,booking.getPricingTierChosen());
            pstmt.setString(8,booking.getBankDetailsCust());
            pstmt.setString(9,booking.getTransactionStatus());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //Close pstmt and connection here
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection!= null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
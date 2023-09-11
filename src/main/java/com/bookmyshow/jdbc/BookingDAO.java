package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class BookingDAO {
    private static Connection connection;
    private static String tableName = "Booking";

    public static void getAllBookingDetails() throws Exception {
        String tableName = "Booking";
        String sqlQuery = "SELECT * FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery, tableName);
    }


     public static  void insertBooking(Booking booking) throws  SQLException {

        Connection connection= null;
        PreparedStatement preparedStatement = null;
        try {
            connection= DatabaseConnectionManager.getConnection();
            //Create table if it does not exist
            String createSQLQuery  = "CREATE TABLE IF NOT EXISTS Booking (bookingID INT AUTO_INCREMENT PRIMARY KEY,  custID INT,  venueID INT,  showID INT, bookingDate VARCHAR(255),  numSeatsBooked INT,  bankDetailsCust VARCHAR(255),  pricingTierChosen VARCHAR(255), transactionStatus VARCHAR(255))";

            String sqlQuery = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt1 = connection.prepareStatement(createSQLQuery);
            pstmt1.execute();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1,booking.getBookingID());
            pstmt.setString(2,booking.getCustID());
            pstmt.setInt(3,booking.getVenueID());
            pstmt.setInt(4,booking.getShowID());
            pstmt.setDate(5, new java.sql.Date(booking.getBookingDate().getTime()));
            pstmt.setInt(6,booking.getNumSeatsBooked());
            pstmt.setString(7,booking.getBankDetailsCust());
            pstmt.setString(8,booking.getPricingTierChosen());
            pstmt.setString(9,booking.getTransactionStatus());
            pstmt.executeUpdate();
            System.out.println("Booking data inserted");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
     }
}

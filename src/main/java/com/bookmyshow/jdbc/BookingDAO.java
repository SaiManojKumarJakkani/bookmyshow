package src.main.java.com.bookmyshow.jdbc;

import src.main.java.com.bookmyshow.model.Booking;
import src.main.java.com.bookmyshow.model.ShowDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private static Connection connection;
    private static String tableName="Booking";
    public BookingDAO(){
        connection = DatabaseConnectionManager.getConnection();
    }


    public List<Booking> getBookingHistoryOfCustomer(String custID){
        List<Booking> bookings = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Booking WHERE custID=?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
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
        catch(Exception e) {
            System.out.println(e);
        }
        return bookings;
    }

    public static void insertNewBooking(Booking booking) throws SQLException {
        //custID, showID, venueID depends on other tables
        //BookingDate, BankAccount, numseatsbooked, bankdetails, pricing tierchosen hardcoded.
        //transaction depends on seats available.

        String sqlQuery = "INSERT INTO " + tableName +
                "(custID,venueID,hallID,showID,bookingDate,numSeatsBooked,pricingTierChosen,bankDetailsCust,transactionStatus )" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            connection= DatabaseConnectionManager.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package org.example;

public class BookingDAO {

    public static void getAllBookingDetails() throws Exception {
        String tableName = "Booking";
        String sqlQuery = "SELECT * FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery, tableName);
    }
    public  static void getVenueID() throws Exception {
        //get venueID from venue table
        String tableName = "Booking";
        String sqlQuery = "SELECT venueid FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery,tableName);
    }

    public  static void getCustID() throws Exception{
        //get custID from customer table
        String tableName = "Booking";
        String sqlQuery = "SELECT userID FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery,tableName);
    }

    public static void getShowID() throws Exception {
        String tableName = "Booking";
        String sqlQuery = "SELECT showID FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery,tableName);
        //get showID from show table
    }
    public static void getSevenDaysBookingHistory()  throws Exception {
        String tableName = "Booking";
        String sqlQuery = "SELECT * FROM " + tableName + " LIMIT 7";
        JDBCMain.getDataJDBC(sqlQuery,tableName);

    }


}

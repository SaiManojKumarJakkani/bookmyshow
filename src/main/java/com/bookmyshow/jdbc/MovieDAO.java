package src.main.java.com.bookmyshow.jdbc;
import src.main.java.com.bookmyshow.model.Movie;
import src.main.java.com.bookmyshow.service.JDBCMain;

import static java.sql.DriverManager.getConnection;
import  java.sql.*;
//This class should connect with DB, insert or get data from DB
public class MovieDAO {
    private Connection connection;
    private String tableName = "Movie";

    public void getMovieDetails () throws Exception {
        String tableName = "Movie";
        String sqlQuery = "SELECT * FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery, tableName);
    }

    //If the owners wants to add new movie
    public void insertNewMovie( Movie movie) throws  SQLException{
        Connection connection = null;
        PreparedStatement pstmt = null;

        String sqlQuery = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?,?)";
        try {
            connection= DatabaseConnectionManager.getConnection();
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,movie.getMovieName());
            pstmt.setString(2,movie.getGenre());
            pstmt.setString(3,movie.getActor());
            pstmt.setString(4,movie.getActress());
            pstmt.setString(5,movie.getProducer());
            pstmt.setString(6,movie.getDirector());
            pstmt.setString(7,movie.getLanguage());
            pstmt.setString(8,movie.getRating());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
          if (pstmt != null) {
                try {
                    pstmt.close();
                }
                catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement had failed!");
                }

            }
            if (connection!= null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    System.out.println(e+": Closing of connection had failed!");
                }
            }
    }
 }

}


        
    
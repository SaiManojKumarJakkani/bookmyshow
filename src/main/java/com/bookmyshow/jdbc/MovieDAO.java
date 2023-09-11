package src.main.java.com.bookmyshow.jdbc;
import src.main.java.com.bookmyshow.model.Movie;
import src.main.java.com.bookmyshow.service.JDBCMain;

import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.DriverManager.getConnection;
import  java.sql.*;
//This class should connect with DB, insert or get data from DB
public class MovieDAO {
    private static Connection connection;
    private static String tableName = "Movie";

    public static void getMovieDetails () throws Exception {
        String tableName = "Movie";
        String sqlQuery = "SELECT * FROM " + tableName;
        JDBCMain.getDataJDBC(sqlQuery, tableName);
    }

    //If the owners wants to add new movie
    public static void insertNewMovie( Movie movie) throws  SQLException{

        String sqlQuery = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?,?)";
        try {
            connection= DatabaseConnectionManager.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,movie.getMovieName());
            pstmt.setString(2,movie.getGenre());
            pstmt.setString(3,movie.getActor());
            pstmt.setString(4,movie.getActress());
            pstmt.setString(5,movie.getProducer());
            pstmt.setString(6,movie.getDirector());
            pstmt.setString(7,movie.getLanguage());
            pstmt.setString(8,movie.getRating());
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}


        
    
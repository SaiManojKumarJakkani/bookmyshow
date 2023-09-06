package src.main.java.com.bookmyshow.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.bookmyshow.model.Movie;
import src.main.java.com.bookmyshow.model.Owner;
import src.main.java.com.bookmyshow.model.User;

public class MovieDAO {
    private Connection connection;
    private String tableName="Shows";
    public MovieDAO(){
        connection=DatabaseConnectionManager.getConnection();
    }
    public Movie getMovieDetails(String name){
        String sqlQuery = "SELECT * FROM " + tableName +" where movieName = '"+name+"'";
        Movie movie=null;
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            if(rs.next()){
                movie=new Movie();
                movie.setMovieName(rs.getString("movieName"));
                movie.setMovieID(rs.getString("movieID"));
            }
        }
        catch(Exception e){
            System.out.println(e);
            
        }
        return movie;


    }


    
}

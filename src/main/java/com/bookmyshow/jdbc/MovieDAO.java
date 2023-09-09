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
    private String tableName="Movie";
    public MovieDAO(){
        connection=DatabaseConnectionManager.getConnection();
    }

    
}

package src.main.java.com.bookmyshow.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.bookmyshow.model.*;

public class OwnerDAO {
    private Connection connection;
    private String tableName="Owner";
    public OwnerDAO(){
        connection=DatabaseConnectionManager.getConnection();
    }

    public List<User> getAllOwners(){
        String sqlQuery = "SELECT * FROM " + tableName;
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
            System.out.println(e+" fetching of data from database had failed!");

        }
        return owners;
    };

    public User getOwner(String userID){
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE userID = ?";
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;

        User owner=null;
        try{
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, userID);

            // Execute the query
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                owner=new Owner(rs.getString("userID"), rs.getString("password"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("emailID"),rs.getString("bankAccNo") );
            }
        }
        catch(Exception e){
            System.out.println(e+" fetching of data from database had failed!");
        }
        finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of resultset had failed!");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of preparedStatement had failed!");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e+": Closing of connection had failed!");
                }
            }
        }
        return owner;
    };

    public int insertOwner(Owner userID){
        String sqlQuery = "INSERT INTO Owner (userID, password, firstName, lastName, emailID, bankAccNo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            // Set the parameter values using setters
            preparedStatement.setString(1, userID.getUserID());
            preparedStatement.setString(2, userID.getPassword());
            preparedStatement.setString(3, userID.getFirstName());
            preparedStatement.setString(4, userID.getLastName());
            preparedStatement.setString(5, userID.getEmailID());
            preparedStatement.setString(6, userID.getBankAccNo());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected;
        }
        catch(Exception e){
            System.out.println(e+" Insertion of data into database had failed!");
            return 0;
        }

    };

}
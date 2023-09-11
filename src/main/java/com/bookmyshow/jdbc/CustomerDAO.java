package src.main.java.com.bookmyshow.jdbc;

import src.main.java.com.bookmyshow.model.Customer;
import src.main.java.com.bookmyshow.model.Owner;
import src.main.java.com.bookmyshow.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private Connection connection;
    private String tableName="Customer";
    public CustomerDAO(){
        connection = DatabaseConnectionManager.getConnection();
    }

    public List<User> getAllCustomers(){
        String sqlQuery = "SELECT * FROM " + tableName;
        List<User> customers = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                User c=new Customer(rs.getString("userID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"));
                customers.add(c);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return customers;
    }

    public User getCustomerByUserID(String queryUserID){
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE userID = ?";
        User user = null;
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,queryUserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new Customer(rs.getString("userID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return user;
    }

    public User getCustomerByEmailID(String queryEmailID){
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE email = ?";
        User user = null;
        try{
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,queryEmailID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new Customer(rs.getString("userID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return user;
    }

    public int addNewCustomer(Customer customer){
        String sqlQuery = "INSERT INTO " + tableName +" VALUES (?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, customer.getUserID());
            pstmt.setString(2, customer.getPassword());
            pstmt.setString(3, customer.getFirstName());
            pstmt.setString(4, customer.getLastName());
            pstmt.setString(5, customer.getEmailID());
            rowsAffected = pstmt.executeUpdate();
        }catch (Exception e){
            rowsAffected=-1;
            System.out.println(e);
        }
        return rowsAffected;
    }

    public int getRowsInCustomerTable() {
        String sqlQuery = "SELECT COUNT(userID) FROM " +tableName;
        int rows =0;
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            rs.next();
            rows = rs.getInt(1);
        }catch(Exception e){
            System.out.println(e);
        }
        return rows;
    }
}

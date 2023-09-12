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
    @Override
    protected void finalize(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection not closed due to " + e);
        }
    }

    public List<User> getAllCustomers(){
        String sqlQuery = "SELECT * FROM " + tableName;
        List<User> customers = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try{
            st = connection.createStatement();
            rs = st.executeQuery(sqlQuery);
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
            System.out.println("Fetching customers failed due to "  + e);
        }finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Resultset not closed due to " + e);
                }
            }
            if(st!=null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println("statement not closed due to " + e);
                }
            }
        }
        return customers;
    }

    public User getCustomerByUserID(String queryUserID){
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE userID = ?";
        User user = null;
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try{
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,queryUserID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new Customer(rs.getString("userID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"));
            }
        }catch(Exception e){
            System.out.println("Fetching customer details failed due to "  + e);
        }finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Resultset not closed due to " + e);
                }
            }
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("statement not closed due to " + e);
                }
            }
        }
        return user;
    }

    public User getCustomerByEmailID(String queryEmailID){
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE email = ?";
        User user = null;
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try{
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1,queryEmailID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new Customer(rs.getString("userID"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"));
            }
        }catch(Exception e){
            System.out.println("Fetching customer details failed due to "  + e);
        }finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Resultset not closed due to " + e);
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("statement not closed due to "+ e);
                }
            }

        }
        return user;
    }

    public int addNewCustomer(Customer customer){
        String sqlQuery = "INSERT INTO " + tableName +" VALUES (?,?,?,?,?)";
        int rowsAffected = 0;
        PreparedStatement pstmt =null;
        try{
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, customer.getUserID());
            pstmt.setString(2, customer.getPassword());
            pstmt.setString(3, customer.getFirstName());
            pstmt.setString(4, customer.getLastName());
            pstmt.setString(5, customer.getEmailID());
            rowsAffected = pstmt.executeUpdate();
        }catch (Exception e){
            rowsAffected=-1;
            System.out.println("Could not add new customer due to"+e);
        }finally {
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("statement not closed due to " + e);
                }
            }
        }
        return rowsAffected;
    }

    public int getRowsInCustomerTable() {
        String sqlQuery = "SELECT COUNT(userID) FROM " +tableName;
        int rows =0;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rs.next();
            rows = rs.getInt(1);
        }catch(Exception e){
            System.out.println(e);
        }finally {
            if(stmt!=null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("statement not closed due to " + e);
                }
            }
        }
        return rows;
    }
}

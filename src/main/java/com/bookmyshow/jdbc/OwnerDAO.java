package src.main.java.com.bookmyshow.jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
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
                System.out.println(e);
                
            }
            return owners;
        };
        
        public User getOwner(String userID){
            String sqlQuery = "SELECT * FROM " + tableName+ " WHERE userID = '"+userID+"'";
            User owner=null;
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sqlQuery);
                if(rs.next()){
                    owner=new Owner(rs.getString("userID"), rs.getString("password"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("emailID"),rs.getString("bankAccNo") );
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            return owner;
        };
        
        public int insertOwner(Owner userID){
            String sqlQuery = "INSERT INTO Owner (userID, password, firstName, lastName, emailID, bankAccNo) "+
            "VALUES ('" + userID.getUserID()+ "','"+userID.getPassword()+ "','"+userID.getFirstName()+ "','"+userID.getLastName()+ "','"+userID.getEmailID()  + "','"+userID.getBankAccNo()+"');";  
            try{
                Statement st = connection.createStatement();
                int rowsAffected = st.executeUpdate(sqlQuery);
                return rowsAffected;
            }
            catch(Exception e){
                System.out.println(e);
                return 0;
            }

        };     
    
}

package src.main.java.com.bookmyshow.jdbc;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.bookmyshow.model.*;
public class VenueDAO {
    private Connection con;
    public VenueDAO(){
        con=DatabaseConnectionManager.getConnection();
    }
    public int pushDetailsVenue(Venue obj) throws ClassNotFoundException, IOException, SQLException{
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;
        int returnValue=0;
        try{
            if(obj.getNumhall()==obj.getListhall().size())
            {con=DatabaseConnectionManager.getConnection();
                pstmt = con.prepareStatement("INSERT INTO venue (ownerid,type,address,numhall,name) VALUES (?,?,?,?,?)");
                pstmt.setString(1, obj.getOwnerId());
                pstmt.setString(2, obj.getType().name());
                pstmt.setString(3, obj.getAddress());
                pstmt.setInt(4,obj.getNumhall() );
                pstmt.setString(5,obj.getName());
                int success= pstmt.executeUpdate();
                pstmt=con.prepareStatement("SELECT venueid FROM venue WHERE name= (?) ");
                pstmt.setString(1, obj.getName());
                resultSet = pstmt.executeQuery();
                resultSet.next();
                obj.setVenueId(resultSet.getInt(1));
                int successHall=pushDetailsHall(obj);
                if(successHall==0)
                {pstmt=con.prepareStatement("DELETE FROM venue WHERE name=(?) ");
                    pstmt.setString(1, obj.getName());
                    pstmt.executeUpdate();}
                if(success>0 && successHall==1)
                    returnValue= 1;
            }
            else{
                System.out.println("length of hall list and numbers of hall do not match");
            }
        }
        catch(Exception e){
            System.out.println("Exception in data insertion "+e);
        }
        finally{
            pstmt.close();
            resultSet.close();
            con.close();
        }
        return returnValue;
    }
    public int pushDetailsHall(Venue obj) throws ClassNotFoundException, IOException, SQLException{
        int success=1;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            con=DatabaseConnectionManager.getConnection();
            int hallCount=1;
            for(Hall hall : obj.getListhall()){
                hall.setHallName("AUDI-"+hallCount);
                hallCount++;

            }
            pstmt = con.prepareStatement("INSERT INTO hall (venueid,hallname,numseatsgold,numseatssilver,numseatsplatinum) VALUES (?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            for(Hall hall : obj.getListhall()){
                pstmt.setInt(1, obj.getVenueId());
                pstmt.setString(2, hall.getHallName());
                pstmt.setInt(3, hall.getNumSeatsGold());
                pstmt.setInt(4, hall.getNumSeatsSilver());
                pstmt.setInt(5, hall.getNumSeatsPlatinum());
                pstmt.addBatch();
            }
            int[] affectedRecords = pstmt.executeBatch();
            rs = pstmt.getGeneratedKeys();
            rs.next();
            for(Hall hall:obj.getListhall()){
                hall.setHallId(rs.getInt(1));
                rs.next();
            }

            for(int i=0;i<affectedRecords.length;i++)
            {
                if(affectedRecords[i]<1)
                    success=0;
            }
        }
        catch(Exception e){
            System.out.println("Exception in data insertion in hall table "+e);
        }
        finally{
            rs.close();
            pstmt.close();
            con.close();
        }
        return success;
    }

    public List<Venue> getAllVenuesForOwner(String ownerID){
        String sqlQuery = "SELECT * FROM Venue WHERE ownerID = ?";
        List<Venue> venues=new ArrayList<Venue>();
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setString(1, ownerID); // Set the ownerID parameter
            ResultSet rs = preparedStatement.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Venue v=new Venue();
                v.setOwnerId(rs.getString("venueID"));
                v.setVenueId(Integer.parseInt(rs.getString("venueID")));
                v.setName(rs.getString("name"));
                String typeString = rs.getString("type");
                Type type;
                switch (typeString) {
                    case "TYPE1":
                        type = Type.multiplex;
                        break;
                    case "TYPE2":
                        type = Type.theatre;
                        break;
                    case "TYPE3":
                        type = Type.facility;
                        break;
                    default:
                        type = Type.theatre;
                        break;
                }
                v.setType(type);
                v.setAddress(rs.getString("address"));
                v.setNumhall(Integer.parseInt(rs.getString("numhall")));
                venues.add(v);
            }
        }
        catch(Exception e){
            System.out.println(e);

        }
        return venues;
    }

}
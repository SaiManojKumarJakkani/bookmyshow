package src.main.java.com.bookmyshow.jdbc;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import src.main.java.com.bookmyshow.model.*;
public class VenueDAO {
    private Connection con;
    public VenueDAO(){
        con=DatabaseConnectionManager.getConnection();
    }
    public int pushDetailsVenue(Venue obj) throws ClassNotFoundException, IOException, SQLException{
        try{
            con=DatabaseConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO venue (ownerid,type,address,numhall,name) VALUES (?,?,?,?,?)");
            pstmt.setString(1, obj.getOwnerId());
            pstmt.setString(2, obj.getType().name());
            pstmt.setString(3, obj.getAddress());
            pstmt.setInt(4,obj.getNumhall() );
            pstmt.setString(5,obj.getName());
            int success= pstmt.executeUpdate();
            pstmt=con.prepareStatement("SELECT venueid FROM venue WHERE name= (?) ");
            pstmt.setString(1, obj.getName());
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            obj.setVenueId(resultSet.getInt(1));
            int successHall=pushDetailsHall(obj);
            if(successHall==0)
                {pstmt=con.prepareStatement("DELETE FROM venue WHERE name=(?) ");
                pstmt.setString(1, obj.getName());
                pstmt.executeUpdate();}

            pstmt.close();
            resultSet.close();
            con.close();
            
            if(success>0 && successHall==1)
            return 1;
            else return 0;
        }
        catch(Exception e){
            System.out.println(e);
            return 0;
        }
    }
    public int pushDetailsHall(Venue obj) throws ClassNotFoundException, IOException, SQLException{
        int success=1;
        try{
        con=DatabaseConnectionManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO hall (venueid,numseatsgold,numseatssilver,numseatsplatinum) VALUES (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
        for(Hall hall : obj.getListhall()){ 
            pstmt.setInt(1, obj.getVenueId());
            pstmt.setInt(2, hall.getNumSeatsGold());
            pstmt.setInt(3, hall.getNumSeatsSilver());
            pstmt.setInt(4, hall.getNumSeatsPlatinum());
            pstmt.addBatch();   
         }
        int[] affectedRecords = pstmt.executeBatch();
        ResultSet rs = pstmt.getGeneratedKeys();
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
        rs.close();
        pstmt.close();
        con.close();}
        catch(Exception e){
            System.out.println(e);
        }
        return success;

    }
}

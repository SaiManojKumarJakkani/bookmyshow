
package src.main.java.com.bookmyshow.model;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import src.main.java.com.bookmyshow.jdbc.VenueDAO;
import src.main.java.com.bookmyshow.service.*;

public class Venue {
    private String ownerId;
    private int venueId;
    private Type type;
    private String address;
    private String name;
    private List<Hall> listhall;
    // private List<Show> listShow; 
    private int numhall;

    public Venue() {
        this.ownerId = "";

        this.type = null;
        this.listhall = null;
        // this.listShow = null;
        this.address="";
        this.numhall=0;
        this.name="";
    }

    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    public int getVenueId() {
        return venueId;
    }
    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public List<Hall> getListhall() {
        return listhall;
    }
    public void setListhall(List<Hall> listhall) {
        this.listhall = listhall;
    }
    // public List<Show> getListShow() {
    //     return listShow;
    // }
    // public void setListShow(List<Show> listShow) {
    //     this.listShow = listShow;
    // }
    public int getNumhall() {
        return numhall;
    }

    public void setNumhall(int numhall) {
        this.numhall = numhall;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int addDetails(String ownerId)throws IOException, ClassNotFoundException, SQLException{
        int success=0;
        try{
            this.setOwnerId(ownerId);
            VenueDAO doa=new VenueDAO();
            success=doa.pushDetailsVenue(this);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return success;
    }
}
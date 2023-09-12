package src.main.java.com.bookmyshow.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import src.main.java.com.bookmyshow.model.*;

public class JASONParsing {

    public static Owner parseDataOwner(String filePath){
        Owner user=null;
        try{
            ObjectMapper om=new ObjectMapper();
            File jsonfile=new File(filePath);
            user=om.readValue(jsonfile,Owner.class);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return user;
    }

    public static Customer parseDataCustomer(String filePath){
        Customer user=null;
        try{
            ObjectMapper om=new ObjectMapper();
            File jsonfile=new File(filePath);
            user=om.readValue(jsonfile,Customer.class);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return user;
    }

    public static ShowDetails parseDataShowDetails(String filePath){
        ShowDetails showDetails =null;
        try{
            ObjectMapper om=new ObjectMapper();
            File jsonfile=new File(filePath);
            showDetails=om.readValue(jsonfile,ShowDetails.class);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return showDetails;
    }

    public static Booking parseBookingData(String filepath) {
        Booking booking = null;
        ObjectMapper objectmap = new ObjectMapper();//Create object of ObjectMapper
        try {
            File jsonFile = new File(filepath);//Create object of jsonfile
            booking= objectmap.readValue(jsonFile,Booking.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return booking;
    }
    public static Movie parseDataMovie(String filepath) {
        //movie = new ArrayList<>();
        ObjectMapper objectmap = new ObjectMapper();//Create object of ObjectMapper
        try {
            File jsonFile = new File(filepath);//Create object of jsonfile
            Movie movie= objectmap.readValue(jsonFile,Movie.class);

            return movie;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static Venue parseDataVenue(String filePath){
        Venue venue=null;
        try{
            ObjectMapper om=new ObjectMapper();
            File jsonfile=new File(filePath);
            venue=om.readValue(jsonfile,Venue.class);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return venue;
    }
}

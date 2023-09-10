package src.main.java.com.bookmyshow.service;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import src.main.java.com.bookmyshow.model.*;

public class JASONParsing {

    public static Object parseDataOwner(String filePath){
        User user=null;
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
}

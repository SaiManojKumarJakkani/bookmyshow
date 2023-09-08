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

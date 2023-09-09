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
    public static Object parseDataVenue(String filePath){
        return null;
    }
    public static Object parseDataShowDetails(String filePath){
        return null;
    }
    public static Object parseDataMovie(String filePath){
        return null;
    }
    
}

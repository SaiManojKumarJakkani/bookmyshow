package src.main.java.com.bookmyshow.service;

import java.io.FileReader;

import com.google.gson.Gson;

import src.main.java.com.bookmyshow.model.*;

public class JASONParsing {

    public static Object parseDataOwner(String filePath){
        User user=null;
        Gson gson = new Gson();      
        try{
            FileReader reader = new FileReader(filePath);
            user = gson.fromJson(reader, Owner.class);
            reader.close();
        }
        catch(Exception e){
            System.out.println(e);
        }     
        
        return user;
    }
    
}

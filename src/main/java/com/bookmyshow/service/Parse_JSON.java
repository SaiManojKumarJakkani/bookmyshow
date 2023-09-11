package org.example;
//package src.main.java.com.bookmyshow.service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
//import src.main.java.com.bookmyshow.model.*;


public class Parse_JSON {
    public static Booking parseBookingData(String filepath) {
      //  bookingList = new ArrayList<>();
        ObjectMapper objectmap = new ObjectMapper();//Create object of ObjectMapper
        try {
            File jsonFile = new File(filepath);//Create object of jsonfile
            Booking booking= objectmap.readValue(jsonFile,Booking.class);
            return booking;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
    public static Movies parseMoviesData(String filepath) {
        //movie = new ArrayList<>();
        ObjectMapper objectmap = new ObjectMapper();//Create object of ObjectMapper
        try {
            File jsonFile = new File(filepath);//Create object of jsonfile
            Movies movie= objectmap.readValue(jsonFile,Movies.class);

            return movie;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}

package org.example;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import org.example.Movie;
//import src.main.java.com.bookmyshow.jdbc.*;
//import src.main.java.com.bookmyshow.model.*;
import org.example.Parse_JSON;
public class BMSMainApp {
    public  static void addBookingMethod() throws SQLException {
        String filePath = "src/main/java/org/example/Input_Booking.json";
        Booking custbooking1= Parse_JSON.parseBookingData(filePath);

        BookingDAO bookingDAO= new BookingDAO();
        Booking booking = new Booking();// booking object
        booking.setBookingID(custbooking1.getBookingID());
        booking.setCustID(custbooking1.getCustID());
        booking.setVenueID(custbooking1.getVenueID());
        booking.setShowID(custbooking1.getShowID());
        booking.setBookingDate(custbooking1.getBookingDate());
        booking.setNumSeatsBooked(custbooking1.getNumSeatsBooked());
        booking.setBankDetailsCust(custbooking1.getBankDetailsCust());
        booking.setPricingTierChosen(custbooking1.getPricingTierChosen());
        booking.setTransactionStatus(custbooking1.getTransactionStatus());
        bookingDAO.insertBooking(booking);
    }
    public static void addMovieDetails() throws SQLException {

        String filePath = "src/main/java/org/example/Input_Movie.json";
        Movie newmovie = Parse_JSON.parseMoviesData(filePath);
        MovieDAO movieDAO = new MovieDAO();
        Movie movie = new Movie();
        movie.setMovieName(newmovie.getMovieName());
        movie.setGenre(newmovie.getGenre());
        movie.setActor(newmovie.getActor());
        movie.setActress(newmovie.getActress());
        movie.setProducer(newmovie.getProducer());
        movie.setDirector(newmovie.getDirector());
        movie.setLanguage(newmovie.getLanguage());
        movie.setRating(newmovie.getRating());
        MovieDAO.insertNewMovie(movie);
        System.out.println("----------");
        System.out.println("Movie name : " + movie.getMovieName());
        System.out.println("Genre : " + movie.getGenre());
        System.out.println("Actor : " + movie.getActor());
        System.out.println("Actress : " + movie.getActress());
        System.out.println("Producer : " + movie.getProducer());
        System.out.println("Director : " + movie.getDirector());
        System.out.println("language : " + movie.getLanguage());
        System.out.println("rating : " + movie.getRating());
        System.out.println("----------");

    }
    public static void main(String[] args) throws Exception {
        //Insert new movie for owner
        // Print existing movies
        // Make booking method-
        System.out.println("which details you want to know (Movie/Booking):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("Movie")) {
            System.out.println("1. Insert new movie into database or 2. print current movies ?");
            input= scanner.nextLine();
            if (input.equalsIgnoreCase("1")) {
                addMovieDetails();
                System.out.println("new movie inserted");
            } else {
                System.out.println("Print data from database");
                MovieDAO.getMovieDetails();
            }
        }
        else if(input.equalsIgnoreCase("Booking")) {
            System.out.println("1. Insert new booking into database or 2. print current bookings ?");
            input= scanner.nextLine();
            //Check if the table exists or not. If not create table movie with the data given
            if (input.equalsIgnoreCase("1")) {
                addBookingMethod();
                System.out.println("Booking inserted ");
            } else if (input.equalsIgnoreCase("2")) {
                BookingDAO.getAllBookingDetails();
            }
        }
        else {
            System.out.println("No option entered");
        }

    }

}

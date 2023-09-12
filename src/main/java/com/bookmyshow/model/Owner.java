package src.main.java.com.bookmyshow.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import src.main.java.com.bookmyshow.jdbc.OwnerDAO;
import src.main.java.com.bookmyshow.service.OwnerService;

public class Owner extends User {
    private String bankAccNo;
    private List<Venue> venues;

    public Owner(){
        super();
        this.venues = new ArrayList<>();
    }

    public Owner(String userID, String password,String firstName, String lastName, String emailID, String bankAccNo){
        super(userID,password, firstName, lastName,emailID);
        this.bankAccNo=bankAccNo;
        this.venues = new ArrayList<>();
    }

    public List<Venue> getVenues() {
        return venues;
    }

    // Setter method for listOfVenues
    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public User login(User inputUser){
        OwnerDAO ownerDAO = new OwnerDAO();
        User dbUser=ownerDAO.getOwner(inputUser.getUserID());
        boolean result=OwnerService.loginService(inputUser,dbUser );
        if(result){return dbUser; }
        else{ return null;}

    }

    public int signUp( User inputUser){
        return OwnerService.signUpService(inputUser);
    }

    public void addVenueDetails( Venue venue){
        int result=0;
        try{
            result = venue.addDetails(this.getUserID());
        }
        catch(Exception e){System.out.println(e+" Adding venue details had failed!");}
        if(result ==1)
        {this.venues.add(venue);}
        else{ System.out.println("Error while adding Venue. Please try again");}
    }

    public void  addShowDetails(ShowDetails show){
        show.addShow();
    }

    public void  addMovie(Movie movie) throws SQLException {
        movie.addMovie();
    }
    
    public void getThreeDaysBookingDetails(){
        try{
            List<Integer> venueIDs = venues.stream().map(Venue::getVenueId).collect(Collectors.toList());
            if(venueIDs.size()!=0){OwnerService.getThreeDaysBookingDetailsService(venueIDs);}
            else{System.out.println("You have no venues!");}

        }
        catch(Exception e){
            System.out.println("You have no venues!");
        }
        
    }

    public void getSevenDaysBookingDetails(){
        try{
            List<Integer> venueIDs = venues.stream().map(Venue::getVenueId).collect(Collectors.toList());
            if(venueIDs.size()!=0){OwnerService.getSevenDaysBookingDetailsService(venueIDs);}
            else{System.out.println("You have no venues!");}

        }
        catch(Exception e){
            System.out.println("You have no venues!");
        }
        
    }
    


    public void getListOfRevenuePerDay(){
        try{
            List<Integer> venueIDs = venues.stream().map(Venue::getVenueId).collect(Collectors.toList());
            if(venueIDs.size()!=0){OwnerService.getListOfRevenuePerDayService(venueIDs);}
            else{System.out.println("You have no venues!");}

        }
        catch(Exception e){
            System.out.println("You have no venues!");
        }
    }
    
    public void getListOfRevenuePerMovie(){
        try{
            List<Integer> venueIDs = venues.stream().map(Venue::getVenueId).collect(Collectors.toList());
            if(venueIDs.size()!=0){OwnerService.getListOfRevenuePerMovieService(venueIDs);}
            else{System.out.println("You have no venues!");}
        }
        catch(Exception e){
            System.out.println("You have no venues!");
        }
    }
    
    public void getListOfShowsByRevenue(){
        try{
            List<Integer> venueIDs = venues.stream().map(Venue::getVenueId).collect(Collectors.toList());
            if(venueIDs.size()!=0){OwnerService.getListOfShowsByRevenueService(venueIDs );}
            else{System.out.println("You have no venues!");}

        }
        catch(Exception e){
            System.out.println("You have no venues!");
        }

    }

    public void getListOfShowsDate(LocalDate date){
        try{
            List<Integer> venueIDs = venues.stream().map(Venue::getVenueId).collect(Collectors.toList());
            if(venueIDs.size()!=0){OwnerService.getListOfShowsDateService(venueIDs,date);}
            else{System.out.println("You have no venues!");}

        }
        catch(Exception e){
            System.out.println("You have no venues!");
        }
    
    }

    public void getListOfShowsForMovieDateRange(LocalDate startDate,LocalDate endDate,String movieName ){

        try{
            List<Integer> venueIDs = venues.stream().map(Venue::getVenueId).collect(Collectors.toList());
            if(venueIDs.size()!=0){OwnerService.getListOfRevenuePerMovieDateRangeService(venueIDs, startDate, endDate, movieName);}
            else{System.out.println("You have no venues!");}

        }
        catch(Exception e){
            System.out.println("You have no venues!");
        }

    }

    @Override
    public String toString() {
        return super.toString() + "\nBank Account Number: " + bankAccNo;
    }


    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

}
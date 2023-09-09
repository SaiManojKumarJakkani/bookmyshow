package src.main.java.com.bookmyshow.model;

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
    private List<Venue> listOfVenues;

    public Owner(){
        super();
    }

    public Owner(String userID, String password,String firstName, String lastName, String emailID, String bankAccNo){
        super(userID,password, firstName, lastName,emailID);
        this.bankAccNo=bankAccNo;
    }

    public List<Venue> getListOfVenues() {
        return listOfVenues;
    }

    // Setter method for listOfVenues
    public void setListOfVenues(List<Venue> listOfVenues) {
        this.listOfVenues = listOfVenues;
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
        catch(Exception e){System.out.println(e);}
        if(result ==1)
            {this.listOfVenues.add(venue);}
        else{ System.out.println("Error while adding Venue. Please try again");}
    }  
    
    public void  addShowDetails(ShowDetails show){
        show.addShow();
    }
    
    public void  addMovie(Movie movie){
        new Movie().addMovie(movie);
    }
    
    public void getThreeDaysBookingDetails(){
        List<Integer> listOfVenueIDs = listOfVenues.stream().map(Venue::getVenueId).collect(Collectors.toList());
        OwnerService.getThreeDaysBookingDetailsService(listOfVenueIDs);
        
    }

    public void getSevenDaysBookingDetails(){

        List<Integer> listOfVenueIDs = listOfVenues.stream().map(Venue::getVenueId).collect(Collectors.toList());

        OwnerService.getSevenDaysBookingDetailsService(listOfVenueIDs);
        
    }
    


    public void getListOfRevenuePerDay(){

        List<Integer> listOfVenueIDs = listOfVenues.stream().map(Venue::getVenueId).collect(Collectors.toList());
        OwnerService.getListOfRevenuePerDayService(listOfVenueIDs);
    }
    
    public void getListOfRevenuePerMovie(){
        List<Integer> listOfVenueIDs = listOfVenues.stream().map(Venue::getVenueId).collect(Collectors.toList());

        OwnerService.getListOfRevenuePerMovieService(listOfVenueIDs);
    }
    
    public void getListOfShowsByRevenue(){
        List<Integer> listOfVenueIDs = listOfVenues.stream().map(Venue::getVenueId).collect(Collectors.toList());
        OwnerService.getListOfShowsByRevenueService(listOfVenueIDs );

    }

    public void getListOfShowsDate(LocalDate date){
        List<Integer> listOfVenueIDs = listOfVenues.stream().map(Venue::getVenueId).collect(Collectors.toList());


        OwnerService.getListOfShowsDateService(listOfVenueIDs,date);
    
    }

    public void getListOfShowsForMovieDateRange(LocalDate startDate,LocalDate endDate,String movieName ){


        List<Integer> listOfVenueIDs = listOfVenues.stream().map(Venue::getVenueId).collect(Collectors.toList());

        OwnerService.getListOfRevenuePerMovieDateRangeService(listOfVenueIDs, startDate, endDate, movieName);
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

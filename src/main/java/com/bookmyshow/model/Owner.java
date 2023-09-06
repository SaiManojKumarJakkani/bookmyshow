package src.main.java.com.bookmyshow.model;

import java.time.LocalDate;
import java.util.ArrayList;
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
        Venue createdVenue= new Venue().addDetails(this.getUserID(), venue);
        this.listOfVenues.add(createdVenue);

    }  
    
    public void  addShowDetails(ShowDetails show){
        ShowDetails createdShow= new ShowDetails().addShow(show);
        for(Venue v:listOfVenues){
            if(v.getVenueID().equals(createdShow.getVenueID())){
                v.addShow(createdShow);
                break;
            }
        }
    }
    
    public void  addMovie(Movie movie){
        new Movie().addMovie(movie);
    }
    
    public void getThreeDaysBookingDetails(){

        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());

        OwnerService.getThreeDaysBookingDetailsService(listOfVenueIDs, mapOfVenueIDName);
        
    }

    public void getSevenDaysBookingDetails(){

        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());

        OwnerService.getSevenDaysBookingDetailsService(listOfVenueIDs, mapOfVenueIDName);
        
    }
    
    public void getListOfRevenuePerDay(){
        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());
        OwnerService.getListOfRevenuePerDayService(listOfVenueIDs);
    }
    
    public void getListOfRevenuePerMovie(){
        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());
        OwnerService.getListOfRevenuePerMovieService(listOfVenueIDs);
    }
    
    public void getListOfShowsByRevenue(){
        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());
        OwnerService.getListOfShowsByRevenueService(listOfVenueIDs,mapOfVenueIDName );

    }

    public void getListOfShowsDate(LocalDate date){
        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());
        OwnerService.getListOfShowsDateService(listOfVenueIDs,mapOfVenueIDName,date);
    

    }

    public void getListOfShowsForMovieDateRange(LocalDate startDate,LocalDate endDate,String movieName ){

        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());

        OwnerService.getListOfRevenuePerMovieDateRangeService(listOfVenueIDs, mapOfVenueIDName, startDate, endDate, movieName);
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

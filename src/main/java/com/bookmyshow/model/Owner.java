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


    public void addVenueDetails(){
        Venue createdVenue= new Venue().addDetails(this.getUserID());
        this.listOfVenues.add(createdVenue);

    }
    
    public void  addShowDetails(){
        Shows createdShow= new Shows().addShow();
        for(Venue v:listOfVenues){
            if(v.getVenueID().equals(createdShow.getVenueID())){
                v.addShow(createdShow);
                break;
            }
        }
    }
    
    public void getThreeDaysBookingDetails(){

        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());

        OwnerService.getThreeDaysBookingDetailsService(listOfVenueIDs, mapOfVenueIDName);
        
    }

    public void getSevenDaysHistory(){

        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());

        OwnerService.getThreeDaysBookingDetailsService(listOfVenueIDs, mapOfVenueIDName);
        
    }
    public void getListOfShows(LocalDate date){
        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());
        OwnerService.getListOfShowsService(listOfVenueIDs,mapOfVenueIDName,date);
    

    }



    public void getListOfShowsForMovie(LocalDate startDate,LocalDate endDate,String movieName ){

        Map<String, String> mapOfVenueIDName = listOfVenues.stream().collect(Collectors.toMap(Venue::getVenueID, Venue::getName));

        List<String> listOfVenueIDs = new ArrayList<>(mapOfVenueIDName.keySet());

        OwnerService.getListOfShowsForMovieService(listOfVenueIDs, mapOfVenueIDName, startDate, endDate, movieName);
    }


    public void getListOfShowsByRevenue(){
        






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

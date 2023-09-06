package src.main.java.com.bookmyshow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import src.main.java.com.bookmyshow.jdbc.MovieDAO;
import src.main.java.com.bookmyshow.jdbc.OwnerDAO;
import src.main.java.com.bookmyshow.jdbc.ShowDAO;
import src.main.java.com.bookmyshow.model.Movie;
import src.main.java.com.bookmyshow.model.Owner;
import src.main.java.com.bookmyshow.model.Shows;
import src.main.java.com.bookmyshow.model.User;

public class OwnerService {

    public static boolean loginService(User inputUser,User dbUser ){
        if(dbUser==null){return false;}
        else{
            if(dbUser.getPassword().equals(inputUser.getPassword())){return true;}
            else{return false;}
        }
    }

    public static int  signUpService(User inputUser){
        OwnerDAO ownerDAO = new OwnerDAO();
        User dbUser=ownerDAO.getOwner(inputUser.getUserID());
        if(dbUser==null){
            int result = ownerDAO.insertOwner((Owner)inputUser);
            return result;
        }
        else{
            return -2;
        }
    }
    

    public static void  getThreeDaysBookingDetailsService(List<String> listOfVenueIDs, Map<String, String> mapOfVenueIDName){
        System.out.println("Booking status for the next 3 days:");
        ShowDAO showDAO=new ShowDAO();
        List<Shows> listOfShows= showDAO.getShowDetailsNextThreeDays(listOfVenueIDs );

        String currentVenue =listOfShows.get(0).getVenueID();
        LocalDate showDate=listOfShows.get(0).getShowDate();


        int i=0;

        while(i<listOfShows.size()){
            int j=i;
            System.out.println("DATE: "+showDate);
            while(j<listOfShows.size() && showDate.equals(listOfShows.get(j).getShowDate()) ){
                int k=j;
                System.out.println("VENUE NAME: "+mapOfVenueIDName.get(currentVenue));
                System.out.println("Show_timing \t Hall_Name \t Movie_Name \t No_of_seats_booked/Total_seats");
                while(k<listOfShows.size() && currentVenue.equals(listOfShows.get(k).getVenueID()) && showDate.equals(listOfShows.get(k).getShowDate()) ){
                    System.out.println(listOfShows.get(k).getTiming()+"\t"+listOfShows.get(k).getHallID()+"\t"+listOfShows.get(k).getNameOfShow()+"\t"+ listOfShows.get(k).getNumOfSeatsBooked()+"/"+listOfShows.get(k).getNumOfTotalSeats());
                    k=k+1;
                }
                j=k;
                if(k==listOfShows.size() ){break;}
                currentVenue=listOfShows.get(j).getVenueID();
                System.out.println();
            }
            i=j;
            if(j==listOfShows.size() ){break;}
            showDate=listOfShows.get(i).getShowDate();
            currentVenue =listOfShows.get(i).getVenueID();
            System.out.println(showDate+":"+i);
            System.out.println("--------------------------------------------");
            // System.exit(0);

        }

    }
    

    public static void  getListOfShowsService(List<String> listOfVenueIDs, Map<String, String> mapOfVenueIDName,LocalDate date){
        System.out.println("list of shows on "+date+" date:");
        ShowDAO showDAO=new ShowDAO();
        List<Shows> listOfShows= showDAO.getShowDetailsOnDate(listOfVenueIDs,date );

        String currentVenue =listOfShows.get(0).getVenueID();

        int j=0;
        while(j<listOfShows.size()){
                int k=j;
                System.out.println("VENUE NAME: "+mapOfVenueIDName.get(currentVenue));
                System.out.println("Show_timing \t Hall_Name \t Movie_Name \t Unsold_seats");
                while(k<listOfShows.size() && currentVenue.equals(listOfShows.get(k).getVenueID())){
                    System.out.println(listOfShows.get(k).getTiming()+" \t "+listOfShows.get(k).getHallID()+" \t "+listOfShows.get(k).getNameOfShow()+" \t "+(listOfShows.get(k).getNumOfTotalSeats()-listOfShows.get(k).getNumOfSeatsBooked()));
                    k=k+1;
                }
                j=k;
                if(k==listOfShows.size() ){break;}
                currentVenue=listOfShows.get(j).getVenueID();
                System.out.println();
        }
    }
    

    public static void  getListOfShowsForMovieService(List<String> listOfVenueIDs, Map<String, String> mapOfVenueIDName,LocalDate startDate,LocalDate endDate,String movieName){
        System.out.println("List of all shows for "+movieName+" movie playing between "+startDate+" to"+endDate);
        MovieDAO movieDAO=new MovieDAO();
        ShowDAO showDAO=new ShowDAO();
        Movie movie=movieDAO.getMovieDetails(movieName);

        List<Shows> listOfShows= showDAO.getShowDetailsMovie(listOfVenueIDs,startDate, endDate, movie);

        String currentVenue =listOfShows.get(0).getVenueID();
        int j=0;
        while(j<listOfShows.size()){
            int k=j;
            System.out.println("VENUE NAME: "+mapOfVenueIDName.get(currentVenue));
            System.out.println("Show_timing \t Hall_Name  \t Unsold_seats");
            while(k<listOfShows.size() && currentVenue.equals(listOfShows.get(k).getVenueID())){
                System.out.println(listOfShows.get(k).getTiming()+" \t "+listOfShows.get(k).getHallID()+" \t "+(listOfShows.get(k).getNumOfTotalSeats()-listOfShows.get(k).getNumOfSeatsBooked()));
                k=k+1;
            }
            j=k;
            if(k==listOfShows.size() ){break;}
            currentVenue=listOfShows.get(j).getVenueID();
            System.out.println();
        }

    }

    

    public static void  getListOfShowsByRevenueService(List<String> listOfVenueIDs ){
        ShowDAO showDAO=new ShowDAO();
        List<List<Object>> listOfShows= showDAO.getShowDetailsRevenue(listOfVenueIDs);




    }

}

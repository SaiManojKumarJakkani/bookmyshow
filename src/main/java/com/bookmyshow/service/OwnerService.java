package src.main.java.com.bookmyshow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import src.main.java.com.bookmyshow.jdbc.MovieDAO;
import src.main.java.com.bookmyshow.jdbc.OwnerDAO;
import src.main.java.com.bookmyshow.jdbc.ShowDetailsDAO;
import src.main.java.com.bookmyshow.model.Movie;
import src.main.java.com.bookmyshow.model.Owner;
import src.main.java.com.bookmyshow.model.ShowDetails;
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
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<ShowDetails> listOfShows= showDAO.getShowDetailsNextThreeDays(listOfVenueIDs );

        if(listOfShows.size()==0){
            System.out.println("No Booking status for the next 3 days!");
        }
        else{
            System.out.println("Booking status for the next 3 days:");
            String currentVenue =listOfShows.get(0).getVenueID();
            LocalDate showDate=listOfShows.get(0).getShowDate();
            int i=0;
            while(i<listOfShows.size()){
                int j=i;
                System.out.println("DATE: "+showDate);
                while(j<listOfShows.size() && showDate.equals(listOfShows.get(j).getShowDate()) ){
                    int k=j;
                    System.out.println("VENUE NAME: "+mapOfVenueIDName.get(currentVenue));
                    System.out.println("Show_time \t Hall_Name \t Movie_Name \t No_of_seats_booked/Total_seats");
                    while(k<listOfShows.size() && currentVenue.equals(listOfShows.get(k).getVenueID()) && showDate.equals(listOfShows.get(k).getShowDate()) ){
                        System.out.println(listOfShows.get(k).getStartTime()+" \t "+listOfShows.get(k).getHallID()+" \t "+listOfShows.get(k).getNameOfShow()+" \t "+ listOfShows.get(k).getNumOfSeatsBooked()+"/"+listOfShows.get(k).getNumOfTotalSeats());
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
                System.out.println("--------------------------------------------");

            }
        }

    }
    
    public static void  getSevenDaysBookingDetailsService(List<String> listOfVenueIDs, Map<String, String> mapOfVenueIDName){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> listOfShows= showDAO.getShowDetailsPastSevenDays(listOfVenueIDs );
        if(listOfShows.size()==0){
            System.out.println("No booking history for the past 7 days!");
        }
        else{
            System.out.println("Booking history for the past 7 days:");
            String currentVenue =((ShowDetails)listOfShows.get(0).get(0)).getVenueID();
            LocalDate showDate=((ShowDetails)listOfShows.get(0).get(0)).getShowDate();
            int i=0;
            while(i<listOfShows.size()){
                int j=i;
                System.out.println("DATE: "+showDate);
                while(j<listOfShows.size() && showDate.equals(((ShowDetails)listOfShows.get(j).get(0)).getShowDate()) ){
                    int k=j;
                    System.out.println("VENUE NAME: "+mapOfVenueIDName.get(currentVenue));
                    System.out.println("Show_time \t Hall_Name \t Movie_Name \t No_of_seats_booked/Total_seats \t Revenue");
                    while(k<listOfShows.size() && currentVenue.equals(((ShowDetails)listOfShows.get(k).get(0)).getVenueID()) && showDate.equals(((ShowDetails)listOfShows.get(k).get(0)).getShowDate()) ){
                        System.out.println(((ShowDetails)listOfShows.get(k).get(0)).getStartTime()+" \t "+((ShowDetails)listOfShows.get(k).get(0)).getHallID()+" \t "+((ShowDetails)listOfShows.get(0).get(0)).getNameOfShow()+" \t "+ ((ShowDetails)listOfShows.get(k).get(0)).getNumOfSeatsBooked()+"/"+((ShowDetails)listOfShows.get(k).get(0)).getNumOfTotalSeats()+
                        " \t "+((String)listOfShows.get(k).get(1)));
                        k=k+1;
                    }
                    j=k;
                    if(k==listOfShows.size() ){break;}
                    currentVenue=((ShowDetails)listOfShows.get(j).get(0)).getVenueID();
                    System.out.println();
                }
                i=j;
                if(j==listOfShows.size() ){break;}
                showDate=((ShowDetails)listOfShows.get(i).get(0)).getShowDate();
                currentVenue =((ShowDetails)listOfShows.get(i).get(0)).getVenueID();

                System.out.println("--------------------------------------------");
                // System.exit(0);
            }
        }
    }

    public static void  getListOfShowsDateService(List<String> listOfVenueIDs, Map<String, String> mapOfVenueIDName,LocalDate date){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<ShowDetails> listOfShows= showDAO.getShowDetailsOnDate(listOfVenueIDs,date );
        if(listOfShows.size()==0){
            System.out.println("No shows are playing on "+date+" date!");
        }
        else{
            String currentVenue =listOfShows.get(0).getVenueID();
            System.out.println("List shows playing on "+date+" date:");
            int j=0;
            while(j<listOfShows.size()){
                    int k=j;
                    System.out.println("VENUE NAME: "+mapOfVenueIDName.get(currentVenue));
                    System.out.println("Show_time \t Hall_Name \t Movie_Name \t Unsold_seats");
                    while(k<listOfShows.size() && currentVenue.equals(listOfShows.get(k).getVenueID())){
                        System.out.println(listOfShows.get(k).getStartTime()+" \t "+listOfShows.get(k).getHallID()+" \t "+listOfShows.get(k).getNameOfShow()+" \t "+(listOfShows.get(k).getNumOfTotalSeats()-listOfShows.get(k).getNumOfSeatsBooked()));
                        k=k+1;
                    }
                    j=k;
                    if(k==listOfShows.size() ){break;}
                    currentVenue=listOfShows.get(j).getVenueID();
                    System.out.println();
            }
        }
    }
    
    public static void  getListOfRevenuePerMovieDateRangeService(List<String> listOfVenueIDs, Map<String, String> mapOfVenueIDName,LocalDate startDate,LocalDate endDate,String movieName){
        MovieDAO movieDAO=new MovieDAO();
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        Movie movie=movieDAO.getMovieDetails(movieName);

        if(movie==null){
            System.out.println("Movie name doesnot exists!");

        }
        else{
            List<ShowDetails> listOfShows= showDAO.getShowDetailsMovie(listOfVenueIDs,startDate, endDate, movie);
            if(listOfShows.size()==0){
                System.out.println("No shows are playing for "+movieName+" movie playing between "+startDate+" to"+endDate+"!");
            }
            else{
                System.out.println("List of all shows for "+movieName+" movie playing between "+startDate+" to"+endDate+":");
                String currentVenue =listOfShows.get(0).getVenueID();
                int j=0;
                while(j<listOfShows.size()){
                    int k=j;
                    System.out.println("VENUE NAME: "+mapOfVenueIDName.get(currentVenue));
                    System.out.println("Show_time \t Hall_Name  \t Unsold_seats");
                    while(k<listOfShows.size() && currentVenue.equals(listOfShows.get(k).getVenueID())){
                        System.out.println(listOfShows.get(k).getStartTime()+" \t "+listOfShows.get(k).getHallID()+" \t "+(listOfShows.get(k).getNumOfTotalSeats()-listOfShows.get(k).getNumOfSeatsBooked()));
                        k=k+1;
                    }
                    j=k;
                    if(k==listOfShows.size() ){break;}
                    currentVenue=listOfShows.get(j).getVenueID();
                    System.out.println();
                }
            }
        }

    }

    public static void  getListOfShowsByRevenueService(List<String> listOfVenueIDs, Map<String, String> mapOfVenueIDName ){
        System.out.println("List of shows sorted by the revenue generated in ascending order:");
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> listOfShows= showDAO.getShowDetailsRevenue(listOfVenueIDs);
        if(listOfShows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("List of shows sorted by the revenue generated in ascending order:");
            int j=0;
            System.out.println("Show_Name \t Venue_Name \t Hall_Name \t Date \t Show_time \t Revenue");

            while(j<listOfShows.size()){
                System.out.println(((ShowDetails)listOfShows.get(j).get(0)).getNameOfShow()+" \t "+mapOfVenueIDName.get(((ShowDetails)listOfShows.get(j).get(0)).getVenueID())+" \t "+((ShowDetails)listOfShows.get(j).get(0)).getHallID()+" \t "+((ShowDetails)listOfShows.get(j).get(0)).getShowDate()+
                " \t "+((ShowDetails)listOfShows.get(j).get(0)).getStartTime()+" \t "+ ((String)listOfShows.get(j).get(1)));
                j=j+1;
            }
        }



    }

    public static void  getListOfRevenuePerDayService(List<String> listOfVenueIDs ){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<String>> listOfShows= showDAO.getRevenuePerDay(listOfVenueIDs);
        if(listOfShows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("Breakdown of revenue generated per day:");
            int j=0;
            System.out.println("Date \t Revenue per day");

            while(j<listOfShows.size()){
                System.out.println(listOfShows.get(j).get(0)+" \t "+listOfShows.get(j).get(1));
                j=j+1;
            }
        }

    }

    public static void  getListOfRevenuePerMovieService(List<String> listOfVenueIDs ){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<String>> listOfShows= showDAO.getRevenuePerMovie(listOfVenueIDs);
        if(listOfShows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("Breakdown of revenue generated per movie:");
            int j=0;
            System.out.println("Movie_Name \t Revenue per movie");

            while(j<listOfShows.size()){
                System.out.println(listOfShows.get(j).get(0)+" \t "+listOfShows.get(j).get(1));
                j=j+1;
            }
        }

    }

}

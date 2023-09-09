package src.main.java.com.bookmyshow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import src.main.java.com.bookmyshow.jdbc.MovieDAO;
import src.main.java.com.bookmyshow.jdbc.OwnerDAO;
import src.main.java.com.bookmyshow.jdbc.ShowDetailsDAO;
import src.main.java.com.bookmyshow.model.Hall;
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

    public static void  getThreeDaysBookingDetailsService(List<Integer> listOfVenueIDs){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> listOfShows= showDAO.getShowDetailsNextThreeDays(listOfVenueIDs );

        if(listOfShows.size()==0){
            System.out.println("No Booking status for the next 3 days!");
        }
        else{
            System.out.println("Booking status for the next 3 days:\n");
            String currentVenue =((String)listOfShows.get(0).get(1));
            Date showDate=((ShowDetails)listOfShows.get(0).get(0)).getShowDate();

            int i=0;
            while(i<listOfShows.size()){
                int j=i;
                System.out.println("DATE: "+showDate);
                while(j<listOfShows.size() && showDate.equals(((ShowDetails)listOfShows.get(j).get(0)).getShowDate()) ){
                    int k=j;
                    System.out.println("VENUE NAME: "+currentVenue);
                    System.out.println("Show_time | Hall_Name | Movie_Name | No_of_silver_seats_booked/Total_silver_seats | No_of_gold_seats_booked/Total_gold_seats | No_of_platinum_seats_booked/Total_platinum_seats");
                    while(k<listOfShows.size() && currentVenue.equals(((String)listOfShows.get(k).get(1))) && showDate.equals(((ShowDetails)listOfShows.get(k).get(0)).getShowDate()) ){
                        System.out.println(((ShowDetails)listOfShows.get(k).get(0)).getStartTime()+" | "+((Hall)listOfShows.get(k).get(2)).getHallName()+" | "+((ShowDetails)listOfShows.get(k).get(0)).getMovieName()
                        +" | "+ ((ShowDetails)listOfShows.get(k).get(0)).getNumSilverBooked()+"/"+((Hall)listOfShows.get(k).get(2)).getNumSeatsSilver()
                        +" | "+ ((ShowDetails)listOfShows.get(k).get(0)).getNumGoldBooked()+"/"+((Hall)listOfShows.get(k).get(2)).getNumSeatsGold()
                        +" | "+ ((ShowDetails)listOfShows.get(k).get(0)).getNumPlatinumBooked()+"/"+((Hall)listOfShows.get(k).get(2)).getNumSeatsPlatinum());
                        k=k+1;
                    }
                    j=k;
                    if(k==listOfShows.size() ){break;}
                    currentVenue=((String)listOfShows.get(j).get(1));
                    System.out.println();
                }
                i=j;
                if(j==listOfShows.size() ){break;}
                showDate=((ShowDetails)listOfShows.get(i).get(0)).getShowDate();
                currentVenue =((String)listOfShows.get(i).get(1));
                System.out.println("--------------------------------------------");

            }
        }

    }
    
    public static void  getSevenDaysBookingDetailsService(List<Integer> listOfVenueIDs){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> listOfShows= showDAO.getShowDetailsPastSevenDays(listOfVenueIDs );
        if(listOfShows.size()==0){
            System.out.println("No booking history for the past 7 days!");
        }
        else{
            System.out.println("Booking history for the past 7 days:");
            String currentVenue =((String)listOfShows.get(0).get(1));
            Date showDate=((ShowDetails)listOfShows.get(0).get(0)).getShowDate();

            int i=0;
            while(i<listOfShows.size()){
                int j=i;
                System.out.println("DATE: "+showDate);
                while(j<listOfShows.size() && showDate.equals(((ShowDetails)listOfShows.get(j).get(0)).getShowDate()) ){
                    int k=j;
                    System.out.println("VENUE NAME: "+currentVenue);
                    System.out.println("Show_time | Hall_Name | Movie_Name | No_of_seats_booked/Total_seats | Revenue");
                    while(k<listOfShows.size() && currentVenue.equals(((String)listOfShows.get(k).get(1))) && showDate.equals(((ShowDetails)listOfShows.get(k).get(0)).getShowDate()) ){
                        System.out.println(((ShowDetails)listOfShows.get(k).get(0)).getStartTime()
                        +" | "+((Hall)listOfShows.get(k).get(2)).getHallName()
                        +" | "+((ShowDetails)listOfShows.get(k).get(0)).getMovieName()
                        +" | "+(((ShowDetails)listOfShows.get(k).get(0)).getNumSilverBooked()+((ShowDetails)listOfShows.get(k).get(0)).getNumGoldBooked()+((ShowDetails)listOfShows.get(k).get(0)).getNumPlatinumBooked()) 
                        +"/"+(((Hall)listOfShows.get(k).get(2)).getNumSeatsSilver()+((Hall)listOfShows.get(k).get(2)).getNumSeatsGold()+((Hall)listOfShows.get(k).get(2)).getNumSeatsPlatinum())
                        +" | "+((String)listOfShows.get(k).get(3)));
                        k=k+1;
                    }
                    j=k;
                    if(k==listOfShows.size() ){break;}
                    currentVenue=((String)listOfShows.get(j).get(1));
                    System.out.println();
                }
                i=j;
                if(j==listOfShows.size() ){break;}
                showDate=((ShowDetails)listOfShows.get(i).get(0)).getShowDate();
                currentVenue =((String)listOfShows.get(i).get(1));

                System.out.println("--------------------------------------------");
                // System.exit(0);
            }
        }
    }

    public static void  getListOfRevenuePerDayService(List<Integer> listOfVenueIDs ){

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

    public static void  getListOfRevenuePerMovieService(List<Integer> listOfVenueIDs ){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<String>> listOfShows= showDAO.getRevenuePerMovie(listOfVenueIDs);
        if(listOfShows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("Breakdown of revenue generated per movie:");
            int j=0;
            System.out.println("Movie_Name | Revenue per movie");

            while(j<listOfShows.size()){
                System.out.println(listOfShows.get(j).get(0)+" | "+listOfShows.get(j).get(1));
                j=j+1;
            }
        }

    }


    public static void  getListOfShowsByRevenueService(List<Integer> listOfVenueIDs){
        System.out.println("List of shows sorted by the revenue generated in ascending order:");
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> listOfShows= showDAO.getShowDetailsRevenue(listOfVenueIDs);
        if(listOfShows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("List of shows sorted by the revenue generated in ascending order:");
            int j=0;
            System.out.println("Movie_Name | Venue_Name | Hall_Name | Date | Show_time | Revenue");

            while(j<listOfShows.size()){
                System.out.println(((ShowDetails)listOfShows.get(j).get(0)).getMovieName()+" | "+((String)listOfShows.get(j).get(1))+" | "+((Hall)listOfShows.get(j).get(2)).getHallName()+" | "+((ShowDetails)listOfShows.get(j).get(0)).getShowDate()+
                " | "+((ShowDetails)listOfShows.get(j).get(0)).getStartTime()+" | "+ ((String)listOfShows.get(j).get(3)));
                j=j+1;
            }
        }
    }


    public static void  getListOfShowsDateService(List<Integer> listOfVenueIDs,LocalDate date){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> listOfShows= showDAO.getShowDetailsOnDate(listOfVenueIDs,date );
        if(listOfShows.size()==0){
            System.out.println("No shows are playing on "+date+" date!");
        }
        else{
            String currentVenue =((String)listOfShows.get(0).get(1));
            System.out.println("List shows playing on "+date+" date:");
            int j=0;
            while(j<listOfShows.size()){
                    int k=j;
                    System.out.println("VENUE NAME: "+currentVenue);
                    System.out.println("Show_time | Hall_Name | Movie_Name | Unsold_seats_silver | Unsold_seats_gold | Unsold_seats_platinum");
                    while(k<listOfShows.size() && currentVenue.equals(((String)listOfShows.get(k).get(1)))){
                        System.out.println(((ShowDetails)listOfShows.get(k).get(0)).getStartTime()
                        +" | "+((Hall)listOfShows.get(k).get(2)).getHallName()
                        +" | "+((ShowDetails)listOfShows.get(k).get(0)).getMovieName()
                        +" | "+(((Hall)listOfShows.get(k).get(2)).getNumSeatsSilver() - ((ShowDetails)listOfShows.get(k).get(0)).getNumSilverBooked())
                        +" | "+(((Hall)listOfShows.get(k).get(2)).getNumSeatsGold() - ((ShowDetails)listOfShows.get(k).get(0)).getNumGoldBooked())
                        +" | "+(((Hall)listOfShows.get(k).get(2)).getNumSeatsPlatinum() - ((ShowDetails)listOfShows.get(k).get(0)).getNumPlatinumBooked()));
                        k=k+1;
                    }
                    j=k;
                    if(k==listOfShows.size() ){break;}
                    currentVenue=((String)listOfShows.get(j).get(1));
                    System.out.println();
            }
        }
    }
    
    public static void  getListOfRevenuePerMovieDateRangeService(List<Integer> listOfVenueIDs,LocalDate startDate,LocalDate endDate,String movieName){
        MovieDAO movieDAO=new MovieDAO();
        ShowDetailsDAO showDAO=new ShowDetailsDAO();

        List<List<Object>> listOfShows= showDAO.getShowDetailsMovie(listOfVenueIDs,startDate, endDate, movieName);
        if(listOfShows.size()==0){
            System.out.println("No shows are playing for "+movieName+" movie playing between "+startDate+" to"+endDate+"!");
        }
        else{
            System.out.println("List of all shows for "+movieName+" movie playing between "+startDate+" to "+endDate+":");
            String currentVenue =((String)listOfShows.get(0).get(1));
            int j=0;
            while(j<listOfShows.size()){
                int k=j;
                System.out.println("VENUE NAME: "+currentVenue);
                System.out.println("Show_time | Hall_Name | Unsold_seats_silver | Unsold_seats_gold | Unsold_seats_platinum");
                while(k<listOfShows.size() && currentVenue.equals(((String)listOfShows.get(k).get(1)))){
                    System.out.println(((ShowDetails)listOfShows.get(k).get(0)).getStartTime()
                    +" | "+((Hall)listOfShows.get(k).get(2)).getHallName()
                        +" | "+(((Hall)listOfShows.get(k).get(2)).getNumSeatsSilver() - ((ShowDetails)listOfShows.get(k).get(0)).getNumSilverBooked())
                        +" | "+(((Hall)listOfShows.get(k).get(2)).getNumSeatsGold() - ((ShowDetails)listOfShows.get(k).get(0)).getNumGoldBooked())
                        +" | "+(((Hall)listOfShows.get(k).get(2)).getNumSeatsPlatinum() - ((ShowDetails)listOfShows.get(k).get(0)).getNumPlatinumBooked()));
                    k=k+1;
                }
                j=k;
                if(k==listOfShows.size() ){break;}
                currentVenue=((String)listOfShows.get(j).get(1));
                System.out.println();
            }
        }

    }



}

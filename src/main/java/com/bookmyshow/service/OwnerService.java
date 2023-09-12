package src.main.java.com.bookmyshow.service;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

import src.main.java.com.bookmyshow.jdbc.*;
import src.main.java.com.bookmyshow.model.*;

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

    public static void  getThreeDaysBookingDetailsService(List<Integer> venueIDs){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> shows= showDAO.getShowDetailsNextThreeDays(venueIDs );

        if(shows.size()==0){
            System.out.println("No Booking status for the next 3 days!");
        }
        else{
            System.out.println("Booking status for the next 3 days:\n");
            String currentVenue =((String)shows.get(0).get(1));
            Date showDate=((ShowDetails)shows.get(0).get(0)).getShowDate();

            int i=0;
            while(i<shows.size()){
                int j=i;
                System.out.println("DATE: "+showDate);
                while(j<shows.size() && showDate.equals(((ShowDetails)shows.get(j).get(0)).getShowDate()) ){
                    int k=j;
                    System.out.println("VENUE NAME: "+currentVenue);
                    System.out.println("Show_time | Hall_Name | Movie_Name | No_of_silver_seats_booked/Total_silver_seats | No_of_gold_seats_booked/Total_gold_seats | No_of_platinum_seats_booked/Total_platinum_seats");
                    while(k<shows.size() && currentVenue.equals(((String)shows.get(k).get(1))) && showDate.equals(((ShowDetails)shows.get(k).get(0)).getShowDate()) ){
                        System.out.println(((ShowDetails)shows.get(k).get(0)).getStartTime()+" | "+((Hall)shows.get(k).get(2)).getHallName()+" | "+((ShowDetails)shows.get(k).get(0)).getMovieName()
                        +" | "+ ((ShowDetails)shows.get(k).get(0)).getNumSilverBooked()+"/"+((Hall)shows.get(k).get(2)).getNumSeatsSilver()
                        +" | "+ ((ShowDetails)shows.get(k).get(0)).getNumGoldBooked()+"/"+((Hall)shows.get(k).get(2)).getNumSeatsGold()
                        +" | "+ ((ShowDetails)shows.get(k).get(0)).getNumPlatinumBooked()+"/"+((Hall)shows.get(k).get(2)).getNumSeatsPlatinum());
                        k=k+1;
                    }
                    j=k;
                    if(k==shows.size() ){break;}
                    currentVenue=((String)shows.get(j).get(1));
                    System.out.println();
                }
                i=j;
                if(j==shows.size() ){break;}
                showDate=((ShowDetails)shows.get(i).get(0)).getShowDate();
                currentVenue =((String)shows.get(i).get(1));
                System.out.println("--------------------------------------------");

            }
        }

    }
    
    public static void  getSevenDaysBookingDetailsService(List<Integer> venueIDs){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> shows= showDAO.getShowDetailsPastSevenDays(venueIDs );
        if(shows.size()==0){
            System.out.println("No booking history for the past 7 days!");
        }
        else{
            System.out.println("Booking history for the past 7 days:");
            String currentVenue =((String)shows.get(0).get(1));
            Date showDate=((ShowDetails)shows.get(0).get(0)).getShowDate();

            int i=0;
            while(i<shows.size()){
                int j=i;
                System.out.println("DATE: "+showDate);
                while(j<shows.size() && showDate.equals(((ShowDetails)shows.get(j).get(0)).getShowDate()) ){
                    int k=j;
                    System.out.println("VENUE NAME: "+currentVenue);
                    System.out.println("Show_time | Hall_Name | Movie_Name | No_of_seats_booked/Total_seats | Revenue");
                    while(k<shows.size() && currentVenue.equals(((String)shows.get(k).get(1))) && showDate.equals(((ShowDetails)shows.get(k).get(0)).getShowDate()) ){
                        System.out.println(((ShowDetails)shows.get(k).get(0)).getStartTime()
                        +" | "+((Hall)shows.get(k).get(2)).getHallName()
                        +" | "+((ShowDetails)shows.get(k).get(0)).getMovieName()
                        +" | "+(((ShowDetails)shows.get(k).get(0)).getNumSilverBooked()+((ShowDetails)shows.get(k).get(0)).getNumGoldBooked()+((ShowDetails)shows.get(k).get(0)).getNumPlatinumBooked()) 
                        +"/"+(((Hall)shows.get(k).get(2)).getNumSeatsSilver()+((Hall)shows.get(k).get(2)).getNumSeatsGold()+((Hall)shows.get(k).get(2)).getNumSeatsPlatinum())
                        +" | "+((String)shows.get(k).get(3)));
                        k=k+1;
                    }
                    j=k;
                    if(k==shows.size() ){break;}
                    currentVenue=((String)shows.get(j).get(1));
                    System.out.println();
                }
                i=j;
                if(j==shows.size() ){break;}
                showDate=((ShowDetails)shows.get(i).get(0)).getShowDate();
                currentVenue =((String)shows.get(i).get(1));

                System.out.println("--------------------------------------------");
                // System.exit(0);
            }
        }
    }

    public static void  getListOfRevenuePerDayService(List<Integer> venueIDs ){

        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<String>> shows= showDAO.getRevenuePerDay(venueIDs);
        if(shows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("Breakdown of revenue generated per day:");
            int j=0;
            System.out.println("Date \t Revenue per day");

            while(j<shows.size()){
                System.out.println(shows.get(j).get(0)+" \t "+shows.get(j).get(1));
                j=j+1;
            }
        }

    }

    public static void  getListOfRevenuePerMovieService(List<Integer> venueIDs ){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<String>> shows= showDAO.getRevenuePerMovie(venueIDs);
        if(shows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("Breakdown of revenue generated per movie:");
            int j=0;
            System.out.println("Movie_Name | Revenue per movie");

            while(j<shows.size()){
                System.out.println(shows.get(j).get(0)+" | "+shows.get(j).get(1));
                j=j+1;
            }
        }

    }


    public static void  getListOfShowsByRevenueService(List<Integer> venueIDs){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> shows= showDAO.getShowDetailsRevenue(venueIDs);
        if(shows.size()==0){
            System.out.println("No shows are playing!");
        }
        else{
            System.out.println("List of shows sorted by the revenue generated in ascending order:");
            int j=0;
            System.out.println("Movie_Name | Venue_Name | Hall_Name | Date | Show_time | Revenue");

            while(j<shows.size()){
                System.out.println(((ShowDetails)shows.get(j).get(0)).getMovieName()+" | "+((String)shows.get(j).get(1))+" | "+((Hall)shows.get(j).get(2)).getHallName()+" | "+((ShowDetails)shows.get(j).get(0)).getShowDate()+
                " | "+((ShowDetails)shows.get(j).get(0)).getStartTime()+" | "+ ((String)shows.get(j).get(3)));
                j=j+1;
            }
        }
    }


    public static void  getListOfShowsDateService(List<Integer> venueIDs,LocalDate date){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();
        List<List<Object>> shows= showDAO.getShowDetailsOnDate(venueIDs,date );
        if(shows.size()==0){
            System.out.println("No shows are playing on "+date+" date!");
        }
        else{
            String currentVenue =((String)shows.get(0).get(1));
            System.out.println("List shows playing on "+date+" date:");
            int j=0;
            while(j<shows.size()){
                    int k=j;
                    System.out.println("VENUE NAME: "+currentVenue);
                    System.out.println("Show_time | Hall_Name | Movie_Name | Unsold_seats_silver | Unsold_seats_gold | Unsold_seats_platinum");
                    while(k<shows.size() && currentVenue.equals(((String)shows.get(k).get(1)))){
                        System.out.println(((ShowDetails)shows.get(k).get(0)).getStartTime()
                        +" | "+((Hall)shows.get(k).get(2)).getHallName()
                        +" | "+((ShowDetails)shows.get(k).get(0)).getMovieName()
                        +" | "+(((Hall)shows.get(k).get(2)).getNumSeatsSilver() - ((ShowDetails)shows.get(k).get(0)).getNumSilverBooked())
                        +" | "+(((Hall)shows.get(k).get(2)).getNumSeatsGold() - ((ShowDetails)shows.get(k).get(0)).getNumGoldBooked())
                        +" | "+(((Hall)shows.get(k).get(2)).getNumSeatsPlatinum() - ((ShowDetails)shows.get(k).get(0)).getNumPlatinumBooked()));
                        k=k+1;
                    }
                    j=k;
                    if(k==shows.size() ){break;}
                    currentVenue=((String)shows.get(j).get(1));
                    System.out.println();
            }
        }
    }
    
    public static void  getListOfRevenuePerMovieDateRangeService(List<Integer> venueIDs,LocalDate startDate,LocalDate endDate,String movieName){
        ShowDetailsDAO showDAO=new ShowDetailsDAO();

        List<List<Object>> shows= showDAO.getShowDetailsMovie(venueIDs,startDate, endDate, movieName);
        if(shows.size()==0){
            System.out.println("No shows are playing for "+movieName+" movie playing between "+startDate+" to"+endDate+"!");
        }
        else{
            System.out.println("List of all shows for "+movieName+" movie playing between "+startDate+" to "+endDate+":");
            String currentVenue =((String)shows.get(0).get(1));
            int j=0;
            while(j<shows.size()){
                int k=j;
                System.out.println("VENUE NAME: "+currentVenue);
                System.out.println("Show_time | Hall_Name | Unsold_seats_silver | Unsold_seats_gold | Unsold_seats_platinum");
                while(k<shows.size() && currentVenue.equals(((String)shows.get(k).get(1)))){
                    System.out.println(((ShowDetails)shows.get(k).get(0)).getStartTime()
                    +" | "+((Hall)shows.get(k).get(2)).getHallName()
                        +" | "+(((Hall)shows.get(k).get(2)).getNumSeatsSilver() - ((ShowDetails)shows.get(k).get(0)).getNumSilverBooked())
                        +" | "+(((Hall)shows.get(k).get(2)).getNumSeatsGold() - ((ShowDetails)shows.get(k).get(0)).getNumGoldBooked())
                        +" | "+(((Hall)shows.get(k).get(2)).getNumSeatsPlatinum() - ((ShowDetails)shows.get(k).get(0)).getNumPlatinumBooked()));
                    k=k+1;
                }
                j=k;
                if(k==shows.size() ){break;}
                currentVenue=((String)shows.get(j).get(1));
                System.out.println();
            }
        }

    }



}

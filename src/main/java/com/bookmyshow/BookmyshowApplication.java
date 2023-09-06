package src.main.java.com.bookmyshow;

import java.time.format.DateTimeFormatter;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import src.main.java.com.bookmyshow.jdbc.*;
import src.main.java.com.bookmyshow.model.*;
import src.main.java.com.bookmyshow.service.JASONParsing;

public class BookmyshowApplication{
    public static void main(String[] args){
        // Owner currentUser= new Owner();
        Scanner s= new Scanner(System.in);
        
        // while(true){
        //     System.out.println("Enter login details for owner:");
        //     // String filePath = "/Users/cb-it-01-1958/Documents/Bookmyshow/src/inputs/owner_login.jason";
        //     String filePath = s.nextLine();
        //     User user1= (Owner)JASONParsing.parseDataOwner(filePath);
        //     User result = currentUser.login(user1);
        //     System.out.println(user1);
        //     if(result==null){
        //         System.out.println("Login failed. Incorrect password. please try again.");
        //         continue;
        //     }
        //     else{
        //         currentUser=(Owner)result;
        //         break;
        //     }
        // }
        Owner currentUser= new Owner();
        Venue v1=new Venue();
        v1.setVenueID("venueID1");
        v1.setName("venue1");
        Venue v2=new Venue();
        v2.setVenueID("venueID2");
        v2.setName("venue2");
        Venue v3=new Venue();
        v3.setVenueID("venueID3");
        v3.setName("venue3");

        currentUser.setListOfVenues(new ArrayList<>(Arrays.asList(v1, v2, v3)));

        System.out.println("Please enter one of the option:\n"+
                                "1)Add Venue details\n"+
                                "2)Add Show details\n"+
                                "3)Add Movie details\n"+
                                "4)View booking status for the next 3 days\n"+
                                "5)booking history for last 7 days\n"+
                                "6)List of shows sorted by the revenue generated in ascending order \n"+
                                "7)List of shows along for a given date\n"+
                                "8)List of all shows for a given movie for specified date range\n");

        int option = Integer.parseInt(s.nextLine());
        if(option==1){
            currentUser.addVenueDetails();
        }
        else if(option==2){
            currentUser.addShowDetails();

        }
        else if(option==3){
            
        }
        else if(option==4){
            currentUser.getThreeDaysBookingDetails();

        }
        else if(option==6){
            currentUser.getListOfShowsByRevenue();

        }
        else if(option==7){
            System.out.print("Please Enter the date (yyyy-MM-dd):");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString=s.nextLine();
            LocalDate date =null;
            try {
                date = LocalDate.parse(dateString, formatter);
            } catch (Exception e) {e.printStackTrace();}
            currentUser.getListOfShows(date);

        }
        else if(option==8){
            System.out.print("Please Enter the start date (yyyy-MM-dd):");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateString=s.nextLine();
            System.out.print("Please Enter the end date (yyyy-MM-dd):");
            String endDateString=s.nextLine();
            System.out.print("Please Enter the movie name:");
            String movieName=s.nextLine();
            LocalDate startDate =null;
            LocalDate endDate =null;
            try {
                startDate = LocalDate.parse(startDateString, formatter);
                endDate = LocalDate.parse(startDateString, formatter);
            } catch (Exception e) {e.printStackTrace();}
            currentUser.getListOfShowsForMovie(startDate,endDate,movieName);
        }


        // OwnerDAO ownerDAO = new OwnerDAO();
        // List<User> users=ownerDAO.getAllOwners();
        // for (User u:users){
        //     System.out.println(u);
        //     System.out.println();
        // }

        // User user=ownerDAO.getOwner("user123");
        // System.out.println(user);
    }

}
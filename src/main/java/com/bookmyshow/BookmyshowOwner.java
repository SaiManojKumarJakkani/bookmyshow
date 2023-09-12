package src.main.java.com.bookmyshow;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import src.main.java.com.bookmyshow.jdbc.*;
import src.main.java.com.bookmyshow.model.*;
import src.main.java.com.bookmyshow.service.JASONParsing;

public class BookmyshowOwner{
    public void ownerStart() throws SQLException {
        Scanner s= new Scanner(System.in);
        Owner currentUser= new Owner();
        System.out.println("Using as owner...");
        System.out.println("Please enter appropriate option");
        System.out.println("1. Sign-up as new owner");
        System.out.println("2. Log in to existing owner");
        int choice = s.nextInt();
        s.nextLine();
        switch (choice) {
            case 1 :
                while(true){
                    System.out.println("Enter path of json file for new user sign up details:");
                    String filePath1 = s.nextLine();
                    currentUser = JASONParsing.parseDataOwner(filePath1);
                    int result = currentUser.signUp(currentUser);
                    if(result==-2){
                        System.out.println("User details already existed! Please try again.");
                        continue;
                    }
                    else if (result==0){
                        System.out.println("Failed to save details! Please try again.");
                        continue;
                    }
                    else{
                        VenueDAO venueDAO = new VenueDAO();
                        List<Venue> listOfVenues=venueDAO.getAllVenuesForOwner(currentUser.getUserID());
                        currentUser.setListOfVenues(listOfVenues);
                        break;
                    }
                }
                break;

            case 2 :
                while(true){
                    System.out.println("Enter path of json file for login details for owner:");
                    String filePath1 = s.nextLine();
                    User user1= (Owner)JASONParsing.parseDataOwner(filePath1);
                    User result = currentUser.login(user1);
                    if(result==null){
                        System.out.println("Login failed. Incorrect password. please try again.");
                        continue;
                    }
                    else{
                        currentUser=(Owner)result;
                        VenueDAO venueDAO = new VenueDAO();
                        List<Venue> listOfVenues=venueDAO.getAllVenuesForOwner(currentUser.getUserID());
                        currentUser.setListOfVenues(listOfVenues);
                        break;
                    }
                }
                break;

            default: System.out.println("Invalid choice.");
        }
        while(true){
            System.out.println("\nPlease enter one of the option:\n"+
                    "1)Add Venue details\n"+
                    "2)Add Show details\n"+
                    "3)Add Movie details\n"+
                    "4)View booking status for the next 3 days\n"+
                    "5)booking history for last 7 days\n"+
                    "6)Breakdown of revenue generated per day \n"+
                    "7)Breakdown of revenue generated per movie \n"+
                    "8)List of shows sorted by the revenue generated in ascending order \n"+
                    "9)List of shows along for a given date\n"+
                    "10)List of all shows for a given movie for specified date range\n"+
                    "11)Exit\n");
            int option=-1;
            try{
                option = Integer.parseInt(s.nextLine());
            }
            catch(Exception e){
            }

            if(option==1){
                System.out.println("Enter Venue details:");
                String filePath = s.nextLine();
                Venue inputVenue= (Venue)JASONParsing.parseDataVenue(filePath);
                currentUser.addVenueDetails(inputVenue);
            }

            else if(option==2){
                System.out.println("Enter Show details:");
                String filePath = s.nextLine();
                ShowDetails inputShow= (ShowDetails)JASONParsing.parseDataShowDetails(filePath);
                currentUser.addShowDetails(inputShow);
            }

            else if(option==3){
                System.out.println("Enter Movie details:");
                String filePath = s.nextLine();
                Movie inputMovie= (Movie)JASONParsing.parseDataMovie(filePath);
                currentUser.addMovie(inputMovie);
            }

            else if(option==4){
                currentUser.getThreeDaysBookingDetails();
            }
            else if(option==5){
                currentUser.getSevenDaysBookingDetails();

            }
            else if(option==6){
                currentUser.getListOfRevenuePerDay();

            }

            else if(option==7){
                currentUser.getListOfRevenuePerMovie();

            }

            else if(option==8){
                currentUser.getListOfShowsByRevenue();

            }

            else if(option==9){
                System.out.print("Please Enter the date (YYYY-MM-DD):");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateString=s.nextLine();
                LocalDate date =null;
                try {
                    date = LocalDate.parse(dateString, formatter);
                    currentUser.getListOfShowsDate(date);

                } catch (Exception e) {e.printStackTrace();}

            }

            else if(option==10){
                try {
                    System.out.print("Please Enter the start date (YYYY-MM-DD):");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String startDateString=s.nextLine();
                    LocalDate startDate = LocalDate.parse(startDateString, formatter);
                    System.out.print("Please Enter the end date (YYYY-MM-DD):");
                    String endDateString=s.nextLine();
                    LocalDate endDate = LocalDate.parse(endDateString, formatter);
                    int comparisonResult = startDate.compareTo(endDate);
                    if (comparisonResult > 0) {
                        System.out.println("Please enter valid date range!");
                        System.exit(0);
                    }
                    System.out.print("Please enter the movie name:");
                    String movieName=s.nextLine();
                    currentUser.getListOfShowsForMovieDateRange(startDate,endDate,movieName);

                } catch (Exception e) {System.out.print(e);}
            }
            else if(option==11){
                break;
            }
            else{
                System.out.print("Please enter valid option!!\n");

            }
        }

    }

}

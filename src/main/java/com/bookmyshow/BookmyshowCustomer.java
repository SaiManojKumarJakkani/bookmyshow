package src.main.java.com.bookmyshow;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import src.main.java.com.bookmyshow.model.*;
import src.main.java.com.bookmyshow.service.JASONParsing;


public class BookmyshowCustomer {
    private void printShowsArray(List<ShowDetails> arr){
        for(ShowDetails show:arr){
            System.out.println(show);
            System.out.println();
        }
    }
    public void customerStart(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Using as customer...");
        System.out.println("Please enter appropriate option");
        System.out.println("1. Sign-up as new customer");
        System.out.println("2. Log in to existing account");
        int choice = sc.nextInt();
        sc.nextLine();
        Customer customer = null;
        switch (choice) {
            case 1 :
                System.out.println("Enter path of json file for new user sign up details:");
                String filePath = sc.nextLine();
                if (filePath.trim().isEmpty()) {
                    System.out.println("Error: File Path is invalid");
                    return;
                }
                customer = JASONParsing.parseDataCustomer(filePath);
                int status = customer.signUp(customer);
                if(status==-1){
                    customer=null;
                }
                break;
            case 2 :
                System.out.println("Enter path of json file for login details:");
                String filePath1 = sc.nextLine();
                if (filePath1.trim().isEmpty()) {
                    System.out.println("Error: File Path is invalid");
                    return;
                }
                customer = JASONParsing.parseDataCustomer(filePath1);
                customer = (Customer) customer.login(customer);
                break;

            default: System.out.println("Invalid choice.");
        }

        if(customer!=null) {
            while (true) {
                System.out.println("\n\nChoose option from below:");
                System.out.println("1. View shows on a particular date and venue.");
                System.out.println("2. View shows in a date range at a venue.");
                System.out.println("3. View shows of a movie in a date range.");
                System.out.println("4. Book tickets for a show.");
                System.out.println("5. View your booking history.");
                System.out.println("6. Logout.");
                LocalDate date;
                LocalDate startDate;
                LocalDate endDate;
                String venue;
                String movie;
                try {
                    choice = sc.nextInt();
                }catch (Exception e){
                    System.out.println(e);
                    choice=0;
                }
                sc.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Enter date (yyyy-mm-dd):");
                        date = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter name of venue:");
                        venue = sc.nextLine();
                        List<ShowDetails> shows1 = customer.getShowsOnDate(date, venue);
                        if(shows1!=null && shows1.size()!=0){
                            printShowsArray(shows1);
                        }else {
                            System.out.println("No shows found.");
                        }
                        break;

                    case 2:
                        System.out.println("Enter start date (yyyy-mm-dd):");
                        startDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter end date (yyyy-mm-dd):");
                        endDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter name of venue:");
                        venue = sc.nextLine();
                        List<ShowDetails> shows2 = customer.getShowsInDateRange(startDate, endDate, venue);
                        if(shows2!=null&& shows2.size()!=0){
                            printShowsArray(shows2);
                        }else {
                            System.out.println("No shows found.");
                        }
                        break;

                    case 3:
                        System.out.println("Enter start date (yyyy-mm-dd):");
                        startDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter end date (yyyy-mm-dd):");
                        endDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter name of movie:");
                        movie = sc.nextLine();
                        List<ShowDetails> shows3 = customer.getShowsForMovieInDateRange(startDate, endDate, movie);
                        if(shows3!=null && shows3.size()!=0){
                            printShowsArray(shows3);
                        }else {
                            System.out.println("No shows found.");
                        }
                        break;

                    case 4:
                        System.out.println("Enter path of json file of booking object:");
                        String bookingJsonFile = sc.nextLine();
                        if (bookingJsonFile.trim().isEmpty()) {
                            System.out.println("Error: File path is invalid");
                            return;
                        }
                        try {
                            customer.makeBookingForShow(bookingJsonFile);
                        } catch (SQLException e) {
                            System.out.println("Booking failed due to "+e);
                        }
                        break;

                    case 5:
                        customer.viewBookingHistory();
                        break;

                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice entered. Enter a valid option number.");
                }
            }
        }else {
            System.out.println("Can not proceed due to failed login/signup.");
        }
    }
}
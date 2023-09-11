package src.main.java.com.bookmyshow;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import src.main.java.com.bookmyshow.model.*;
import src.main.java.com.bookmyshow.service.JASONParsing;


public class BookmyshowCustomer {
    private static void printShowsArray(List<ShowDetails> arr){
        for(ShowDetails show:arr){
            System.out.println(show);
            System.out.println();
        }
    }
    public static void main(String[] args){
//        // test for login
//        String filePath1 = "/Users/cb-it-01-1959/Documents/Assign-3/bookmyshow-addrija/src/inputs/customer_login.json";
//        Customer customer1 = JASONParsing.parseDataCustomer(filePath1);
//        customer1 = (Customer)customer1.login(customer1);
//        System.out.println("::::::::::::");
//        System.out.println(customer1);
//        System.out.println("::::::::::::");
//
//        //test for signup
//        String filePath2 = "/Users/cb-it-01-1959/Documents/Assign-3/bookmyshow-addrija/src/inputs/customer_signup.json";
//        Customer customer2 = JASONParsing.parseDataCustomer(filePath2);
//        customer2 = (Customer)customer2.signUp(customer2);
//        System.out.println("::::::::::::");
//        System.out.println(customer2);
//        System.out.println("::::::::::::");

//        //test for view shows on a particular date and venue
//        List<ShowDetails> shows1 = Customer.getShowsOnDate(LocalDate.parse("2023-09-10"),"venue1");
//        System.out.println(shows1.size());
//
//        //test for view shows in a date range at venue
//        List<ShowDetails> shows2 = Customer.getShowsInDateRange(LocalDate.parse("2023-09-01"),LocalDate.parse("2023-09-20"),"venue1");
//        System.out.println(shows2.size());
//
//        //test for shows of a movie in date range across all venues
//        List<ShowDetails> shows3 = Customer.getShowsForMovieInDateRange(LocalDate.parse("2023-09-01"),LocalDate.parse("2023-09-20"),"barbie");
//        System.out.println(shows3.size());
//
//        //test for shows of a movie on a date across all venues
//        List<ShowDetails> shows4 = Customer.getShowsForMovieOnDate(LocalDate.parse("2023-09-10"),"barbie");
//        System.out.println(shows4.size());
//
//        test for adding booking
//        String bookingJsonFile = "/Users/cb-it-01-1959/Documents/Assign-3/bookmyshow-addrija/src/inputs/bookingJson.json";
//        try {
//            customer1.makeBookingForShow(bookingJsonFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        test for viewing booking history
//        customer1.viewBookingHistory();
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
                customer = JASONParsing.parseDataCustomer(filePath);
                customer = (Customer) customer.signUp(customer);
                break;
            case 2 :
                System.out.println("Enter path of json file for login details:");
                String filePath1 = sc.nextLine();
                customer = JASONParsing.parseDataCustomer(filePath1);
                customer = (Customer) customer.login(customer);
                break;

            default: System.out.println("Invalid choice.");
        }
        try {
            while (true) {
                System.out.println("Choose option from below:");
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
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 :
                        System.out.println("Enter date (yyyy-mm-dd):");
                        date = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter name of venue:");
                        venue = sc.nextLine();
                        List<ShowDetails> shows1 = Customer.getShowsOnDate(date, venue);
                        printShowsArray(shows1);
                        break;

                    case 2 :
                        System.out.println("Enter start date (yyyy-mm-dd):");
                        startDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter end date (yyyy-mm-dd):");
                        endDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter name of venue:");
                        venue = sc.nextLine();
                        List<ShowDetails> shows2 = Customer.getShowsInDateRange(startDate,endDate,venue);
                        printShowsArray(shows2);
                        break;

                    case 3:
                        System.out.println("Enter start date (yyyy-mm-dd):");
                        startDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter end date (yyyy-mm-dd):");
                        endDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter name of movie:");
                        movie = sc.nextLine();
                        List<ShowDetails> shows3 = Customer.getShowsForMovieInDateRange(startDate,endDate,movie);
                        printShowsArray(shows3);
                        break;

                    case 4 :
                        System.out.println("Enter path of json file of booking object:");
                        String bookingJsonFile=sc.nextLine();
                        customer.makeBookingForShow(bookingJsonFile);
                        break;

                    case 5:
                        customer.viewBookingHistory();
                        break;

                    case 6:
                        System.exit(0);
                        break;

                    default: System.out.println("Invalid choice entered. Enter a valid option number.");
                }
            }
        }catch(Exception e){
            e.getMessage();
        }

    }
}
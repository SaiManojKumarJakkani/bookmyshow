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
    public void customerStart(String[] args){

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

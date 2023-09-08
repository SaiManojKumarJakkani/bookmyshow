package src.main.java.com.bookmyshow.model;

import src.main.java.com.bookmyshow.jdbc.BookingDAO;
import src.main.java.com.bookmyshow.jdbc.CustomerDAO;
import src.main.java.com.bookmyshow.jdbc.ShowDetailsDAO;
import src.main.java.com.bookmyshow.service.JASONParsing;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static src.main.java.com.bookmyshow.service.JASONParsing.*;

public class Customer extends User{
    private List<Booking> listOfBookings;
    private static CustomerDAO customerDAO;
    private static ShowDetailsDAO showDetailsDAO;
    private BookingDAO bookingDAO;
    public Customer(){
        customerDAO = new CustomerDAO();
        showDetailsDAO = new ShowDetailsDAO();
        listOfBookings = new ArrayList<>();
    }
    public Customer(String userID,String password,String firstName, String lastName,String emailID){
        super(userID,password, firstName, lastName,emailID);
        customerDAO = new CustomerDAO();
        showDetailsDAO = new ShowDetailsDAO();
        listOfBookings = new ArrayList<>();
    }

    @Override
    public User login(String filePath){
//        String filePath = "/Users/cb-it-01-1959/Documents/Assign-3/bookmyshow-addrija/src/inputs/customer_login.json";
        Customer userTemp = (Customer) parseDataCustomer(filePath);

        Customer queryCustomer = (Customer) customerDAO.getCustomerByEmailID(userTemp.getEmailID());
        if(queryCustomer!=null && queryCustomer.getPassword().equals(userTemp.getPassword())){
            System.out.println("Login successful.");
            return queryCustomer;
        }else {
            System.out.println("Login failed. Try again");
            return null;
        }
    }

    @Override
    public User signUp(String filePath) {
        //read details of new customer
        Customer newCustomer = (Customer) parseDataCustomer(filePath);
        //create data access object to check database
        User queryEmailCustomer = customerDAO.getCustomerByEmailID(newCustomer.getEmailID());
        if(queryEmailCustomer==null){
            int numCustomers = customerDAO.getRowsInCustomerTable();
            newCustomer.setUserID("cust"+Integer.toString(numCustomers+1));
            customerDAO.addNewCustomer(newCustomer);
            System.out.println("Sign up successful. Welcome!");
            return newCustomer;
        }else{
            System.out.println("This email is already in use.");
            return null;
        }
    }

    public void makeBookingForShow(String jsonFilePath){

        List<Booking> bookings= JASONParsing.parseBookingData(jsonFilePath);
        Booking bookingRequestedByCust = bookings.get(0);
        String tier = bookingRequestedByCust.getPricingTierChosen();
        int seatsAvailable = 0;
        if(tier.equalsIgnoreCase("silver")){
            seatsAvailable = showDetailsDAO.getAvailableSeatsForSilver(bookingRequestedByCust.getShowID());
        } else if (tier.equalsIgnoreCase("gold")) {
            seatsAvailable = showDetailsDAO.getAvailableSeatsForGold(bookingRequestedByCust.getShowID());
        } else if (tier.equalsIgnoreCase("platinum")) {
            seatsAvailable = showDetailsDAO.getAvailableSeatsForPlatinum(bookingRequestedByCust.getShowID());
        }

        if(seatsAvailable-bookingRequestedByCust.getNumSeatsBooked()>=0){
            Booking booking =
                    new Booking(bookingRequestedByCust.getBookingID(),
                            this.getUserID(),
                            bookingRequestedByCust.getVenueID(),
                            bookingRequestedByCust.getShowID(),
                            bookingRequestedByCust.getBookingDate(),
                            bookingRequestedByCust.getNumSeatsBooked(),
                            bookingRequestedByCust.getBankDetailsCust(),
                            bookingRequestedByCust.getPricingTierChosen(),
                            "success");
            bookingDAO.insert(booking);
        }else {
            System.out.println("Insufficient seats available for the chosen pricing tier. Booking failed.");
        }
    }

    public static List<ShowDetails> getShowsOnDate(LocalDate date, String venueName){
        return showDetailsDAO.getShowsOnDate(date,venueName);
    }
    public static List<ShowDetails> getShowsInDateRange(LocalDate startDate, LocalDate endDate, String venueName){
        return showDetailsDAO.getShowsInDateRange(startDate,endDate,venueName);
    }
    public static List<ShowDetails> getShowsForMovieInDateRange(LocalDate startDate, LocalDate endDate,String movieName){
        return  showDetailsDAO.getShowsForMovieInDateRange(movieName,startDate,endDate);
    }
    public static List<ShowDetails> getShowsForMovieOnDate(LocalDate date,String movieName) {
        return showDetailsDAO.getShowsForMovieOnDate(movieName,date);
    }

    public void viewBookingHistory(){
        List<Booking> bookings = bookingDAO.getBookingHistoryOfCustomer(this.getUserID());
        for(Booking bk:bookings){
            System.out.println(bk);
            System.out.println("----------------------------");
        }
    }
}

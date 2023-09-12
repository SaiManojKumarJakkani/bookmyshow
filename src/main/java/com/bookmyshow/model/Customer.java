package src.main.java.com.bookmyshow.model;

import src.main.java.com.bookmyshow.jdbc.BookingDAO;
import src.main.java.com.bookmyshow.jdbc.CustomerDAO;
import src.main.java.com.bookmyshow.jdbc.ShowDetailsDAO;
import src.main.java.com.bookmyshow.service.JASONParsing;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private List<Booking> listOfBookings;
    private CustomerDAO customerDAO;
    private ShowDetailsDAO showDetailsDAO;
    private BookingDAO bookingDAO;
    
    public Customer(){
        customerDAO = new CustomerDAO();
        showDetailsDAO = new ShowDetailsDAO();
        bookingDAO = new BookingDAO();
        listOfBookings = new ArrayList<>();
    }
    public Customer(String userID,String password,String firstName, String lastName,String emailID){
        super(userID,password, firstName, lastName,emailID);
        customerDAO = new CustomerDAO();
        showDetailsDAO = new ShowDetailsDAO();
        bookingDAO = new BookingDAO();
        listOfBookings = new ArrayList<>();
    }
    public Customer(User otherCustomer){
        this(otherCustomer.getUserID(), otherCustomer.getPassword(), otherCustomer.getFirstName(), otherCustomer.getLastName(), otherCustomer.getEmailID());
    }
    @Override
    public User login(User customer){
        Customer queryCustomer = (Customer) customerDAO.getCustomerByUserID(customer.getUserID());
        if(queryCustomer!=null && queryCustomer.getPassword().equals(customer.getPassword())){
            System.out.println("Login successful.");
            return queryCustomer;
        }else {
            System.out.println("Login failed. Try again");
            return null;
        }
    }

    @Override
    public int signUp(User newCustomer) {
        User queryCustomer = customerDAO.getCustomerByUserID(newCustomer.getUserID());
        int rowsAffected=0;
        if(queryCustomer==null){
            rowsAffected = customerDAO.addNewCustomer((Customer) newCustomer);
            System.out.println("Sign up successful. Welcome!");
        }else{
            rowsAffected=-1;
            System.out.println("This userID is already in use. SignUp failed.");
        }
        return rowsAffected;
    }

    public void makeBookingForShow(String jsonFilePath) throws SQLException {

        Booking bookingRequestedByCust= JASONParsing.parseBookingData(jsonFilePath);
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
                            bookingRequestedByCust.getHallID(),
                            bookingRequestedByCust.getShowID(),
                            bookingRequestedByCust.getBookingDate(),
                            bookingRequestedByCust.getNumSeatsBooked(),
                            bookingRequestedByCust.getBankDetailsCust(),
                            bookingRequestedByCust.getPricingTierChosen(),
                            "success");
            BookingDAO.insertNewBooking(booking);
            showDetailsDAO.incrementBookedSeats(bookingRequestedByCust.getShowID(),
                    bookingRequestedByCust.getNumSeatsBooked(),bookingRequestedByCust.getPricingTierChosen());

        }else {
            System.out.println("Insufficient seats available for the chosen pricing tier. Booking failed.");
        }
    }

    public  List<ShowDetails> getShowsOnDate(LocalDate date, String venueName){
        return showDetailsDAO.getShowsOnDate(date,venueName);
    }
    public  List<ShowDetails> getShowsInDateRange(LocalDate startDate, LocalDate endDate, String venueName){
        return showDetailsDAO.getShowsInDateRange(startDate,endDate,venueName);
    }
    public  List<ShowDetails> getShowsForMovieInDateRange(LocalDate startDate, LocalDate endDate,String movieName){
        return  showDetailsDAO.getShowsForMovieInDateRange(movieName,startDate,endDate);
    }
    public  List<ShowDetails> getShowsForMovieOnDate(LocalDate date,String movieName) {
        return showDetailsDAO.getShowsForMovieOnDate(movieName,date);
    }

    public void viewBookingHistory(){
        try {
            listOfBookings = bookingDAO.getBookingHistoryOfCustomer(this.getUserID());
            for(Booking bk:listOfBookings){
                System.out.println(bk);
                System.out.println("----------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

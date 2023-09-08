package org.example;
import java.util.ArrayList;
import java.util.List;
public class Booking {
    private  String bookingID;//Primary key
    private  String custID;//foreign key

    private  String venueID;//foreign key
    private  String showID;//foreign key
    private  String bookingDate; //read from user
    private  int numSeatsBooked;//read from user
    private  String pricingTierChosen; //read from user
    private  String bankDetailsCust;//bank account number and IFSC code-2d array for each customer
    private  String transactionStatus;//hardcode in json

    public Booking() {
        this.bookingID="";
        this.custID="";
        this.venueID="";//take from venue class
        this.showID="";//take from show class
        this.bookingDate="";
        this.numSeatsBooked=0;//read from user
        this.bankDetailsCust=""; // when to take arraylist vs string list
        this.pricingTierChosen="";// choose from [gold, silver, platinum]
        this.transactionStatus="";//Failed or successful
    }
    public Booking(String bookingID, String custID,  String venueID, String showID,
                   String bookingDate, int numSeatsBooked, String bankDetailsCust,
                   String pricingTierChosen, String transactionStatus) {
        this.bookingID=bookingID;
        this.custID= custID;
        this.venueID=venueID;
        this.showID=showID;
        this.bookingDate=bookingDate;
        this.numSeatsBooked=numSeatsBooked;
        this.bankDetailsCust=bankDetailsCust;
        this.pricingTierChosen=pricingTierChosen;
        this.transactionStatus=transactionStatus;//depends on the payment gateway.
    }

    //Getter and setter functions
    public String getBookingID() {
        return bookingID;
    }
    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    //Getter and setter functions
    public String getCustID() {
        return custID;
    }
    public void setCustID(String custID) {
        this.custID = custID;
    }

    //Getter and setter functions
    public String getVenueID() {
        return venueID;
    }
    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    //Getter and setter functions
    public String getShowID() {
        return showID;
    }
    public void setShowID(String showID) {
        this.showID = showID;
    }

    //Getter and setter functions
    public String getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    //Getter and setter functions
    public int getNumSeatsBooked() {
        return numSeatsBooked;
    }
    public void setNumSeatsBooked(int numSeatsBooked) {
        this.numSeatsBooked = numSeatsBooked;
    }

    //Getter and setter functions
    public String getBankDetailsCust() {
        return bankDetailsCust;
    }
    public void setBankDetailsCust(String bankDetailsCust) {
        this.bankDetailsCust = bankDetailsCust;
    }

    //Getter and setter functions
    public String getPricingTierChosen() {
        return pricingTierChosen;
    }
    public void setPricingTierChosen(String pricingTierChosen) {
        this.pricingTierChosen = pricingTierChosen;
    }

    //Getter and setter functions
    public String getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public String toString() {
        return "bookingID: "+bookingID+
                "\n custID: "+custID+
                "\n venueID: "+venueID+
                "\n showID: "+showID+
                "\n bookingDate: "+bookingDate+
                "\n numSeatsBooked: "+numSeatsBooked+
                "\n bankDetailsCust: "+bankDetailsCust+
                "\n pricingTierChosen: "+pricingTierChosen+
                "\n transactionStatus: "+transactionStatus;
    }


}

package org.example;
import java.util.Date;

public class Booking {
    private  int bookingID;//Primary key
    private  int custID;//foreign key

    private  int venueID;//foreign key
    private  int showID;//foreign key
    private Date bookingDate; //read from user
    private  int numSeatsBooked;//read from user
    private  String pricingTierChosen; //read from user
    private  String bankDetailsCust;//bank account number and IFSC code-2d array for each customer
    private  String transactionStatus;//hardcode in json

    public Booking() {
        this.bookingID=0;
        this.custID=0;
        this.venueID=0;//take from venue class
        this.showID=0;//take from show class
        this.bookingDate= null;
        this.numSeatsBooked=0;//read from user
        this.bankDetailsCust=""; // when to take arraylist vs string list
        this.pricingTierChosen="";// choose from [gold, silver, platinum]
        this.transactionStatus="";//Failed or successful
    }
    public Booking(int bookingID, int custID,  int venueID, int showID,
                   Date bookingDate, int numSeatsBooked, String bankDetailsCust,
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
    public int getBookingID() {
        return bookingID;
    }
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    //Getter and setter functions
    public int getCustID() {
        return custID;
    }
    public void setCustID(int custID) {
        this.custID = custID;
    }

    //Getter and setter functions
    public int getVenueID() {
        return venueID;
    }
    public void setVenueID(int venueID) {
        this.venueID = venueID;
    }

    //Getter and setter functions
    public int getShowID() {
        return showID;
    }
    public void setShowID(int showID) {
        this.showID = showID;
    }

    //Getter and setter functions
    public Date getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Date bookingDate) {
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

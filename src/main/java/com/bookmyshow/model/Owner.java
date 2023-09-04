package src.main.java.com.bookmyshow.model;

public class Owner extends User {
    private String bankAccNo;
    public Owner(String userID, String password,String firstName, String lastName, String emailID, String bankAccNo){
        super(userID,password, firstName, lastName,emailID);
        this.bankAccNo=bankAccNo;
    }


    void login(){




    }

    void signUp(){

    }

    void addShowDetails(){}
    void addVenueDetails(){}
    void seeBookingDetails(){}

    @Override
    public String toString() {
        return super.toString() + "\nBank Account Number: " + bankAccNo;
    }


    
}

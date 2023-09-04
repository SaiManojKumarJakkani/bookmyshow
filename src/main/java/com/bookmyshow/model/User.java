package src.main.java.com.bookmyshow.model;
import com.google.gson.annotations.SerializedName;

public abstract class User {
    @SerializedName("userID")
    private String userID;
    @SerializedName("password")
    private String password;
    private String firstName;
    private String lastName;
    private String emailID;


    User(String userID,String password,String firstName, String lastName, String emailID ){
        this.userID=userID;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailID=emailID;

    }


    abstract void login();
    abstract void signUp();



    // Getter and Setter for userID
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for emailID
    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }



    @Override
    public String toString() {
        return "UserID: "+userID+
        "\n password: "+password+
        "\n firstName: "+firstName+
        "\n lastName: "+lastName+
        "\n emailID: "+emailID;
    }


    
}

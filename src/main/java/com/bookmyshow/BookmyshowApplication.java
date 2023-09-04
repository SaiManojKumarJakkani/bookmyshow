package src.main.java.com.bookmyshow;

import java.io.FileReader;
import java.util.List;

import src.main.java.com.bookmyshow.jdbc.*;
import src.main.java.com.bookmyshow.model.*;
import src.main.java.com.bookmyshow.service.JASONParsing;

// import src.com.bookmyshow.model.*;

public class BookmyshowApplication{
    public static void main(String[] args){
        String filePath = "/Users/cb-it-01-1958/Documents/Bookmyshow/src/inputs/owner_login.jason";
        User user1= (Owner)JASONParsing.parseDataOwner(filePath);
        System.out.println("::::::::::::");
        System.out.println("User: " + user1.getUserID());
        System.out.println("Password: " + user1.getPassword());
        System.out.println("::::::::::::");
        System.out.println();

        OwnerDAO ownerDAO = new OwnerDAO();
        List<User> users=ownerDAO.getAllOwners();
        for (User u:users){
            System.out.println(u);
            System.out.println();
        }

        User user=ownerDAO.getOwner("user123");
        System.out.println(user);
    }

}
package src.main.java.com.bookmyshow;

import src.main.java.com.bookmyshow.model.Customer;
import src.main.java.com.bookmyshow.model.ShowDetails;
import src.main.java.com.bookmyshow.model.User;

import java.time.LocalDate;
import java.util.List;

public class tempMain {
    public void tempFun(){
        String filePath = "/Users/cb-it-01-1959/Documents/Assign-3/bookmyshow-addrija/src/inputs/customer_login.json";
        User customer1 = new Customer();
        customer1 = ((Customer)customer1).login(customer1);
        System.out.println("::::::::::::");
        System.out.println(customer1);
        System.out.println("::::::::::::");
        List<ShowDetails> shows1 = Customer.getShowsOnDate(LocalDate.parse("2023-09-01"),"pvr-b");
        System.out.println(shows1.size());

        List<ShowDetails> shows2 = Customer.getShowsInDateRange(LocalDate.parse("2023-09-01"),LocalDate.parse("2023-09-10"),"pvr-b");
        System.out.println(shows2.get(0));

        List<ShowDetails> shows3 = Customer.getShowsForMovieInDateRange(LocalDate.parse("2023-09-01"),LocalDate.parse("2023-09-10"),"barbie");
        System.out.println(shows3.get(0));

        List<ShowDetails> shows4 = Customer.getShowsForMovieOnDate(LocalDate.parse("2023-09-01"),"barbie");
        System.out.println(shows4.size());
    }
}

package src.main.java.com.bookmyshow;
import java.io.*;
import java.sql.SQLException;

public class BookmyshowApplication {
    public static void main(String[] args) throws NumberFormatException, IOException, SQLException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("....................WELCOME TO BOOK MY SHOW.......................");
        boolean valid=false;
        int choice;
        System.out.println("Enter 1 to sign as owner");
        System.out.println("Enter 2 to sign as customer");
        while(valid==false){
            choice =Integer.parseInt(br.readLine());
            switch(choice){
                case 1:{
                    valid=true;
                    System.out.println("Welcome owner");
                    BookmyshowOwner obj=new BookmyshowOwner();
                    obj.ownerStart();
                    break;
                    }
                case 2:{
                    valid=true;
                    System.out.println("Welcome customer");
                    BookmyshowCustomer obj=new BookmyshowCustomer();
                    obj.customerStart();
                    break;
                }
               default:{
                    System.out.println("Invalid choice, try again");
               }
            }
        }
    }   
}

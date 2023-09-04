package src.main.java.com.bookmyshow;
// import src.com.bookmyshow.model.*;
import java.io.*;
import java.sql.*;

public class JDBCmain {
	public static void main(String[] args) throws Exception
	{
		String url
			= "jdbc:mysql://localhost:3306/bookmyshow"; // table details
            
		String username = "root"; // MySQL credentials
		String password = "";

		Class.forName(
			"com.mysql.cj.jdbc.Driver"); // Driver name

		Connection con = DriverManager.getConnection(
			url, username, password);

		System.out.println(
			"Connection Established successfully");

		Statement st = con.createStatement();
		String tableName = "Persons";

		// Execute a SELECT query to fetch all data from the table
		String sqlQuery = "SELECT * FROM " + tableName;
		ResultSet rs = st.executeQuery(sqlQuery); // Execute query
		System.out.println(rs);

		int columnCount = rs.getMetaData().getColumnCount();
		
		for (int i = 1; i <= columnCount; i++) {
			System.out.print(rs.getMetaData().getColumnName(i) + "\t");
		}
		System.out.println();


		// Print table data
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				System.out.print(rs.getString(i) + "\t");
			}
			System.out.println();
		}
		st.close(); // close statement
		con.close(); // close connection
		System.out.println("Connection Closed....");
	}
}

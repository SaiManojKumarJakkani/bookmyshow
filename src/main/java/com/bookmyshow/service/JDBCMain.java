package src.main.java.com.bookmyshow.service;

import java.sql.*;

public class JDBCMain {//this connects with DB and gets data back from DB
	public static void getDataJDBC(String sqlQuery, String tableName) throws Exception
	{
		String url = "jdbc:mysql://localhost:3306/bookmyshow"; // table details
		String username = "root"; // MySQL credentials
		String password = "";
		Class.forName("com.mysql.cj.jdbc.Driver"); // Driver name
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connection Established successfully");
		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(sqlQuery); // Execute query
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
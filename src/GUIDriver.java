import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

public class GUIDriver{

	public static Connection my_conn;
	
	public static void main(String[] args) {

		my_conn=establishConnection();
		Login launch1 = new Login();
		launch1.launchloginScreen();
		
		//System.out.println("i am here");
		
		//Login launch2 = new Login();
		//launch2.loginScreen();
	
}
	private static Connection establishConnection() {
		
		Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/InvManagement", "postgres", "postgrealan");
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	      return c;
		
	}
}
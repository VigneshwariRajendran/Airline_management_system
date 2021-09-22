package ar;
import java.sql.*;

public class dbconnect {

	final String driver="com.mysql.cj.jdbc.Driver";
	final String DBpath="jdbc:mysql://localhost:3306/airline";
	
	static Connection con;
	
	
	public void connect() throws Exception,SQLException
	{
		
		Class.forName(driver);
		System.out.println("connecting......");
		con=DriverManager.getConnection(DBpath,"root","root");
		
	}
	public void closeconnection() throws Exception,SQLException 
	{
		
		con.close();

	}
	
	 public Connection getConnection() 
	 {
		return con;
	}
	

	
}

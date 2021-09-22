package ar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class cancel extends dbconnect {
		
		static Scanner sc=new Scanner(System.in);
		
		static void cancel() throws Exception 
		{ 
			try
			{
				//Connection con=dbconnect.getCon();
				 Statement stmt=con.createStatement();  
				 
				System.out.println("Enter your ticket's PNR Number");
				String pnr=sc.next();
				
				PreparedStatement pst = con.prepareStatement("select *from passenger where Pnr_no=?");
				pst.setString(1, pnr);
				ResultSet result = pst.executeQuery();

				
				 while(result.next()) 
				 { 
					  System.out.println("your ticket information:");
					  System.out.printf(result.getString(1)+"  "+result.getString(2)+"  "
					  +result.getString(3)+"  " +result.getString(4)+"  " +result.getString(5)+"  "
					  +result.getDate(6)+ "  " +result.getString(7)+"  " +result.getString(8)
					  );
					  
					  System.out.println();
					
				  }
				 
				System.out.println("Enter yes to confirm cancellation");
				String confirm=sc.next();
				
				if(confirm.equalsIgnoreCase("yes")) 
				{

					String sql = "DELETE FROM passenger WHERE (Pnr_no = ?)";
					PreparedStatement statement = con.prepareStatement(sql);
					statement.setString(1, pnr);
					
					int row = statement.executeUpdate();
					if(row > 0)
					{
						System.out.println("Your ticket is cancelled...!");
					}
					else
					{
						System.out.println("Sorry....! there is no such reservations!");
					}
					System.out.println("_________________________________________________");
					statement.close();
					
				}
				else 
				{
					System.out.println("We are glad you changed your mind!");
				}
				
				
			}
			catch (Exception e) 
			{
				System.out.println("Some error occured......"  +e);
			}
		}
}

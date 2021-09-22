package ar;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class book extends dbconnect {

	// function to generate a random string of length n
	static String getAlphaNumericString(int n) {

			// chose a Character random from this String
			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
	
			// create StringBuffer size of AlphaNumericString
			StringBuilder sb = new StringBuilder(n);

			for (int i = 0; i < n; i++) 
			{
				// generate a random number between
				// 0 to AlphaNumericString variable length
				int index = (int) (AlphaNumericString.length() * Math.random());
	
				// add Character one by one in end of sb
				sb.append(AlphaNumericString.charAt(index));
	
			}

	    	return sb.toString();
	}

	// Get the size n
	final static int n = 8;

static void book() throws Exception {
	    String contact;
		//to get the userid from account class in order to use it to display passenger table
		String userid = Account.userid;
		Statement stmt = con.createStatement();
		
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("Please enter 'b' to book and 'c' to cancel ticket");
		Scanner s = new Scanner(System.in);
		char op = s.next().charAt(0);

		// to book ticket
		if (op == 'b')
		{
			System.out.println("Enter departure location: ");
			String dep = s.next();

			System.out.println("Enter arrival location: ");
			String arr = s.next();

			System.out.println("Enter the date of journey");
			System.out.println("Note: To enter date use this format 'yyyy-mm-dd'");
			String doj = s.next();
			
			// check if flight exist then ask further details
			PreparedStatement pst3 = con.prepareStatement("SELECT EXISTS(select *from trip where (departure,arrival,doj)=(?,?,?))");
			pst3.setString(1, dep);
			pst3.setString(2, arr);
			pst3.setString(3, doj);
			
			ResultSet rs=pst3.executeQuery();
			
			int exist=0;
			while(rs.next()) 
			{
				exist=rs.getInt(1);
				
			 } 
			 if(exist>0)
			{
				PreparedStatement pst = con.prepareStatement("select *from trip where (departure,arrival,doj)=(?,?,?)");
				pst.setString(1, dep);
				pst.setString(2, arr);
				pst.setString(3, doj);
	
				ResultSet result = pst.executeQuery();
				Formatter fmt=new Formatter();
				System.out.println("-----------------------------------------------------------------------------------------------------------------------");
				System.out.format("%5s %10s %-10s %8s %10s %8s %10s %8s %8s %8s", "Flight_no", "Flight_name", "DOJ",
						"Departure", "Depart_time", "Arrival", "Arrival_time", "Seats Available", "Base_price",
						"Final_price");
	
				System.out.println();
	
				System.out.println("-----------------------------------------------------------------------------------------------------------------------");
	
				while (result.next()) 
				{
				
					System.out.format(result.getString(1) + "  " + result.getString(2) + "  " + result.getDate(3) + "   "
							+ result.getString(4) + "  " + result.getTime(5) + "  " + result.getString(6) + "  "
							+ result.getTime(7) + "  " + result.getInt(8) + "  " + result.getDouble(9) + "  "
							+ result.getDouble(10));
					
					System.out.println();
				}
				
	
				System.out.println("-----------------------------------------------------------------------------------------------------------------------");
	
				System.out.println("Enter flight number you wish to travel");
				String fnum = s.next();
	
				System.out.println("Enter number of tickets to book");
				int num = s.nextInt();
				
				
				for (int i = 1; i <= num; i++) 
				{
	
					System.out.println("Enter passenger" + i + " name: ");
					String pname = s.next();
	
					System.out.println("Enter passenger" + i + " mail_id: ");
					String mail;
					boolean b = false;
					do
					{
					mail=s.next();
					Pattern p=Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
					Matcher m=p.matcher(mail);
					b=m.matches();
						if(b==true)
						{
							//System.out.println("Valid email");
							//statement1.setString(3, mail);
					    }
						else
						{
							System.out.println("Invalid email... please enter the valid email");
							
						}
					}while(b==false);

	
					System.out.println("Enter passenger" + i + " contact_no: ");
					
					do {
						contact = s.next();
						if(contact.length()==10) 
							break;
						
						else
							System.out.println("Invalid contact no");
					}while(contact.length()!=10);
					
					System.out.println("Enter passenger" + i + " Id_number: ");
					String id=s.next();
					
					// to get random pnr for each ticket
					String rand = book.getAlphaNumericString(n);
	
					// to store passenger information in passenger table
	
					String sql1 = "INSERT INTO passenger (Username,Pnr_no, Passenger_name, Mail_id,Id_num,Contact_no, Flight_no,DOJ,Departure,Arrival) VALUES (?,?,?, ?, ?, ?, ?, ?,?,?)";
					PreparedStatement statement1 = con.prepareStatement(sql1);
					statement1.setString(1, userid);
					statement1.setString(2, rand);
					statement1.setString(3, pname);
					statement1.setString(4, mail);
					statement1.setString(5, id);
					statement1.setString(6, contact);
					statement1.setString(7, fnum);
					statement1.setString(8, doj);
					statement1.setString(9, dep);
					statement1.setString(10, arr);
					statement1.addBatch();
					
					int[] updateCounts = statement1.executeBatch();
					
					
					System.out.println("______________________________________________________________________________________________");
	
				}
				//to decrement number of seats
				
				  String sql4="UPDATE trip \r\n" + "SET no_seats = no_seats - ?\r\n" +
				  "WHERE f_no = ?\r\n" + "and no_seats > 0 "; 
				  PreparedStatement statement2 =con.prepareStatement(sql4);
				  statement2.setInt(1, num);
				  statement2.setString(2, fnum); 
				  int row=statement2.executeUpdate();
				 
				 
				
				// to select price from particular column and multiply as per the number of tickets
	
				PreparedStatement pst1 = con.prepareStatement("select final_price from trip where f_no=?");
	
				pst1.setString(1, fnum);
				ResultSet result1 = pst1.executeQuery();
	
				while (result1.next()) 
				{
					double s_id = result1.getDouble(1);
					double total = s_id * num;
					System.out.printf("The total price for your tickets: %.2f", +total);
					System.out.println();
				}
				System.out.println();
				
				//to get time fields from trip database table
				String sql3 = "UPDATE passenger, trip\r\n" + 
				  		"SET passenger.Depart_time = trip.dept_time,\r\n" + 
				  		"      passenger.Arrival_time = trip.arr_time\r\n" + 
				  		"WHERE passenger.Flight_no = trip.f_no;";
				Statement statement3 = con.createStatement();
	            int res = statement3.executeUpdate(sql3);
	             
	             //to display the reservation to user
	             System.out.println( "Your reservations details:");
	             System.out.println();
	             PreparedStatement pst2 = con.prepareStatement("  SELECT \r\n" + 
	             		"    Pnr_no,Passenger_name,Contact_no,Flight_no,DOJ,Departure,Depart_time,Arrival,Arrival_time\r\n" + 
	             		"FROM\r\n" + 
	             		"    (SELECT \r\n" + 
	             		"        *\r\n" + 
	             		"    FROM\r\n" + 
	             		"        passenger\r\n" + 
	             		"    ORDER BY P_no DESC\r\n" + 
	             		"    LIMIT ?) lastNrows_subquery\r\n" + 
	             		"ORDER BY P_no ");
	             pst2.setInt(1, num);
	            
	             ResultSet re = pst2.executeQuery();
	             	System.out.println("Pnr_number  Passenger_name contact_no Flight_number Date of journey Departure Depart_time Arrival Arrival_time");
	             	System.out.println();
	 				while (re.next()) 
	 					{
	 						System.out.println(re.getString(1) + "    " + re.getString(2) + "       " + re.getString(3) + "     "
							+ re.getString(4) + "    "  + re.getDate(5) + "     " + re.getString(6) + "    "
	 								+re.getTime(7) + "    " + re.getString(8)+"   "+re.getTime(9));
	 						System.out.println();
	 					}
		}
			 else 
			 {
				 System.out.println("no such flights");
			 }
	} 
	else if (op == 'c') 
	{
		cancel.cancel();
	}
	else
		System.out.println("invalid option.....try again");
}

}

//to add regex to mail

 // System.out.println("Mail Id"); 
  
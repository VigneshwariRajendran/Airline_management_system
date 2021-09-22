package ar;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class admin extends dbconnect{
	static Scanner sc=new Scanner(System.in);
	static void display() throws Exception {
		System.out.println();
		System.out.println("Press 1 to add new flight details to airline database");
		System.out.println("Press 2 to delete any flight from airline database");
		System.out.println("Press 3 to update flight details on airline database");
		int opt=sc.nextInt();
		switch(opt) 
		{
		case 1:
			while(true) {
				System.out.println("Flight_number: ");
				String flight_no=sc.next();
				System.out.println("Flight_name: ");
				String flight_name=sc.next();
				System.out.println("Date of journey: ");
				String doj=sc.nextLine();
				System.out.println("Departure location: ");
				String loc=sc.next();
				System.out.println("Departure Time: ");
				String dtime=sc.next();
				System.out.println("Arrival location: ");
				String loc2=sc.next();
				System.out.println("Arrival Time: ");
				String atime=sc.next();
				System.out.println("Number of seats: ");
				int seats=sc.nextInt();
				System.out.println("Base price:");
				float bprice=sc.nextFloat();
				float temp=(float) (bprice*0.18);
				float fprice=bprice+temp;
				String sql="insert into trip values(?,?,STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?,?,?)";		
				PreparedStatement statement1 = con.prepareStatement(sql);
				statement1.setString(1, flight_no);
				statement1.setString(2, flight_name);
				statement1.setString(3, doj);
				statement1.setString(4, loc);
				statement1.setString(5, dtime);
				statement1.setString(6, loc2);
				statement1.setString(7, atime);
				statement1.setInt(8, seats);
				statement1.setFloat(9, bprice);
				statement1.setFloat(10, fprice);
				statement1.addBatch();
				
				int[] updateCounts = statement1.executeBatch();
				System.out.println("flight data added successfully");
				System.out.println();
				System.out.println("Enter 'y' to continue to add flights, 'n' to exit...");
				char op=sc.next().charAt(0);
				Character.toLowerCase(op);
				if(op=='n')
					break;
					
			}
			break;
		case 2:
				System.out.println("Enter Flight_number to delete");
				String flight_num=sc.next();
				System.out.println("Enter 'yes' to confirm deletion");
				String confirm=sc.next();
					if(confirm.equalsIgnoreCase("yes")) 
						{
							String sql2 = "DELETE FROM trip WHERE (f_no = ?)";
							PreparedStatement statement = con.prepareStatement(sql2);
							statement.setString(1, flight_num);
							
							int row = statement.executeUpdate();
							if(row > 0)
							{
								System.out.println("Flight is deleted from database.....!");
							}
							else
							{
								System.out.println("Sorry....! there is no such flights available in database!");
							}
						}
					else
						System.out.println("Flight is not deleted....!");
				break;
				
		case 3:
			System.out.println("Enter the flight number in which you need to change the number of seats: ");
			String f_num=sc.next();
			System.out.println("Enter the change in number of seats");
			int seat=sc.nextInt();
			String sql3="update trip set no_seats=? where f_no=?";
			PreparedStatement statement1 = con.prepareStatement(sql3);
			statement1.setInt(1, seat);
			statement1.setString(2, f_num);
			int row = statement1.executeUpdate();
			System.out.println("Number of seats updated");
			break;
		default:
			System.out.println("Please enter the correct choice....and try again! ");
			break;
		}
	}
		static void admin() throws Exception {
			display();
		}
}

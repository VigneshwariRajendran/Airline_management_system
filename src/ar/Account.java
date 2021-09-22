package ar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends dbconnect{
	static Scanner sc=new Scanner(System.in);
	static Statement smt;
	static String userid;
	void login() throws Exception 
	
	{
		
			 //book b=new book();
		    
		     System.out.println("Enter username: ");
			 userid = sc.next();  
			 System.out.print("Enter the password: ");  
			 String userpwd = sc.next();  
			 
			 Statement stmt=con.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from users where username='" + userid + "' and password='" + userpwd + "'"); 
			 
			  if (rs.next())
			  { 
			   System.out.println("Welcome  " + userid); 
			  
			  // if(opt=='b')
			   book.book();
			  }
			  
			  else 
			  { 
			   System.out.println("Invalid user name and password"); 
			  }
	}

	static void admin() throws Exception 
	{
		admin.admin();
		
	}
	
	public static void main(String[] args) throws Exception {
		
		  
		 Account a=new Account();
		 a.connect();//db is connected
		 smt = a.getConnection().createStatement(); // con.createstatement(con is returned from getconnection)
		 con=a.getConnection();
		 
				 
		try {
			
			
			System.out.println("---------Welcome to Air-line reservation---------");
			System.out.println("-------------------------------------------------");
			System.out.println("Enter 'u' if you are user or 'a'if you are admin");
			char login=sc.next().charAt(0);
			
			if(login=='u') 
			{
				//System.out.println("Welcome");
				System.out.println("Enter 1 to login and 2 to signup");
				int option=sc.nextInt();
				String mail;
				switch(option) 
				{
				//existing user login
				case 1:
					  a.login();
					  break;
					  
				//signup new user
				case 2:
					try {
						//Connection con=dbconnect.getCon(); 
						System.out.println();
						String sql1 = "INSERT INTO users (username, password, email, contact_no, age, id_type, id_num) VALUES (?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement statement1 = con.prepareStatement(sql1);
						int data=0;
						
						System.out.println("Enter username: ");
						String name=sc.next();
						
						System.out.println("Enter password");
						String pwd=sc.next();
						
						System.out.println("Enter mail address");
						boolean b = false;
						do
						{
						mail=sc.next();
						Pattern p=Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
						Matcher m=p.matcher(mail);
						b=m.matches();
							if(b==true)
							{
								//System.out.println("Valid email");
								statement1.setString(3, mail);
						    }
							else
							{
								System.out.println("Invalid email... please enter the valid email");
								
							}
						}while(b==false);

						System.out.println("Enter  contact number");
						String cno=sc.next();
						
						System.out.println("Enter your age");
						int age=sc.nextInt();
						
						System.out.println("Enter id_type");
						System.out.println("eg: Aadhar,Pan,Voter_id etc...");
						String idtype=sc.next();
						
						System.out.println("Enter id_num");
						String idnum=sc.next();
						
						statement1.setString(1, name);
						statement1.setString(2, pwd);
						//statement1.setString(3, mail);
						statement1.setString(4, cno);
						statement1.setInt(5,age);	
						statement1.setString(6, idtype);
						statement1.setString(7, idnum);
						
						 data=statement1.executeUpdate();
						 if(data > 0) 
						 { 
							 System.out.println("Sign_up successfull");
							 //again login and go to user actions
							 a.login();
							 
						 }
				
						 else 
						 { 
							 System.out.println("Something went wrong.....please try again"); 
						 }
					}
					catch(SQLException e) 
					{
						 System.out.println(e);
					}
					 break;
				default:
					System.out.println("option doesn't exist.....please try again");
					break;
				}
			}
			else if(login=='a') 
			{
				System.out.println("Enter username: ");
				String admin=sc.next();
				System.out.println("Enter password: ");
				String pwd=sc.next();
				if(admin.equals("admin")&& pwd.equals("admin"))
				{
					System.out.println("Welcome Admin");
					a.admin();
				}
				else {
					System.out.println("Invalid username or password......try agin later");
				}
				
			}
		}
		catch(Exception e) 
		{
			System.out.println("Invalid data.....please check your data and try again " +e);
		}
		
	}


}

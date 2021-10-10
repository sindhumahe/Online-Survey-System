package userjdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;
import java.sql.Statement;



public class SurveyDatabase {
	
	private static final String SurveyId = null;
	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);
	
	int survey_id;
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SurveyDatabase surveyDatabase = new SurveyDatabase();
		
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String dbURL = "jdbc:mysql://localhost:3309/Sabarish";
			String username = "root";
			String password = "Sindhu#05root";
			
		connection = DriverManager.getConnection(dbURL, username, password);
		
		while(true) {
			
		System.out.println("  ");
		System.out.println("  ");
		System.out.println("WELCOME TO ONLINE SURVEY SYSTEM");
		System.out.println();
		System.out.println("1.CREATE SURVEY");
		System.out.println("2.TAKE SURVEY");
		System.out.println("3.SHOW ALL SURVEY");
		System.out.println("4.EXIT");
		System.out.println("");
		System.out.println("");
		System.out.println("ENTER CHOICE:");
		int choice = scanner.nextInt();
				
			switch (choice) {
				
			case 1:
					surveyDatabase.insertRecord();
					break;
					
			case 2:
				surveyDatabase.selectRecord();
				break;
					
			case 3:
				surveyDatabase.selectallrecord();
				break;				
					
			case 4:
			      System.out.println("Thanks for visting");
                  System.exit(0);
                  
            default:
               	  System.out.println("Your choice is invalid");
               	  break;    
					
				}
		}

	}
	
	private void insertRecord() throws SQLException {
		
		PreparedStatement ps=connection.prepareStatement("INSERT INTO sabarish.sindhu(Username,PASSWORD,Surveyname)values (?,?,?)");
		
		System.out.println("********************NEW REGISTER*************************************");
		
		String yourname, password, surveyname;
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("ENTER YOURNAME :");
		yourname = s.nextLine();
		ps.setString(1, yourname);
		
		System.out.println("PASSWORD :");
		password = s.nextLine();
		ps.setString(2, password);
		
		System.out.println("ENTER SURVEYNAME :");
		surveyname = s.nextLine();
		ps.setString(3, surveyname);
		
		int rows = ps.executeUpdate();
		
		if (rows > 0) {
			System.out.println("**********Registered Successfully!!**********");
		}

	   String sql = "SELECT * FROM sabarish.sindhu WHERE Username =\""+yourname+"\"";
	   
	   Statement statement  = connection.createStatement();
		
		ResultSet result = statement.executeQuery(sql);
		
		 if(result.next()) {
			 System.out.println("Your survey id is : "+result.getInt("SurveyId")+"                          "+"Surveyname is: "+surveyname);
			 System.out.println("");
			 System.out.println("");
			 survey_id=result.getInt("SurveyId");
			 
		 }
				 
		 System.out.println("Create your survey questions here");
		 System.out.println("");
		
		 int question;
		 
		  Scanner s1 = new Scanner(System.in);
		  System.out.println("How many questions do you want to create? :");
		  question = Integer.parseInt(s1.nextLine());
		  System.out.print("");
		  
		    Scanner s2 = new Scanner(System.in);
			System.out.println("How many options do you want to create? :");
			int option = Integer.parseInt(s2.nextLine());
			System.out.print("");
		
		  for(int i=1; i<=question; i++) {	
			  
			  PreparedStatement ps1=connection.prepareStatement("INSERT INTO sabarish.question(question,answer,sindhu_fkey)values (?,?,?)");
				
				 String q="question " +i +":";
				 System.out.println(q);
				 String qa =s2.nextLine();
				 
				 String  sol = q+qa;
				  String poi = "";
				 ps1.setString(1, sol);
				  
				 for(int j=1; j<=option; j++) {
					  					 					  
						Scanner s3 = new Scanner(System.in);
					    String o="Enter option" +j +":";
					    System.out.println(o);
					    String oa=s3.nextLine();
					 
					    String finalsol=j+")"+oa;
						System.out.print("");
												
						poi = poi +"       "+finalsol;
				        ps1.setString(2, poi);
				        ps1.setInt(3, survey_id);
				      				        
				  }			  
				 
				  ps1.executeUpdate();
								
		  } 
		
	System.out.println("----------------------------------your survey is created!------------------------------------------");
						
	}
	
	public void selectRecord() throws SQLException{
		
		System.out.println("***************** Your survey is ready*******************");
	    System.out.println("Enter your surveyid :");
	    
		int surveyid = scanner.nextInt();	
		
		String sql = "SELECT * FROM Sabarish.question WHERE sindhu_fkey = "+ surveyid;
				
		Statement Statement = connection.createStatement();
	    ResultSet result = Statement.executeQuery(sql);
		
		while(result.next()) {
			String question = result.getString("question");
			String answer = result.getString("answer");
			
			System.out.println(question);
			System.out.println(answer);
			System.out.println("Enter your Answer :");
			scanner.nextInt();
			
			}
		
	}
	
public void selectallrecord() throws SQLException{
		
		System.out.println("***************** Your All surveys is ready*******************");
	  
		String sql = "SELECT * FROM Sabarish.question";
				
		Statement Statement = connection.createStatement();
	    ResultSet result = Statement.executeQuery(sql);
		
		while(result.next()) {
			String question = result.getString("question");
			String answer = result.getString("answer");
			int id = result.getInt("sindhu_fkey");
			
			System.out.println("Your id is : "+id);
			System.out.println(question);
			System.out.println(answer);
		
		}
		
	}

}

package JDBC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class ReadfileDB {
	public static void main(String[] args) throws SQLException {
		
		final String Db_URL = "jdbc:mysql://localhost:3306/ipldetails";
		 final String user ="root";
		 final String passcode ="Connect@123";
			 try {
				 		Connection conn  =null;       
				 		Statement stmt =null;
				 		PreparedStatement prepStmt = null;
				 		conn = DriverManager.getConnection(Db_URL, user, passcode);
		
				 		Scanner scanner = new Scanner(System.in);
			  
			 BufferedReader Readingvalue = new BufferedReader(
					 new FileReader("C:\\Users\\abishek.murugesan\\Desktop\\Cricket_Players.txt"));
			 BufferedWriter bw = new BufferedWriter(
                     new FileWriter("C:\\Users\\abishek.murugesan\\Desktop\\IPL_List.txt"));
             String Values =null;
          
             String tid ,tname,pname,pscore;
             //Read the values from file
             while((Values = Readingvalue.readLine()) != null)// valued inserted in database
             {
	            	  String[] s = Values.trim().split("\t");
	            	  tid =s[0];
	            	  tname= s[1];
	            	  pname =s[2];
	            	  System.out.print("Enter the "+pname+" 's Score: \n");
	            	  pscore =scanner.nextLine();
	            
		             prepStmt=conn.prepareStatement("INSERT into teamdetails (TeamId, TeamName,PlayerName,PlayerScore) "
		             + "values (?, ?, ?, ?)");
		             prepStmt.setString(1,tid);
		             prepStmt.setString(2,tname);
		             prepStmt.setString(3,pname);
		             prepStmt.setString(4,pscore);
		             prepStmt.executeUpdate();
		             
             }
             System.out.println("Values inserted successfully");
             
             //Write the values to file
             stmt = conn.createStatement();
             System.out.println("\n");
		    
		      ResultSet result= stmt.executeQuery("SELECT TeamId,TeamName,PlayerName,PlayerScore FROM teamdetails  group by TeamName,PlayerName ORDER BY TeamId,PlayerScore ASC;");
                
		      	System.out.println("File write is loading........");
		             while(result.next()) {
		            	 String Tid = result.getString("TeamId");
		            	 String Tname = result.getString("TeamName");
		            	 String Pname = result.getString("PlayerName");
		            	 String Pscore = result.getString("PlayerScore");
		            	 bw.write("\n" +Tid +"\t"+ Tname+"\t"+ Pname +"\t         "+Pscore);
		             }
             
		             Readingvalue.close();
				     conn.close();
				     prepStmt.close();
			         bw.close();
	                scanner.close();
	                result.close();
	                
			 }catch (Exception e) {
        e.printStackTrace();
			 }
		}
	}




package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Employee {

//A common method to connect to the DB
private Connection connect(){
	
	 Connection con = null;
	 try{
		 Class.forName("com.mysql.jdbc.Driver");

		 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electroemp", "root", "");
	 }
	 catch (Exception e){
		 e.printStackTrace();
	 	}
	 	
	 	return con;
	 }
	
//Method to insert notices to the system
public String insertEmployee(String name, String address, String phone, String nic){
	
	 String output = "";
	 
	 try{
		 
		 Connection con = connect();
	
		 if (con == null){
			 return "Error while connecting to the database for inserting."; 
			}
	 
		 // create a prepared statement
		 String query = "insert into employee(`EID`,`Name`,`Address`,`Phone`,`NIC`)" + " values (?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, name);
		 preparedStmt.setString(3, address);
		 preparedStmt.setString(4, phone);
		 preparedStmt.setString(5, nic);
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		  
		 output = "Employee details successfully inserted ";
	 }
	 	catch (Exception e){	
	 			output = "Error while inserting.";
	 			System.err.println(e.getMessage());
	 		}
	 	
	 return output;
}
	


public String readEmployee(){
	
	 String output = "";
	
	 try{
		 Connection con = connect(); 
		 if (con == null){
			 return "Error while connecting to the database for reading."; }
	
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Name</th><th>Address</th>" +
				 "<th>Phone</th>" +
				 "<th>NIC</th>" +
				 "<th>Update</th><th>Remove</th></tr>";

		 String query = "select * from employee";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 
		 // iterate through the rows in the result set
		 while (rs.next()){			 
			 String EID = Integer.toString(rs.getInt("EID"));
			 String name = rs.getString("Name");
			 String address = rs.getString("Address");
			 String phone = rs.getString("Phone");
			 String nic = rs.getString("NIC");
			 	
			 // Add into the html table
			 output += "<tr><td>" + name + "</td>";
			 output += "<td>" + address + "</td>";
			 output += "<td>" + phone + "</td>";
			 output += "<td>" + nic + "</td>";
			 
			 // buttons
			 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
				+ "<td><form method='post' action='notices.jsp'>"
				+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
				+ "<input name='EID' type='hidden' value='" + EID
				+ "'>" + "</form></td></tr>";
		 }
		 	
		 con.close();
		 // Complete the html table
		 output += "</table>";
	 }
	 	catch (Exception e){
	 		output = "Error while reading the employee details.";
	 		System.err.println(e.getMessage());
	 	}
	 
	 	return output;
}
	

public String updateEmployee(String EID, String name, String address, String phone, String nic){
	
		 String output = "";
		
		 try{
			  Connection con = connect();
			  if (con == null){
				  return "Error while connecting to the database for updating."; }
		
			  // create a prepared statement
			  String query = "UPDATE employee SET Name=?,Address=?,Phone=?,NIC=? WHERE EID=?";
		 	  PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 	  // binding values
		 	  preparedStmt.setString(1, name);
		 	  preparedStmt.setString(2, address);
		 	  preparedStmt.setString(3, phone);
		 	  preparedStmt.setString(4, nic);
		 	  preparedStmt.setInt(5, Integer.parseInt(EID));
		 
		 	  // execute the statement
		 	  preparedStmt.execute();
		 	  con.close();
		 	  output = "Employee details successfully updated ";
		 }
		 	catch (Exception e){
		 			output = "Error while updating.";
		 			System.err.println(e.getMessage());
		 	}
		 	
		 return output;
}
		


public String deleteEmployee(String EID){
	
		 String output = "";
		 
		 try{	 
			 Connection con = connect();
			  if (con == null){
				  return "Error while connecting to the database for deleting."; }
		 
			  // create a prepared statement
			  String query = "delete from employee where EID=?";
			  PreparedStatement preparedStmt = con.prepareStatement(query);
		 
			  // binding values
			  preparedStmt.setInt(1, Integer.parseInt(EID));
		 
			  // execute the statement
			  preparedStmt.execute();
			  con.close();
			  output = "Employee details  successfully deleted";
		 }
		 	catch (Exception e){
		 			output = "Error while deleting.";
		 			System.err.println(e.getMessage());
		 	  }
		 	
		 return output;
  } 

}
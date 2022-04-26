package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notice {

	//A common method to connect to the DB
private Connection connect(){
	
	 Connection con = null;
	 try{
		 Class.forName("com.mysql.jdbc.Driver");

		 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbitems", "root", "");
	 }
	 catch (Exception e){
		 e.printStackTrace();
	 	}
	 	
	 	return con;
	 }
	
//Method to insert notices to the system
public String insertNotice(String title, String description, String branch){
	
	 String output = "";
	 
	 try{
		 
		 Connection con = connect();
	
		 if (con == null){
			 return "Error while connecting to the database for inserting."; 
			}
	 
		 // create a prepared statement
		 String query = "insert into notices(`ID`,`Title`,`Description`,`Branch`)" + " values ( ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, title);
		 preparedStmt.setString(3, description);
		 preparedStmt.setString(4, branch);
		 
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 
		 output = "Inserted successfully";
	 }
	 	catch (Exception e){	
	 			output = "Error while inserting the item.";
	 			System.err.println(e.getMessage());
	 		}
	 	
	 return output;
}
	

public String readNotice(){
	
	 String output = "";
	
	 try{
		 Connection con = connect(); 
		 if (con == null){
			 return "Error while connecting to the database for reading."; }
	
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Title</th><th>Description</th>" +
				 "<th>Branch</th>" +
				 "<th>Update</th><th>Remove</th></tr>";

		 String query = "select * from notices";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 
		 // iterate through the rows in the result set
		 while (rs.next()){			 
			 String ID = Integer.toString(rs.getInt("ID"));
			 String title = rs.getString("Title");
			 String description = rs.getString("Description");
			 String branch = rs.getString("Branch");
	
			 // Add into the html table
			 output += "<tr><td>" + title + "</td>";
			 output += "<td>" + description + "</td>";
			 output += "<td>" + branch + "</td>";
			 
			 // buttons
			 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
				+ "<td><form method='post' action='notices.jsp'>"
				+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
				+ "<input name='ID' type='hidden' value='" + ID
				+ "'>" + "</form></td></tr>";
		 }
		 	
		 con.close();
		 // Complete the html table
		 output += "</table>";
	 }
	 	catch (Exception e){
	 		output = "Error while reading the items.";
	 		System.err.println(e.getMessage());
	 	}
	 
	 	return output;
}
	
public String updateNotice(String ID, String title, String description, String branch){
	
		 String output = "";
		
		 try{
			  Connection con = connect();
			  if (con == null){
				  return "Error while connecting to the database for updating."; }
		
			  // create a prepared statement
			  String query = "UPDATE notices SET Title=?,Description=?,Branch=? WHERE ID=?";
		 	  PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 	  // binding values
		 	  preparedStmt.setString(1, title);
		 	  preparedStmt.setString(2, description);
		 	  preparedStmt.setString(3, branch);
		 	  preparedStmt.setInt(4, Integer.parseInt(ID));
		 
		 	  // execute the statement
		 	  preparedStmt.execute();
		 	  con.close();
		 	  output = "Updated successfully";
		 }
		 	catch (Exception e){
		 			output = "Error while updating the item.";
		 			System.err.println(e.getMessage());
		 	}
		 	
		 return output;
}
		

public String deleteNotice(String ID){
	
		 String output = "";
		 
		 try{	 
			 Connection con = connect();
			  if (con == null){
				  return "Error while connecting to the database for deleting."; }
		 
			  // create a prepared statement
			  String query = "delete from notices where ID=?";
			  PreparedStatement preparedStmt = con.prepareStatement(query);
		 
			  // binding values
			  preparedStmt.setInt(1, Integer.parseInt(ID));
		 
			  // execute the statement
			  preparedStmt.execute();
			  con.close();
			  output = "Deleted successfully";
		 }
		 	catch (Exception e){
		 			output = "Error while deleting the item.";
		 			System.err.println(e.getMessage());
		 	  }
		 	
		 return output;
  } 

}


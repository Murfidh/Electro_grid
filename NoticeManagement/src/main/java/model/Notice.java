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
			e.printStackTrace();}
	 		return con;
	    }
	
//--------Method to create new Notice(Company Informations to the customer)-----------------	
public String insertNotice(String heading, String description, String branch){
	 
		String output = "";
	 
		try{
				Connection con = connect();
				if (con == null){
					return "Error while connecting to the database for inserting."; 
					}
	 
	  // the query to insert into notice table and prepared statement
	 String query = " insert into notices(`ID`,`Heading`,`Description`,`Branch`)" + " values (?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, heading);
	 preparedStmt.setString(3, description);
	 preparedStmt.setString(4, branch);
	 // execute the statement
	 preparedStmt.execute();
	 
	 //close the connection
	 con.close();
	 //success
	 output = "Inserted successfully";
	 
		}catch (Exception e){
				//failure
				output = "Error while inserting the item.";
				System.err.println(e.getMessage());
	 }
		return output;
 }
	
//********************************************************************
//The method to read all notices in the notice table -------------------
public String readNotices(){
		
	 String output = "";
	 
	 //checking db connectivity
	 try{
		 Connection con = connect();
	 
		 if (con == null){
		 return "Error while connecting to the database for reading."; 
		 
	 }
	 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Heading</th><th>Description</th>" +
	 "<th>Branch</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

	 //the query to select all the records from the table
	 String query = "select * from notices";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 
	 // iterate through the rows in the result set
	 while (rs.next()){
	 
	//reading values from results set	 
	 String ID = Integer.toString(rs.getInt("ID"));
	 String Heading = rs.getString("Heading");
	 String Description = rs.getString("Description");
	 String Branch = rs.getString("Branch");
	 
	 // Add into the html table
	 output += "<tr><td>" + Heading + "</td>";
	 output += "<td>" + Description + "</td>";
	 output += "<td>" + Branch + "</td>";
	 
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='items.jsp'>"
	 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + ID
	 + "'>" + "</form></td></tr>";
 }
	 //close the db connection
	 con.close();
	 
	 // Complete the html table
	 output += "</table>";
   }
	 
	 catch (Exception e){
		 //failure
		 output = "Error while reading the items.";
		 System.err.println(e.getMessage());
	 }
	 	
	 	return output;
}


//***********************************************************************************
//the method to update notice record by ID ------------------------------------------
public String updateNotice(String ID, String heading, String description, String branch){
		
	  String output = "";
		 
	  try{
		  
		 Connection con = connect();
		 
		 if (con == null){
			 
			 return "Error while connecting to the database for updating."; 
			 
		 }
		 
		 // create a prepared statement
		 //The query to update notice record 
		 String query = "UPDATE notices SET Heading=?,Description=?,Branch=? WHERE ID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, heading);
		 preparedStmt.setString(2, description);
		 preparedStmt.setString(3, branch);
		 preparedStmt.setInt(4, Integer.parseInt(ID));
		 
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 //success
		 output = "Updated successfully";
		 
 }
		 catch (Exception e){
			 
			 output = "Error while updating the item.";
			 System.err.println(e.getMessage());
		 }
	  		return output;
	}
		

//********************************************************************************
//The method to delete a record by ID----------------------------------------------------
public String deleteNotice(String ID){
		
		 String output = "";
		 try{
			 
			 Connection con = connect();
			 if (con == null){
				 return "Error while connecting to the database for deleting."; 
				 }
		 
		 // create a prepared statement
		 // query to delete record from notice table using ID	 
		 String query = "delete from notices where ID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setInt(1,Integer.parseInt(ID));
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

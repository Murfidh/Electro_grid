package com;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;

import model.Employee;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/Employee") 			
public class EmployeeService {
	
	//notice object 
	Employee employeeObj = new Employee(); 
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)   
	public String readEmployee(){
	 return employeeObj.readEmployee();
	 
  }

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)   
	@Produces(MediaType.TEXT_PLAIN)					   	
	public String insertEmployee(@FormParam("Name") String Name,
			@FormParam("Address") String Address,
			@FormParam("Phone") String Phone,
			@FormParam("NIC") String NIC)
	{
		String output = employeeObj.insertEmployee(Name, Address, Phone,NIC);
		return output;
  }



	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateEmployee(String employeeData){
		
		//Convert the input string to a JSON object
		JsonObject employeeObject = new JsonParser().parse(employeeData).getAsJsonObject();
		
		//Read the values from the JSON object
		String EID = employeeObject.get("EID").getAsString();
		String Name = employeeObject.get("Name").getAsString();
		String Address = employeeObject.get("Address").getAsString();
		String Phone = employeeObject.get("Phone").getAsString();
		String NIC = employeeObject.get("NIC").getAsString();
		String output = employeeObj.updateEmployee(EID, Name, Address, Phone,NIC) ;
	
		return output;
  }

	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteEmployee(String employeeData){
		
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(employeeData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String EID = doc.select("EID").text();
	 String output = employeeObj.deleteEmployee(EID);
	
	 return output;
  }


}

package com;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;

import model.Notice;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/Notice")
public class NoticeServices {
	
	Notice noticeObj = new Notice(); 
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readNotices(){
	 return noticeObj.readNotice();
	 
  }
		
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertNotice(@FormParam("Title") String Title,
			@FormParam("Description") String Description,
			@FormParam("Branch") String Branch)
	{
		String output = noticeObj.insertNotice(Title, Description, Branch);
		return output;
  }

	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateNotice(String noticeData){
		
		//Convert the input string to a JSON object
		JsonObject noticeObject = new JsonParser().parse(noticeData).getAsJsonObject();
		
		//Read the values from the JSON object
		String ID = noticeObject.get("ID").getAsString();
		String Title = noticeObject.get("Title").getAsString();
		String Description = noticeObject.get("Description").getAsString();
		String Branch = noticeObject.get("Branch").getAsString();
		String output = noticeObj.updateNotice(ID, Title, Description, Branch) ;
	
		return output;
  }

	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteNotice(String noticeData){
		
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(noticeData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String ID = doc.select("ID").text();
	 String output = noticeObj.deleteNotice(ID);
	
	 return output;
  }


}





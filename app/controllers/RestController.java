package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.*;
import play.*;
import views.html.*;
import java.util.*;
import Service.*;
import Model.*;

public class RestController extends Controller {
    
    private IdentificationsService identificationsService = IdentificationsService.getInstance();

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	//Do something with the identification
    	Boolean flag=identificationsService.addIdentifier(json);
    
        return ok();
    }
    
    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    		//Do something with the company
    		
    	Boolean flag= identificationsService.addCompany(json);
    //	return ok(index.render(flag.toString()));
    return ok();
}
    public Result identifications() {
    	
    	//Get the current identification
    	List<Identifier> openList=identificationsService.getOpenIdentifications();
    	
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
    	
         return ok(Json.toJson(openList));
    }
    
    }

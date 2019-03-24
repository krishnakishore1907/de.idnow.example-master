package Service;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import java.util.*;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.*;
import play.*;
import play.mvc.*;
import views.html.*;
import play.libs.Json;
import java.math.BigDecimal;
import controllers.*;
import Model.Company;
import Model.Identifier;
import Model.PriorityModel;
import Service.*;

/*import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
*/

public class IdentificationsService {

    private Map<Integer, Company> companies;
    private List<PriorityModel> priorityModelList;
    private static IdentificationsService identificationsService=null;
    
    private IdentificationsService(){ 
        
        companies= new HashMap<Integer, Company>();
        priorityModelList= new ArrayList<PriorityModel>();
    }
    
    public static IdentificationsService getInstance(){
        
        if(identificationsService==null){
        identificationsService=new IdentificationsService();
        }
        return identificationsService;
    }
    
    public static void cleanUpResources(){
        identificationsService=null;
    }
    
    public Boolean addCompany(JsonNode json) {
    	
    	Company company = Json.fromJson(json, Company.class);
    	try{
    	companies.put(company.getId(),company);
    	return true;
    	}
    	catch(Exception e){
    	    e.printStackTrace();
    	    return false;
    	}
    }
     
     public Boolean addIdentifier(JsonNode json) {
    	
    	Identifier identifier = Json.fromJson(json, Identifier.class);
    	PriorityModel priorityModel=new PriorityModel();
    	try{
    	Float priority = calculateUserPriority(identifier);
    
    	priorityModel.setPriority(priority);
    	priorityModel.setIdentifier(identifier);
    	priorityModelList.add(priorityModel);
    	return true;
    	}
    	catch(Exception e){
    	e.printStackTrace();
    	return false;
     }
    }
     
     public Float calculateUserPriority(Identifier identifier){
     
        float current_sla_percentage=0.0f,pending_sla_percentage=0.0f, priority=0.0f;
        Company companyDetails=companies.get(identifier.getCompanyId());
        current_sla_percentage=companyDetails.getCurrent_sla_percentage();
        pending_sla_percentage=1-current_sla_percentage;
         
        int company_sla=companyDetails.getSla_time();
        int user_waiting_time= identifier.getWaiting_time();
         
        priority=pending_sla_percentage/(company_sla-user_waiting_time);
        return priority;
    }
    
    public List<Identifier> getOpenIdentifications() {
        
        //Compute correct order
       List<Identifier> identifiers=priorityModelList.stream().sorted((p,q)->(p.getPriority()<q.getPriority())?1:-1).map(p->p.getIdentifier()).collect(Collectors.toList());
	   return identifiers;
	}
    }
    
    
    
    
    
    
package student.server;
//REST Services are organised into Applications
//An application is a set of related resources (Restlets) 
//possibly linked together through a Router.
//The Router maps each resource path to a Restlet that represents a single resource.
//This class combines multiple restlets 
//into a single application that will be deployed on a single node.

import java.util.HashMap;
import java.util.Map;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.routing.Router;

import com.google.gson.Gson;

import student.core.StudentRecord;

public class StudentApplication extends Application { //Must extend Application
	static Map<String, StudentRecord> records = new HashMap<String,StudentRecord>();
	static Gson gson = new Gson();
	
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/student", new Restlet() {
		    public void handle(Request request, Response response) {
		        if (request.getMethod().equals(Method.GET)) { //Get request
		            String out = "["; boolean first = true;
		            for (String key : records.keySet()) {
		                if (first) first = false; else out += ",";
		                String url = request.getResourceRef().getPath() + "/student/" + key;
		                out += "{\"student_number\" : \"" + key + "\", \"link\":\"" + url + "\"}";
		            }
		            response.setEntity(out + "]", MediaType.APPLICATION_JSON);
		        } else if (request.getMethod().equals(Method.POST)) { //Post request
		            String json = request.getEntityAsText();
		            StudentRecord record = gson.fromJson(json, StudentRecord.class);
		            if (!records.containsKey(record.student_number)) {
		                records.put(record.student_number, record);
		                String url=request.getResourceRef().getPath()+"/student/"+record.student_number;
		                response.setEntity("{\"student_number\" : \"" + record.student_number + 
		                        "\", \"link\":\"" + url + "\"}", MediaType.APPLICATION_JSON);
		            } else response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
		        } else response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
		    }
		});
		router.attach("/student/{student_number}", new Restlet() { //{student number} is a parameter of the URL
		    @Override
		    public void handle(Request request, Response response) {
		        String id = (String) request.getAttributes().get("student_number");
		        if (records.containsKey(id)) {
		            response.setStatus(Status.SUCCESS_OK);

		            if (request.getMethod().equals(Method.GET)) {
		                response.setEntity(gson.toJson(records.get(id)), MediaType.APPLICATION_JSON);
		                response.setStatus(Status.SUCCESS_OK);
		            } else if (request.getMethod().equals(Method.PUT)) {
		                StudentRecord record =
		                    gson.fromJson(request.getEntityAsText(), StudentRecord.class);
		                record.merge(records.get(id));
		                records.put(record.student_number, record);
		                response.setStatus(Status.SUCCESS_NO_CONTENT);
		            } else if (request.getMethod().equals(Method.DELETE)) {
		                records.remove(id);
		                response.setStatus(Status.SUCCESS_NO_CONTENT);
		            }
		        } else {
		            response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		        }
		    }
		});
		
		return router;
	}

}

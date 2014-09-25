package vit.mini.vitfaculty;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonLib {
	public static int employee_id;
	public static String employee_name;
	public static String SERVER = "http://vitacademics-faculty-dev.herokuapp.com/api/";
	
	public static String checkError(String json) throws JSONException{
		JSONObject root = new JSONObject(json);
		String error_status = root.getString("result");
		if(error_status.equals("success")){
			return root.getString("token");
		}
		else if(error_status.equals("failure")){
			return "";
		}
		else{
			return "error";
		}
	}
	public static String checkPostedError(String json) throws JSONException{
		JSONObject root = new JSONObject(json);
		String error_status = root.getString("result");
		if(error_status.equals("success")){
			return "success";
		}
		else if(error_status.equals("failure")){
			return "";
		}
		else{
			return "error";
		}
	}
}

package vit.mini.vitfaculty.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vit.mini.vitfaculty.data.Course;
import vit.mini.vitfaculty.data.Student;
import android.content.Context;
import android.content.SharedPreferences;

public class CentralParser {

	Context context;
	SharedPreferences prefs;
	
	public CentralParser(Context context){
		this.context = context;
		prefs = context.getSharedPreferences("app_settings", 0);
	}
	
	public List<Course> getCoursesFromCache(){
		
		List<Course> courses = new ArrayList<Course>();
		String json = prefs.getString("classes","");
		try {
			JSONObject root = new JSONObject(json);
			JSONArray classes = root.getJSONArray("classes");
			for(int i = 0;i<classes.length();i++){
				JSONObject _class = classes.getJSONObject(i);
				int class_number = Integer.parseInt(_class.getString("cnum"));
				String class_name = _class.getString("name");
				String course_code = _class.getString("code");
				String venue = _class.getString("venue");
				String slot = _class.getString("slot");
				JSONArray students = _class.getJSONArray("students");
				List<Student> _students = new ArrayList<Student>();
				for(int j = 0;j<students.length();j++){
					JSONObject json_student = students.getJSONObject(j);
					String regno = json_student.getString("regno");
					JSONArray attendance_status = json_student.getJSONArray("attended");
					List<String> attended_dates = new ArrayList<String>();
					for(int k = 0;k<attendance_status.length();k++){
						attended_dates.add(attendance_status.getString(k));
					}
					Student student = new Student(regno,class_number,attended_dates);
					_students.add(student);
				}
				Course course = new Course(class_number, class_name, venue, _students, course_code, slot);
				courses.add(course);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return courses;
	}
	
}

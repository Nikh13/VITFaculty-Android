package vit.mini.vitfaculty.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vit.mini.vitfaculty.data.Course;
import vit.mini.vitfaculty.data.Marks;
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
	
	public boolean checkDay(String day,int class_number){
		String schedule = prefs.getString("schedule", "");
		boolean flag = false;
		try{
			JSONObject json_obj = new JSONObject(schedule);
			JSONArray classes  = json_obj.getJSONArray(day);
			for(int i = 0;i<classes.length();i++){
				if(classes.getInt(i)!=0&&classes.getInt(i)==class_number)
					flag = true;
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag)
			return true;
		else
			return false;
	}
	
	public Marks getMarks(String json_marks,String type){
		Marks marks = null;
		try {
			JSONArray marks_array = new JSONArray(json_marks);
			List<String> regnos = new ArrayList<String>();
			List<Integer> marks_list = new ArrayList<Integer>();
			for(int i=0;i<marks_array.length();i++){
				JSONObject student = marks_array.getJSONObject(i);
				String regno = student.getString("regno");
				int mark = student.getInt(type);
				regnos.add(regno);
				marks_list.add(mark);
			}
			marks = new Marks(regnos,marks_list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(marks!=null){
			return marks;
		}
		else{
			return null;
			
		}
	}
	
}

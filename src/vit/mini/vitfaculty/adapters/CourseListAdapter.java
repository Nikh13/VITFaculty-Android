package vit.mini.vitfaculty.adapters;

import java.util.List;

import vit.mini.vitfaculty.R;
import vit.mini.vitfaculty.data.Course;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CourseListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Course> courses;
	
	public CourseListAdapter(Context context, List<Course> courses){
		this.context = context;
		this.courses = courses;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return courses.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View item = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		
		item = inflater.inflate(R.layout.course_item, null);
		Course course = courses.get(position);
		
		TextView course_name = (TextView)item.findViewById(R.id.course_name);
		TextView course_code = (TextView)item.findViewById(R.id.course_code);
		TextView course_room = (TextView)item.findViewById(R.id.course_room);
		
		course_name.setText(course.getCourse_name());
		course_code.setText(course.getCourse_code());
		course_room.setText(course.getRoom());
		
		
		course_name.setTextColor(context.getResources().getColor(android.R.color.black));
		course_code.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
		course_room.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
		
		return item;
	}
	

	
}

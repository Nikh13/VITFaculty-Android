package vit.mini.vitfaculty.adapters;

import java.util.List;

import vit.mini.vitfaculty.R;
import vit.mini.vitfaculty.data.Attendance;
import vit.mini.vitfaculty.data.Student;
import vit.mini.vitfaculty.fragments.StudentsPage;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class StudentListAdapter extends BaseAdapter {

	Context context;
	List<Student> students;
	public List<Attendance> attendance_list;

	public StudentListAdapter(Context ctx, List<Student> students,
			List<Attendance> attendance_list) {
		this.context = ctx;
		this.students = students;
		this.attendance_list = attendance_list;
		for(Attendance a:attendance_list)
			Log.i("Adapter", a.getStatus()+"");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return students.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = LayoutInflater.from(context);

//		if (position == students.size()) {
//			View submit = inflater.inflate(R.layout.submit, null);
//			return submit;
//		}

		View item = null;
		item = inflater.inflate(R.layout.student_list_item, null);
		Student student = students.get(position);
		final Attendance attendance = attendance_list.get(position);

		TextView regno = (TextView) item.findViewById(R.id.regno);

		regno.setText(student.getRegistration_number());
		regno.setTextColor(context.getResources().getColor(
				android.R.color.black));

//		TextView name = (TextView) item.findViewById(R.id.name);
//
//		name.setText(student.getName());
//		name.setTextColor(context.getResources().getColor(
//				android.R.color.darker_gray));

		final Button status = (Button) item.findViewById(R.id.status);

		
		if (!attendance.getStatus()){
			status.setText("Absent");
			status.setTextColor(context.getResources().getColor(
					android.R.color.holo_red_dark));
		}
			
		else if (attendance.getStatus()){
			status.setText("Present");
			status.setTextColor(context.getResources().getColor(
					android.R.color.holo_green_dark));
		}
		status.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StudentsPage.changed = true;
				if (!attendance.getStatus()) {
					status.setText("Present");
					status.setTextColor(context.getResources().getColor(
							android.R.color.holo_green_dark));
					attendance.setStatus(true);
					for(Attendance a:attendance_list)
						Log.i("Adapter", a.getStatus()+"");
				} else if (attendance.getStatus()) {
					status.setText("Absent");
					status.setTextColor(context.getResources().getColor(
							android.R.color.holo_red_dark));
					attendance.setStatus(false);
				}
			}
		});
		
		
		return item;
	}

}

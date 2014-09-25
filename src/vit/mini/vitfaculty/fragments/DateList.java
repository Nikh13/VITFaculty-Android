package vit.mini.vitfaculty.fragments;

import java.util.ArrayList;
import java.util.List;

import vit.mini.vitfaculty.MainActivity;
import vit.mini.vitfaculty.R;
import vit.mini.vitfaculty.adapters.DateListAdapter;
import vit.mini.vitfaculty.data.Attendance;
import vit.mini.vitfaculty.data.Date;
import vit.mini.vitfaculty.data.Student;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DateList extends Fragment {

	ListView date_listview;
	List<Date> date_list;
	DateListAdapter adapter;
	Context context;
	SharedPreferences prefs;
	int class_number;
	List<Student> students;
	
	public DateList(Context context, List<Student> students, int class_number) {
		this.students = students;
		prefs = context.getSharedPreferences("app_settings", 0);
		this.class_number = class_number;
		this.context = context;
		Log.i("DateList", "Fragment Instantiated");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View date_list_layout = inflater.inflate(R.layout.date_list, null);
		date_listview = (ListView)date_list_layout.findViewById(R.id.date_listview);
		date_list = new ArrayList<Date>();
		getDates();
		setDateListView();
		Log.i("DateList", "Fragment View: "+date_list_layout);
		return date_list_layout;
																					
	}

	private void setDateListView() {
		// TODO Auto-generated method stub
		adapter = new DateListAdapter(getActivity().getApplicationContext(), date_list);
		date_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position == 1 || position>2){
					MainActivity activity = (MainActivity) getActivity();
					FragmentManager fm = activity.getSupportFragmentManager();
					fm.beginTransaction().add(R.id.container, new StudentsPage(getActivity().getApplicationContext(),students,class_number))
					.addToBackStack("stack").commit();
				}
				
			}
		});
		date_listview.setAdapter(adapter);
	}

	private void getDates() {
		// TODO Auto-generated method stub
		date_list.add(new Date("2014-09-24",false));
		date_list.add(new Date("2014-09-22",true));
		date_list.add(new Date("2014-09-19",true));
		date_list.add(new Date("2014-09-17",true));
		date_list.add(new Date("2014-09-15",false));
		
	}
	
	
}

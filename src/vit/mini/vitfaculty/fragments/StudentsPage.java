package vit.mini.vitfaculty.fragments;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import vit.mini.vitfaculty.CommonLib;
import vit.mini.vitfaculty.HttpManager;
import vit.mini.vitfaculty.MainActivity;
import vit.mini.vitfaculty.R;
import vit.mini.vitfaculty.adapters.StudentListAdapter;
import vit.mini.vitfaculty.data.Attendance;
import vit.mini.vitfaculty.data.Student;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class StudentsPage extends Fragment {

	List<Student> students;
	List<Attendance> attendance_list;
	StudentListAdapter student_adapter;
	ListView student_list;
	Fragment frag;
	public static boolean changed;
	Context context;
	static String POST_ATTENDANCE = "postattendance";
	SharedPreferences prefs;
	int class_number;
	
	public StudentsPage(Context context, List<Student> students, int class_number) {
		this.students = students;
		prefs = context.getSharedPreferences("app_settings", 0);
		attendance_list = new ArrayList<Attendance>();
		for (Student s : students) {
			Attendance attendance = new Attendance(s.getRegistration_number());
			attendance_list.add(attendance);
		}
		this.class_number = class_number;
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		frag = this;
		View student_page = inflater.inflate(R.layout.students_page, null);
		student_list = (ListView) student_page.findViewById(R.id.students_list);

		setStudentListView();

		setHasOptionsMenu(true);

		return student_page;
	}

	private void setStudentListView() {
		// TODO Auto-generated method stub
		student_adapter = new StudentListAdapter(getActivity(), students,
				attendance_list);
		student_list.setAdapter(student_adapter);
		// student_list.setOnItemClickListener(onStudentClick);
	}

	private void setAttendance() {
		attendance_list = student_adapter.attendance_list;
		PostAttendance pa  = new PostAttendance();
		pa.execute();
	}

	public void closePage() {
		setHasOptionsMenu(false);
		MainActivity activity = (MainActivity) getActivity();
		FragmentManager fm = activity.getSupportFragmentManager();
		fm.beginTransaction().remove(frag).commit();
	}

	// private OnItemClickListener onStudentClick = new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // TODO Auto-generated method stub
	//
	//
	// if (position == students.size()) {
	// setAttendance();
	//
	// }
	// }
	// };

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_itemdetail, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		case R.id.save:
			if (changed) {
				setAttendance();
			} else
				Toast.makeText(getActivity(), "No Changes Made",
						Toast.LENGTH_SHORT).show();
			return true;
		case R.id.mark_all:
			for (Attendance a : attendance_list) {
				a.setStatus(true);
			}
			student_list.invalidateViews();
			return true;
		case R.id.unmark_all:
			for (Attendance a : attendance_list) {
				a.setStatus(false);
			}
			student_list.invalidateViews();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class PostAttendance extends AsyncTask<Void, Void, Void> {

		ProgressDialog pd;
		String result;

		@Override
		public void onPreExecute() {
			pd = new ProgressDialog(MainActivity.context);
			pd.setMessage("Posting...");
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpPost post = new HttpPost(CommonLib.SERVER + POST_ATTENDANCE);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
			nvp.add(new BasicNameValuePair("token", prefs
					.getString("token", "")));
			for (Attendance a : attendance_list) {
				if (a.getStatus())
					nvp.add(new BasicNameValuePair(
							"present", a.getRegno()));
				else if(!a.getStatus())
					nvp.add(new BasicNameValuePair(
							"absent", a.getRegno()));
			}
			nvp.add(new BasicNameValuePair("cnum",Integer.toString(class_number)));
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			nvp.add(new BasicNameValuePair("date", sdf.format(cal.getTime())));
			try {
				post.setEntity(new UrlEncodedFormEntity(nvp));
				HttpResponse response = HttpManager.execute(post);
				result = EntityUtils.toString(response.getEntity());
				Log.i("Students Page", "Posted Response: "+result);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void onPostExecute(Void v) {
			pd.dismiss();
			try {
				if (checkResult(result)) {
					Toast.makeText(getActivity(),
							"Changes have been submitted", Toast.LENGTH_SHORT)
							.show();
					closePage();
				} else {
					Toast.makeText(getActivity(),
							"Something went wrong, Try later",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean checkResult(String result) throws JSONException {
		// TODO Auto-generated method stub
		if (CommonLib.checkPostedError(result).equals("success"))
			return true;
		else {
			return false;
		}
	}
}

package vit.mini.vitfaculty.fragments;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vit.mini.vitfaculty.CommonLib;
import vit.mini.vitfaculty.HttpManager;
import vit.mini.vitfaculty.MainActivity;
import vit.mini.vitfaculty.R;
import vit.mini.vitfaculty.adapters.CourseListAdapter;
import vit.mini.vitfaculty.data.Course;
import vit.mini.vitfaculty.parsers.CentralParser;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CoursesPage extends Fragment {

	List<Course> courses;
	Fragment frag;
	CourseListAdapter course_adapter;
	ListView course_list;
	CentralParser parser;
	SharedPreferences prefs;
	static String GET_CLASSES = "getclasses";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		frag = this;
		parser = new CentralParser(getActivity().getApplicationContext());
		prefs = getActivity().getSharedPreferences("app_settings", 0);
		boolean check = checkCache();
		if (check)
			LoadData();
		else{
			LoadClasses lc = new LoadClasses();
			lc.execute();
		}
		View course_page = inflater.inflate(R.layout.courses_page, null);
		course_list = (ListView) course_page.findViewById(R.id.course_list);
		if (courses != null)
			setCourseList();
		return course_page;
	}

	private void LoadData() {
		courses = parser.getCoursesFromCache();
		Log.i("Courses Page", "Course 0 " + courses.get(0));
	}

	private boolean checkCache() {
		// TODO Auto-generated method stub

		if (!prefs.getString("classes", "").equals(""))
			return true;
		else
			return false;
	}

	OnItemClickListener onCourseClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			MainActivity activity = (MainActivity) getActivity();
			FragmentManager fm = activity.getSupportFragmentManager();
			fm.beginTransaction().add(R.id.container, new DateList(getActivity().getApplicationContext(),courses.get(position).getStudents(),courses.get(position).getClass_number()))
					.addToBackStack("stack").hide(frag).commit();
		}
	};

	private void setCourseList() {
		course_adapter = new CourseListAdapter(getActivity(), courses);
		course_list.setAdapter(course_adapter);
		course_list.setOnItemClickListener(onCourseClick);
	}

	public class LoadClasses extends AsyncTask<Void, Void, Void> {

		ProgressDialog pd;
		String result;

		@Override
		public void onPreExecute() {
			pd = new ProgressDialog(MainActivity.context);
			pd.setMessage("Loading...");
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpPost post = new HttpPost(CommonLib.SERVER + GET_CLASSES);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
			nvp.add(new BasicNameValuePair("token", prefs
					.getString("token", "")));
			try {
				post.setEntity(new UrlEncodedFormEntity(nvp));
				HttpResponse response = HttpManager.execute(post);
				result = EntityUtils.toString(response.getEntity());
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
			if (checkResult(result)){
				addToCache(result);
				LoadData();
				setCourseList();
			}
			else
				Toast.makeText(getActivity(), "Try reloading after a bit", Toast.LENGTH_SHORT).show();
			
		}
	}
	public void addToCache(String json){
		Editor editor = prefs.edit();
		editor.putString("classes", json);
		editor.commit();
	}

	public boolean checkResult(String result) {
		JSONObject root;
		JSONArray classes = null;
		try {
			root = new JSONObject(result);
			classes = root.getJSONArray("classes");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (classes != null)
			return true;
		else
			return false;
	}
}

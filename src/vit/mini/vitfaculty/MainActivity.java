package vit.mini.vitfaculty;

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
import org.json.JSONException;

import vit.mini.vitfaculty.fragments.CoursesPage;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	Dialog alertDialog;
	SharedPreferences prefs;
	public static Context context;
	static String GET_TOKEN = "getaccesstoken";
	static String GET_TIMETABLE = "gettimetable";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		prefs = getSharedPreferences("app_settings", 0);
		
		// ViewPager pager = (ViewPager)findViewById(R.id.pager);
		// PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
		// pager.setAdapter(adapter);
		
		boolean check = checkLogin();
		if(!check)
			buildDialog();
		else
			openCoursesPage();
	}

	public boolean checkLogin() {
		int empid = prefs.getInt("emp_id", 0);
		if(empid>0)
			return true;
		else
			return false;		
	}

	public void buildDialog() {
		
		alertDialog = new Dialog(context);
		alertDialog.setContentView(R.layout.login_dialog);
		alertDialog.setTitle("Login");
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);

		final EditText employee_id = (EditText) alertDialog
				.findViewById(R.id.employee_id);
		final EditText password = (EditText) alertDialog
				.findViewById(R.id.password);

		Button login = (Button) alertDialog.findViewById(R.id.dialog_login);
		Button dismiss = (Button) alertDialog.findViewById(R.id.dialog_cancel);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Login login = new Login();
				String[] params = {employee_id.getText().toString(),password.getText().toString()};
				login.execute(params);
			}
		});

		dismiss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismissDialog();
			}
		});

		showDialog();
	}

	protected void addToCache(int empid, String password,String token) {
		// TODO Auto-generated method stub
		Editor editor = prefs.edit();
		editor.putInt("emp_id", empid);
		editor.putString("password", password);
		editor.putString("token", token);
		editor.commit();
	}
	protected void addScheduleToCache(String schedule) {
		// TODO Auto-generated method stub
		Editor editor = prefs.edit();
		editor.putString("schedule", schedule);
		editor.commit();
	}

	public void dismissDialog() {
		alertDialog.dismiss();
		openCoursesPage();
	}

	public void showDialog() {
		alertDialog.show();
	}

	public void openCoursesPage() {
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new CoursesPage()).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class Login extends AsyncTask<String, String, Void>{
		
		ProgressDialog pd;
		String result=null;
		String empid = null;
		String schedule = null;
		String password = null;
		
		@Override
		public void onPreExecute(){
			pd = new ProgressDialog(context);
			pd.setMessage("Verifying...");
			pd.show();
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			empid = params[0];
			password = params[1];
			HttpPost post = new HttpPost(CommonLib.SERVER+GET_TOKEN);
			HttpPost post1 = new HttpPost(CommonLib.SERVER+GET_TIMETABLE);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("empid", empid));
			nvp.add(new BasicNameValuePair("passwordhash", password));
			List<NameValuePair> nvp1 = new ArrayList<NameValuePair>(2);
			nvp1.add(new BasicNameValuePair("token", prefs.getString("token","")));
			try {
				post.setEntity(new UrlEncodedFormEntity(nvp));
				HttpResponse response = HttpManager.execute(post);
				HttpResponse response1 = HttpManager.execute(post1);
				result = EntityUtils.toString(response.getEntity());
				schedule = EntityUtils.toString(response1.getEntity());
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
		public void onPostExecute(Void v){
			pd.dismiss();
			try {
				String token = CommonLib.checkError(result);
				if(!token.equals("")){
					dismissDialog();
					addToCache(Integer.parseInt(empid),password,token);
					addScheduleToCache(schedule);
				}
				else{
					Toast.makeText(getApplicationContext(),
							"Invalid Credentials", Toast.LENGTH_SHORT).show();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

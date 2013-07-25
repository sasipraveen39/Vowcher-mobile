package com.vowcher.mobile_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class LoginActivity  extends Activity {
	public final static String EXTRA_MESSAGE = "com.Vowcher.mobile_app.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	private class LoginTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... arg0) {
			String sid="";
			try {
				sid =postData(arg0[0],arg0[1]);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sid;
		}
		
		private String postData(String username,String password) throws ClientProtocolException, IOException {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://10.0.2.2:8080/expense/server/login.jsp");
		    
	        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("username", username));
	        nameValuePairs.add(new BasicNameValuePair("password", password));
	        nameValuePairs.add(new BasicNameValuePair("mode", "sid"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String line="";
	        String sid="";
	        
	        while((line=br.readLine())!=null){
	        	sid += line;
	        }
	        return sid;
		}
	}
	
	public void requestLogin(View view) throws ClientProtocolException, IOException, JSONException {
		EditText uname = (EditText)findViewById(R.id.usernametext);
		String username = uname.getText().toString();
		EditText pwd = (EditText)findViewById(R.id.passwordtext);
		String password=pwd.getText().toString();
		
		LoginTask task = new LoginTask();
		String[] params = new String[2];
		params[0] = username;
		params[1] = password;
		String sid = task.doInBackground(params);
		if(sid.equals("AUTH_ERROR")){
			TextView error = (TextView)findViewById(R.id.errorMsg);
			error.setText("Invalid username or password");
			uname.requestFocus();
		}
		else {
			JSONObject json_obj = new JSONObject(sid);
			GlobalVars.userId = json_obj.getString("USERNAME");
			GlobalVars.userFullName = json_obj.getString("FULLNAME");
			GlobalVars.secureId = json_obj.getString("SECUREID");
			
			Intent intent = new Intent(this, DashboardActivity.class);
		    startActivity(intent);
		}
	}
}


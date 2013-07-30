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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardActivity extends Activity {
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
		welcomeText.setText("Welcome " + GlobalVars.userFullName);
		
		FetchNotificationTask task = new FetchNotificationTask();
		String notif=task.doInBackground();
		
		try {
			JSONObject json_obj = new JSONObject(notif);
			JSONArray json_arr = json_obj.getJSONArray("NOTIFICATIONS");
			
			String[] notifMsgs = new String[json_arr.length()];
			for(int i=0;i<json_arr.length();i++){
				JSONObject obj = json_arr.getJSONObject(i);
				notifMsgs[i] = obj.getString("NOTIF_MSG");
			}
			
			ListView list = (ListView)findViewById(R.id.notifList);
			NotificationListAdapter adapter = new NotificationListAdapter(this,notifMsgs);
			adapter.setJSONArray(json_arr);
			
			list.setAdapter(adapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private class FetchNotificationTask extends AsyncTask<String, Void, String> {
		
		protected String doInBackground(String... arg0) {
			String notif="";
			try {
				notif =postData();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return notif;
		}
		
		private String postData() throws ClientProtocolException, IOException {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://"+GlobalVars.ipAddress+":8080/expense/server/notification.jsp");
		    
	        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("username", GlobalVars.userId));
	        nameValuePairs.add(new BasicNameValuePair("sid", GlobalVars.secureId));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String line="";
	        String notif="";
	        
	        while((line=br.readLine())!=null){
	        	notif += line;
	        }
	        return notif;
		}	
	}
	
	public void signout(View v){
		AlertDialog.Builder	 alertDialogBuilder = new AlertDialog.Builder(context);
        
        alertDialogBuilder.setTitle("Vowcher");
        alertDialogBuilder
        		.setMessage("Are you sure you want to signout?")
        		.setCancelable(false)
        		.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
        			public void onClick(DialogInterface dialog,int id){
        				GlobalVars.userId = "";
        				GlobalVars.userFullName = "";
        				GlobalVars.secureId = "";
        				Intent intent = new Intent(context, LoginActivity.class);
        			    startActivity(intent);
        			}
        		})
        		.setNegativeButton("No",new DialogInterface.OnClickListener(){
        			public void onClick(DialogInterface dialog,int id){
        				
        			}
        		});
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
	}
}


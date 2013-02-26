package com.vowcher.mobile_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

public class Datepick extends Activity {
	public static final String DATE = "com.vowcher.mobile_app.MESSAGE"; 
	private String dt="";
	private Intent resultIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datepick);
		
	}
	
	 @Override
	 public void onBackPressed(){
		 resultIntent = new Intent();
		 DatePicker date = (DatePicker)findViewById(R.id.datePicker);
		 dt = date.getDayOfMonth()+"/"+(date.getMonth()+1)+"/"+date.getYear();
		 resultIntent.putExtra(DATE,dt);
		 setResult(Activity.RESULT_OK, resultIntent);
		 super.onBackPressed();
	 }	 
	 
	 @Override
	 protected void onPause(){
		 super.onPause();
		 finish();
	 }

}

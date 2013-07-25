package com.vowcher.mobile_app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DashboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
		welcomeText.setText("Welcome " + GlobalVars.userFullName);
	}
}

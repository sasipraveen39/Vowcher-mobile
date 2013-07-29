package com.vowcher.mobile_app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListElement extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_element);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_element, menu);
		return true;
	}

}

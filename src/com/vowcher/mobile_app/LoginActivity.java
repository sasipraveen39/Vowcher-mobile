package com.vowcher.mobile_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity  extends Activity {
	public final static String EXTRA_MESSAGE = "com.Vowcher.mobile_app.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	
	public void requestLogin(View view) {
		Intent intent = new Intent(this,VoucherAdd.class);
		EditText usernametxt = (EditText)findViewById(R.id.usernametext);
		String  username = usernametxt.getText().toString();
		intent.putExtra(EXTRA_MESSAGE,username);
		startActivity(intent);
	}
}

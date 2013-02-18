package com.vowcher.mobile_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VoucherAdd extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher_add);
	}

	public void showDatePicker(View view){
		Intent intent = new Intent(this,Datepick.class);
		startActivity(intent);
	}

}

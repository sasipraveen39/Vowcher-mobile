package com.vowcher.mobile_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class VoucherAdd extends Activity {
	public static final int DATE_REQUEST_CODE = 3;
	public static final int IMAGE_REQUEST_CODE = 2;
	private final int CAMERA_PICTURE = 1;
	private final int GALLERY_PICTURE = 2;
	private ImageView userPictureImageView;
	private Intent pictureActionIntent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher_add);

		// spinner
		Spinner voucherspinner = (Spinner) findViewById(R.id.VoucherTypespinner);
		ArrayAdapter<String> voucherspinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, android.R.id.text1);
		voucherspinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		voucherspinner.setAdapter(voucherspinnerAdapter);
		// add spinner items here
		voucherspinnerAdapter.add("voucher type 1");
		voucherspinnerAdapter.add("voucher type 2");
		voucherspinnerAdapter.add("voucher type 3");
		voucherspinnerAdapter.add("voucher type 4");

		voucherspinnerAdapter.notifyDataSetChanged();

	}

	public void showDatePicker(View view) {
		Intent intent = new Intent(this, Datepick.class);
		startActivityForResult(intent, DATE_REQUEST_CODE);
	}

	public void getImage(View view) {
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Upload Pictures Option");
		myAlertDialog.setMessage("How do you want to set your picture?");

		myAlertDialog.setPositiveButton("Gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						pictureActionIntent = new Intent(
								Intent.ACTION_GET_CONTENT, null);
						pictureActionIntent.setType("image/*");
						pictureActionIntent.putExtra("return-data", true);
						startActivityForResult(pictureActionIntent,
								GALLERY_PICTURE);
					}
				});

		myAlertDialog.setNegativeButton("Camera",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						pictureActionIntent = new Intent(
								android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(pictureActionIntent,
								CAMERA_PICTURE);
					}
				});
		myAlertDialog.show();
	}

	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (DATE_REQUEST_CODE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra(Datepick.DATE);
				EditText datetext = (EditText) findViewById(R.id.datetext);
				datetext.setText(newText);
				EditText desctext = (EditText) findViewById(R.id.descriptiontext);
				desctext.requestFocus();
			}
			break;
		}

		case (GALLERY_PICTURE): {
			Uri uri = data.getData();
			if (uri != null) {
				// User had pick an image.
				Cursor cursor = getContentResolver()
						.query(uri,
								new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
								null, null, null);
				cursor.moveToFirst();
				// Link to the image
				final String imageFilePath = cursor.getString(0);
				File photos = new File(imageFilePath);
				Bitmap b = decodeFile(photos);
				b = Bitmap.createScaledBitmap(b, 150, 150, true);
				userPictureImageView.setImageBitmap(b);
				cursor.close();
			} else {
				Toast toast = Toast.makeText(this, "No Image is selected.",
						Toast.LENGTH_LONG);
				toast.show();
			}

			break;
		}
		case (CAMERA_PICTURE): {
			if (data.getExtras() != null) {
				// here is the image from camera
				Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				userPictureImageView.setImageBitmap(bitmap);
			}
			break;

		}
		}
	}
}

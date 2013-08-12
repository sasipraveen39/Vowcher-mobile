package com.vowcher.mobile_app;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class NotificationListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private JSONArray notifs;
	
	public NotificationListAdapter(Context context, String[] values) {
		super(context, R.layout.list_element, values);
		this.context = context;
		this.values = values;
		this.notifs = new JSONArray();
	}
	
	public void setJSONArray(JSONArray notif_array){
		this.notifs=notif_array;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_element, parent, false);
		TextView notifMsgText = (TextView) rowView.findViewById(R.id.notifMsg);
		TextView timeText = (TextView)rowView.findViewById(R.id.timeUpdate);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.notifIcon);
		
		try {
			JSONObject notification = this.notifs.getJSONObject(position);
			String notifClass = notification.getString("NOTIF_CLASS");
			String notifMsg = notification.getString("NOTIF_MSG");
			String timeUpdate = notification.getString("NOTIF_TIME");
			
			notifMsgText.setText(this.values[position]);
			timeText.setText(timeUpdate);
			if(notifClass.equals("success")){
				imageView.setImageResource(R.drawable.success);
			}
			else if(notifClass.equals("error")){
				imageView.setImageResource(R.drawable.error);
			}
			else if(notifClass.equals("warning")){
				imageView.setImageResource(R.drawable.warning);
			}
			else if(notifClass.equals("info")){
				imageView.setImageResource(R.drawable.info);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return rowView;
	}
}
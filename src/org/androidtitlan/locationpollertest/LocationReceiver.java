package org.androidtitlan.locationpollertest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.commonsware.cwac.locpoll.LocationPoller;

public class LocationReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		File log=new File(Environment.getExternalStorageDirectory(),
		"MagicTaxi.txt");
		
		try {
			BufferedWriter out=new BufferedWriter(
														new FileWriter(log.getAbsolutePath(),
																						log.exists()));
			
			Location loc=(Location)intent.getExtras().get(LocationPoller.EXTRA_LOCATION);
			String msg;
			
			if (loc==null) {
				msg=intent.getStringExtra(LocationPoller.EXTRA_ERROR);
			}
			else {
//				msg=loc.toString();
//				String latitude = Double.toString(loc.getLatitude());
//				String longitude = Double.toString(loc.getLongitude());
				
				Double latitude = loc.getLatitude();
				Double longitude = loc.getLongitude();
				
				Log.e("some", "stuff");
				
				Intent in = new Intent(context,LocationPollerTest.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle bundle = new Bundle();        

//				bundle.putString("latitude",latitude);
//				bundle.putString("longitude",longitude);
				
				in.putExtra("latitude", latitude);
				in.putExtra("longitude", longitude);
//				in.putExtras(bundle);
				context.startActivity(in);
//				((Activity) context).startActivityForResult(in,0); 
								
				 
				 
				msg = latitude +","+ longitude;
			}
			
			if (msg==null) {
				msg="Invalid broadcast received!";
			}
			
			out.write(msg);
			out.write("\n");
			out.close();
		}
		catch (IOException e) {
			Log.e(getClass().getName(), "Exception appending to log file", e);
		}
	}	 

}

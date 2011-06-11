package org.androidtitlan.locationpollertest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Environment;
import android.util.Log;

import com.commonsware.cwac.locpoll.LocationPoller;

public class LocationReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		File log=new File(Environment.getExternalStorageDirectory(),
		"LocationLog.txt");
		
		try {
			BufferedWriter out=new BufferedWriter(
														new FileWriter(log.getAbsolutePath(),
																						log.exists()));
			
			out.write(new Date().toString());
			out.write(" : ");
			
			Location loc=(Location)intent.getExtras().get(LocationPoller.EXTRA_LOCATION);
			String msg;
			
			if (loc==null) {
				msg=intent.getStringExtra(LocationPoller.EXTRA_ERROR);
			}
			else {
				msg=loc.toString();
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

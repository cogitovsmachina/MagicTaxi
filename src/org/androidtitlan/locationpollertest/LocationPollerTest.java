package org.androidtitlan.locationpollertest;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.commonsware.cwac.locpoll.LocationPoller;

public class LocationPollerTest extends Activity {
//	private static final int PERIOD=300000; 	// 5 minutes
//	private static final int PERIOD = 60000; //1 minute
	private static final int PERIOD = 15000; //15 secs
	
	private AlarmManager mgr;
	private PendingIntent pi;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		
		mgr=(AlarmManager)getSystemService(ALARM_SERVICE);
		
		Intent i=new Intent(this, LocationPoller.class);
		
		i.putExtra(LocationPoller.EXTRA_INTENT,
							 new Intent(this, LocationReceiver.class));
		i.putExtra(LocationPoller.EXTRA_PROVIDER,
							 LocationManager.NETWORK_PROVIDER);
		
		pi=PendingIntent.getBroadcast(this, 0, i, 0);
		mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
											SystemClock.elapsedRealtime(),
											PERIOD,
											pi);
		
		Toast
		.makeText(this,
							"Location polling every 1 minute started",
							Toast.LENGTH_LONG)
		.show();          
		                                         
    }
	public void omgPleaseStop(View v) {
		mgr.cancel(pi);
		finish();
	}     
}
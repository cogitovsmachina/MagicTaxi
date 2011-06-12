package org.androidtitlan.locationpollertest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.commonsware.cwac.locpoll.LocationPoller;

public class LocationPollerTest extends Activity {
	
//	private static final int PERIOD = 60000; //1 minute
//	private static final int PERIOD = 15000; //15 secs
	private static final int PERIOD = 180000; //3 minutes
		
	private AlarmManager mgr;
	private PendingIntent pi;


	private double mLatitude;

	private String latitude; 
	private String longitude;

	private double mLongitude;




	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Bundle extras = getIntent().getExtras();
        
        double newString;
		if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
            	Toast.makeText(getApplicationContext(), "Connection problem, try again later", Toast.LENGTH_SHORT).show();
                newString= 1;
            } else {
                mLatitude= extras.getDouble("latitude");
                latitude = Double.toString(mLatitude);
                
                mLongitude = extras.getDouble("longitude");
                longitude = Double.toString(mLongitude);
            }
        } else {
            newString= (Double) savedInstanceState.getSerializable("latitude");
        }
        
		
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
							"Location polling every 3 minute started",
							Toast.LENGTH_LONG)
		.show();  
			
		doPost(); 
    }
    public void doPost(){
        try {        	
        	
        HttpClient client = new DefaultHttpClient();	
         String postURL = "http://magictaxi.heroku.com/position/create";
//       String postURL = "http://www.postbin.org/1g3lqnu";
        HttpPost post = new HttpPost(postURL); 
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
//            params.add(new BasicNameValuePair("latitude", "66.6"));
//            params.add(new BasicNameValuePair("longitude", "66.6")); 
          params.add(new BasicNameValuePair("latitude", latitude));
          params.add(new BasicNameValuePair("longitude",longitude));
          params.add(new BasicNameValuePair("taxi_id", "1"));
          
          



            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            post.setEntity(ent);

            HttpResponse responsePOST = client.execute(post);  
            HttpEntity resEntity = responsePOST.getEntity();  
            if (resEntity != null) {     
                Log.i("RESPONSE", EntityUtils.toString(resEntity));                
            } 
    } catch (Exception e) {
        e.printStackTrace();
        }
    
    }
	public void omgPleaseStop(View v) {
		mgr.cancel(pi);
		finish();
	}     
}
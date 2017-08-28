package me.jewel.com;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class profileService extends Service implements SensorEventListener {
	  SensorManager ManagerSensor;
	  Sensor lightSensor,accSensor,proxSensor;
	  private boolean screenOn;
	  private  boolean screenUp;
	  private boolean lightPersent;
	  private AudioManager audioManager;
	  

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(this, "service created", Toast.LENGTH_SHORT).show();
		ManagerSensor = (SensorManager)getSystemService(SENSOR_SERVICE);
		lightSensor = ManagerSensor.getDefaultSensor(Sensor.TYPE_LIGHT);
	    accSensor = ManagerSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    proxSensor = ManagerSensor.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	    audioManager=(AudioManager) getSystemService(AUDIO_SERVICE);
	    
	    if(accSensor !=null){
	           ManagerSensor.registerListener(this, accSensor,SensorManager.SENSOR_DELAY_NORMAL);
	       }
	    if(lightSensor !=null){
	           ManagerSensor.registerListener(this, lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
	       }
	    if (proxSensor !=null){
	           ManagerSensor.registerListener(this, proxSensor,SensorManager.SENSOR_DELAY_NORMAL);
	       }
	    Log.d("OnCreate:","In oncreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("onStartComm:","In onStartCommand before");
		Thread thread=new Thread(new TheadClass(startId));
        thread.start();
		Log.d("onStartComm:","In onStartCommand after");
        return START_STICKY;
      }
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("onDestroy:","In onDestroy ");
		Toast.makeText(this, "Service destroyed",Toast.LENGTH_LONG).show();
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		Log.d("onSensorChanged:","In onSensorchanged");

		// TODO Auto-generated method stub
		  float currAccX = event.values[0];
          float currAccY = event.values[1];
          float currAccZ = event.values[2];
		 if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		 {
			   if(currAccZ >= 2 && currAccZ < 10){
	                screenUp=true;
	                
	            }else if(currAccZ <=-2 && currAccZ >-10){
	                screenUp=false;
	               
	            } 
		 }
		 
	     if(event.sensor.getType()==Sensor.TYPE_LIGHT)
	     {
	           if(event.values[0]<=10){	               
	               lightPersent=false;
	          }else if(event.values[0]>10) {                
	               lightPersent=true;
	           }

	      }
	     
	     if (event.sensor.getType()==Sensor.TYPE_PROXIMITY)
	     {
	           if(event.values[0]==0){
	               screenOn=false;
	                
	           }else{
	                screenOn=true;
	              	          }

	      } 
	     
	     if (screenUp)
	     {	            
	            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	            audioManager.setStreamVolume(AudioManager.STREAM_RING,audioManager.getStreamMaxVolume(AudioManager.STREAM_RING),0);
	     }
	     
	     else if (!screenUp)
	     {	            
	            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	      
	     } 
	     
	     else if (!lightPersent && !screenOn  && audioManager.getRingerMode()!=AudioManager.RINGER_MODE_VIBRATE)
	     {
	            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	            audioManager.setStreamVolume(AudioManager.STREAM_RING,20,0);
	            

	     }
		
	}
	
    class TheadClass implements Runnable {
        int serviceId;


        TheadClass(int serviceId) {
            this.serviceId = serviceId;
        }

        public void run() {
           try {
       			Log.d("onThread:","In onThread");

               Thread.sleep(150);
             
              } catch (InterruptedException e) {
                    e.printStackTrace();
               }

        }
    }

}












































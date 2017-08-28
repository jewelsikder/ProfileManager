package me.jewel.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProfileManagerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
   public void start(View v)
   {
	   Intent intent=new Intent(this,profileService.class);
       startService(intent);
	   
   }
   
   public void stop(View v)
   { 
	   Intent intent=new Intent(this,profileService.class);
       stopService(intent);
	   
   }
}
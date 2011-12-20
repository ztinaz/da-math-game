package com.games.dmg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ProgressBar;

public class vSplash extends Activity {
	
	 /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;    

	
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);
        
        ProgressBar pb = (ProgressBar) findViewById(R.id.pbloading);
        pb.setProgress(10);
        
    
        //Comment out following code 
        new Handler().postDelayed(new Runnable() {
        	@Override
        	public void run() {
        		Intent i = new Intent(vSplash.this, DMG.class);
        		vSplash.this.startActivity(i);
        		vSplash.this.finish();
        	}},1000);
    
        
	}
	
	public boolean OnTouchEvent(MotionEvent evt) {
		if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
	}
}

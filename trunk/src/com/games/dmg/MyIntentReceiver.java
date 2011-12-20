package com.games.dmg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class MyIntentReceiver extends BroadcastReceiver {
	
	public String newNumber = "";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		try { 
			String action = intent.getAction();
			if( action.equals("com.telephony.lma.NEWPHONE")) {
				
				Bundle extra = intent.getExtras();
				newNumber = extra.getString("NewPhone");
			}
		}catch(Exception e) { ; } 
	} 
}

package com.esspl.hemendra.basicneed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CallStateListener extends BroadcastReceiver {
    private static final String TAG = CallStateListener.class.getSimpleName();
    public CallStateListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Asuchi...........................");
        Toast.makeText(context, "Incoming Call Be alert.....", Toast.LENGTH_SHORT).show();
       //intent = new Intent(context, CallDetailsService.class);
        intent.setAction("ACCESS_PHONE_STATE");
        intent.setClass(context,CallReceiveService.class);
        //intent.setClass(context,CallDetailsService.class);
        context.startService(intent);

    }
}

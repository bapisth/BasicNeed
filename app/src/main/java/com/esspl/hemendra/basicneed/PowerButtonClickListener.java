package com.esspl.hemendra.basicneed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PowerButtonClickListener extends BroadcastReceiver {
    final static String TAG = PowerButtonClickListener.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Powerbutton Clicked............................");
        Toast.makeText(context, "Media Button Clicked.........."+intent.getAction(), Toast.LENGTH_SHORT).show();
    }
}

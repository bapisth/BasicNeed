package com.esspl.hemendra.basicneed;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import java.io.IOException;

/**
 * Created by hemendra on 17-03-2016.
 */
public class CallReceiveService extends Service {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private static final String TAG = "CallReceiveService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Intent myIntent = intent;
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(TAG, "onHandleIntent: Service Vitare Achhi...........");
            if ("ACCESS_PHONE_STATE".equals(action)){
                Log.d(TAG, "onHandleIntent: Phone Asuchi re..........");
                SensorEventListener sensorEventListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        Log.d(TAG, "onSensorChanged: Event clicked.................");
                        if (event.sensor.getType()== Sensor.TYPE_PROXIMITY){
                            float nearHumanTouch = event.values[0];
                            Log.d(TAG, "onSensorChanged: Event Values : "+ nearHumanTouch);
                            if (nearHumanTouch==0.0f){
                                Log.d(TAG, "onSensorChanged: If vitaraku pasuchi ");
                                String callState =  myIntent.getStringExtra(TelephonyManager.EXTRA_STATE);
                                Log.d(TAG, "onSensorChanged: CallState :"+callState);
                                Log.d(TAG, "onSensorChanged: TelephonyManager.EXTRA_STATE_RINGING "+TelephonyManager.EXTRA_STATE_RINGING);
                                if (callState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                                    Log.d(TAG, "onSensorChanged: Sala Phone Ring Hauchi re utheide........");
                                    String number = myIntent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                                    Log.d(TAG, "Incoming call from ============: " + number);

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d(TAG, "run: Run method re pasila.....");
                                            try {
                                                Runtime.getRuntime().exec("input keyevent "+ KeyEvent.KEYCODE_HEADSETHOOK);
                                            } catch (IOException e) {
                                                Log.e(TAG, "run: Error ",e );
                                            }
                                        }
                                    }).start();

                                }

                            }
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        Log.d(TAG, "onAccuracyChanged: fired....accuracy :"+accuracy);
                    }
                };
                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                mSensorManager.registerListener(sensorEventListener, mSensor, SensorManager.SENSOR_DELAY_FASTEST);

            }
        }

        BroadcastReceiver listenPowerButtonClick = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: Service vitare mu Power button ku dekhibi  " + intent.getAction());
                if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF) || intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF))
                    stopSelf();
            }
        };
        registerReceiver(listenPowerButtonClick,new IntentFilter("android.intent.action.SCREEN_OFF"));
        registerReceiver(listenPowerButtonClick,new IntentFilter("android.intent.action.SCREEN_ON"));

        return 0;
    }
}

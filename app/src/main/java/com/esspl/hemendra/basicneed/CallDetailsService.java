package com.esspl.hemendra.basicneed;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import java.io.IOException;

public class CallDetailsService extends IntentService {

    private static final String ACTION_FOO = "com.esspl.hemendra.basicneed.action.FOO";
    private static final String ACTION_BAZ = "com.esspl.hemendra.basicneed.action.BAZ";
    private static final String TAG = "CallReceiveService";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.esspl.hemendra.basicneed.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.esspl.hemendra.basicneed.extra.PARAM2";

    private SensorManager mSensorManager;
    private Sensor mSensor;

    public CallDetailsService() {
        super("CallDetailsService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CallDetailsService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, CallDetailsService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Intent myIntent = intent;
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(TAG, "onHandleIntent: Service Vitare Achhi...........");
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }else if ("ACCESS_PHONE_STATE".equals(action)){
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
                                                Runtime.getRuntime().exec("input keyevent "+KeyEvent.KEYCODE_HEADSETHOOK);
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

                    }
                };
                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                mSensorManager.registerListener(sensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        Log.d(TAG, "onHandleIntent: Service Died........");
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


}

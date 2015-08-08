package pe.area51.broadcasttestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case MainActivity.ACTION_MENU_BUTTON_CLICKED:
                Log.d(TAG, "Button clicked!");
                break;
            case Intent.ACTION_BATTERY_LOW:
                Log.d(TAG, "Battery low!");
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Log.d(TAG, "Airplane mode!");
                break;
        }
    }

}
